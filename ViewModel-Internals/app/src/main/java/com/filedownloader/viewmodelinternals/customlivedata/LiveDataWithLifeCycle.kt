package com.filedownloader.viewmodelinternals.customlivedata

import android.os.Handler
import android.os.Looper
import androidx.annotation.MainThread
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import java.lang.IllegalStateException

/**
 * The LiveData class provides the following advantages:
 *
 * Always up to date data: If a Lifecycle starts again (like an activity going back to started state from the back stack)
 * it receives the latest location data (if it didn’t already).
 */

/**
 * clear
 */

/**
 * https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150
 */
abstract class LiveDataWithLifeCycle<T>(initialValue: T? = null) {

    private var data: T? = initialValue //1.

    //It may have used by different owner
    private val observersHashMap: HashMap<Observer<T>, LifecycleObserverWrapper> = HashMap()//2.

    //Helper for thread safety
    private val mDataLock = Any()//1.
    private val noData = Any()// 2.
    private var mPendingData: Any? =
        noData //3. Initially mPendingData is equal to noData; must write : Any?

    private var mMainHandler: Handler? = null//1.

    private val mPostRunnableTask = Runnable {//2.
        var newValue: Any?
        synchronized(mDataLock) {
            newValue = mPendingData
            mPendingData = noData
        }
        setValue(newValue as? T)
    }

    fun getValue(): T? {
        return data
    }

    @MainThread
    open fun setValue(value: T?) {
        assertMainThread("setValue")
        data = value//Immediate/direct update to actual data
        //Notify all observer
        observersHashMap.values.forEach { lifecycleObserverWrapper ->
            if (lifecycleObserverWrapper.shouldNotify()) {//At least start state
                notifyChange(lifecycleObserverWrapper.observerLambda)
            }
        }
    }

    open fun postValue(value: T?) {
        var shouldPostTask = false
        synchronized(mDataLock) {
            //If Previous data is already processed inside Runnable {} then shouldPostTask will be true
            shouldPostTask = mPendingData == noData
            mPendingData = value//Always new value is picked up inside Runnable {}
        }

        if (!shouldPostTask) {
            return
        }
        postOnMainThread(mPostRunnableTask)
    }

    private fun postOnMainThread(runnableTask: Runnable) {
        mMainHandler = mMainHandler ?: synchronized(mDataLock) {
            mMainHandler ?: Handler(Looper.getMainLooper())
        }
        mMainHandler?.post(runnableTask)
    }

    private fun assertMainThread(methodName: String) {
        require(Looper.myLooper() == Looper.getMainLooper()) {
            throw IllegalStateException("Can't invoke $methodName on background")
        }
    }

    private inline fun notifyChange(observerLambda: Observer<T?>) {
        observerLambda.invoke(data)
    }

    fun observe(owner: LifecycleOwner, observer: Observer<T?>) {
        val lifecycleObserverWrapper = LifecycleObserverWrapper(owner, observer)
        observersHashMap[observer] = lifecycleObserverWrapper
        //Note: Adding DefaultLifecycleObserver implementation  to real lifecycle of owner
        owner.lifecycle.addObserver(lifecycleObserverWrapper)//Now owner is
    }

    private fun removeObserver(observer: Observer<T?>) {
        val lifecycleObserverWrapper = observersHashMap.remove(observer)
        //Note: Removing DefaultLifecycleObserver implementation  from  real lifecycle of owner
        lifecycleObserverWrapper?.owner?.lifecycle?.removeObserver(lifecycleObserverWrapper)
    }

    fun clear(owner: LifecycleOwner) {
        val toBeRemovedObserver = ArrayList<LifecycleObserverWrapper>()
        observersHashMap.values.forEach { lifecycleObserver ->
            if (lifecycleObserver.owner == owner) {//Remove all observer of this owner
                toBeRemovedObserver.add(lifecycleObserver)
            }
        }
        toBeRemovedObserver.forEach { removeObserver(it.observerLambda) }
    }

    /**
     * To make the Livedata lifecycle aware, we have to pass implementation of DefaultLifecycleObserver
     * to owner.lifecycle.addObserver(LifecycleObserver)
     *
     * To make it lifecycle unaware - owner?.lifecycle?.removeObserver(LifecycleObserver)
     */
    private inner class LifecycleObserverWrapper(
        val owner: LifecycleOwner,
        val observerLambda: Observer<T?>
    ) : DefaultLifecycleObserver {
        override fun onStart(owner: LifecycleOwner) {
            notifyChange(observerLambda)
        }

        override fun onResume(owner: LifecycleOwner) {
            notifyChange(observerLambda)
        }

        override fun onDestroy(owner: LifecycleOwner) {
            clear(owner)//We don't need to clear it from out side
            removeObserver(observerLambda)
        }

        fun shouldNotify(): Boolean {
            return owner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)
        }
    }

}

class MutableLiveDataWithLifecycle<T>(initialValue: T? = null) :
    LiveDataWithLifeCycle<T>(initialValue) {

    override fun postValue(value: T?) {
        super.postValue(value)
    }

    override fun setValue(value: T?) {
        super.setValue(value)
    }

}