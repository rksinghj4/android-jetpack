package com.example.workmanager.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.workmanager.utils.NetworkUtils.Companion.LOG_TAG
import com.example.workmanager.view.QuoteApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuoteWorker(
    private val context: Context,
    params: WorkerParameters
): Worker(context, params) {
    override fun doWork(): Result {
        //Performs work synchronously on a background thread provided by WorkManager
        Log.d(LOG_TAG, "QuoteWorker: doWork() called")
        val repository = (context as QuoteApplication).quoteRepository
        //UnStructured coroutine - not tied to any lifecycle
        CoroutineScope(Dispatchers.IO).launch {
            repository.getQuotesBackGround()
        }
        return Result.success()
    }
}