package com.example.workmanager.view

import android.app.Application
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.workmanager.api.QuoteService
import com.example.workmanager.api.RetrofitHelper
import com.example.workmanager.db.QuoteDatabase
import com.example.workmanager.repository.QuoteRepository
import com.example.workmanager.worker.QuoteWorker
import java.util.concurrent.TimeUnit

class QuoteApplication: Application() {

    lateinit var quoteRepository: QuoteRepository

    override fun onCreate() {
        super.onCreate()
        initialize()
        setUpWorker()
    }

    private fun setUpWorker() {
        val constraint = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

        val workerRequest = PeriodicWorkRequest.Builder(
            QuoteWorker::class.java,
            15,
            TimeUnit.MINUTES
        ).setConstraints(constraint).build()

        WorkManager.getInstance(this).enqueue(workerRequest)
    }

    private fun initialize() {
        val quoteService = RetrofitHelper.getInstance().create(QuoteService::class.java)
        val database = QuoteDatabase.getDatabase(applicationContext)
        quoteRepository = QuoteRepository(quoteService, database, applicationContext)
    }
}