package com.filedownloader.viewmodelinternals.customlivedata

import android.os.Handler
import android.os.Looper
import androidx.annotation.MainThread
import kotlinx.coroutines.Runnable
import java.lang.IllegalStateException

/**
 * Note: CustomObserver<T?> - can't use ? nullable type directly in class declaration and typealias
 * fun <T?> functionName() { } - gives error
 */

fun <T> functionName() {} //No error

typealias CustomObserver<T> = (T?) -> Unit

/**
 * LiveDataWithoutLifecycle<T?> - can't use nullable in generic declaration (i.eT?) directly
 *
 * Error with T?: The issue comes from placing the ? in the generic type parameter directly.
 * Kotlin needs T to be defined as a generic type and not immediately as T?.
 *
 * Fix: Define your class as class LiveDataWithoutLifecycle<T> and then handle
 * nullable types (e.g., T?) where appropriate when you instantiate or use the class.
 *e.g LiveDataWithoutLifecycle<String?> - works fine
 */

// In LiveDataWithoutLifecycle<T> T covers both nullable as well as non nullable
abstract class LiveDataWithoutLifecycle<T>(initialValue: T? = null) {
    private var data: T? = initialValue

    private val observers = arrayListOf<CustomObserver<T>>()

    //Helper needed
    private val mDataLock = Any()
    private val noData: Any? = Any()

    private var mPendingData: Any? = noData //Note initially mPendingData = noData

    //Handler with MainLooper - Handler(Looper.getMainLooper())
    private var mMainHandler: Handler? = null

    //Define a task
    private val mPostTaskRunnable = object : Runnable {
        override fun run() {
            val newValue: Any?
            synchronized(mDataLock) {//Thread safety
                newValue = mPendingData//mPendingData holds data to be posted to main from bg thread
                mPendingData = noData
            }
            setValue(newValue as? T)
        }
    }

    fun observe(observer: CustomObserver<T>) {
        observers.add(observer)
    }

    fun getValue(): T? {
        return data
    }

    open fun postValue(value: T?) {
        var shouldPostTask: Boolean = false
        synchronized(mDataLock) {
            //mPendingData has noData to post, so new value can be posted to it
            shouldPostTask = mPendingData == noData
            //If already one task is scheduled in message queue, then shouldPostTask will be false,
            // whenever it will gets executed then this updated value will be posted
            mPendingData = value
        }
        if (!shouldPostTask) {
            return
        }
        postToMainThread(mPostTaskRunnable)
    }

    @MainThread
    open fun setValue(value: T?) {
        assertMainThread("setValue")
        data = value//Immediate/direct update to actual data
        //Notify all observer
        observers.forEach {
            it.invoke(data)
        }
    }

    private fun postToMainThread(runnableTask: Runnable) {
        //Use double null check mechanism
        mMainHandler = mMainHandler ?: synchronized(mDataLock) {
            mMainHandler ?: Handler(Looper.getMainLooper())
        }
        mMainHandler?.post(runnableTask)
    }

    /**
     * public class ArchTaskExecutor extends TaskExecutor { }
     * ArchTaskExecutor: A static class that serves as a central point to execute common tasks.
     * TaskExecutor:
     */
    private fun assertMainThread(methodName: String) {
        /**
         *@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
         *if (!ArchTaskExecutor.getInstance().isMainThread) -  can't use in my code here because of @RestrictTo
         *
         * LiveData.assertMainThread(methodName) - can be used here. Anyway we need to write our own Livedata
         */

        if (Looper.getMainLooper().thread !== Thread.currentThread()) {//Note: Referencial equality
            throw IllegalStateException("Can't invoke $methodName on background")
        }

        /**
         * Looper.myLooper() - Return the Looper object associated with the current thread.
         * Looper.myLooper() - Returns null if the calling thread is not associated with a Looper.
         *
         * Looper.getMainLooper() - Returns the application's main looper, which lives in the main thread of the application.
         */

        if (Looper.myLooper() == Looper.getMainLooper()) {///Use one check only, out of above one and this one, both are same
            println("Running on main thread")
        }
    }

}

class MutableLiveDataWithoutLifecycle<T>(value: T? = null) : LiveDataWithoutLifecycle<T>(value) {

    override fun postValue(value: T?) {
        super.postValue(value)
    }

    override fun setValue(value: T?) {
        super.setValue(value)
    }
}

/**
 * Don't run main to test live data it will give error. Use Activity to test.
 */
private fun main() {
    val liveData1: MutableLiveDataWithoutLifecycle<String?> =
        MutableLiveDataWithoutLifecycle("Singh")//No error for nullable type

    liveData1.observe {
        println("1. Onchange value: $it")
    }

    val liveData2: MutableLiveDataWithoutLifecycle<Int> =
        MutableLiveDataWithoutLifecycle(0)//Non nullable type
    liveData2.observe {
        println("2. Onchange value: $it")
    }

    //liveData1.setValue("Raj")
}