package com.example.workmanager.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.workmanager.api.QuoteService
import com.example.workmanager.db.QuoteDatabase
import com.example.workmanager.models.QuoteList
import com.example.workmanager.utils.NetworkUtils

class QuoteRepository(
    private val quoteService: QuoteService,
    private val quoteDatabase: QuoteDatabase,
    private val applicationContext: Context
) {
    private val quotesLiveData = MutableLiveData<QuoteList>()

    val quotes: LiveData<QuoteList>
        get() = quotesLiveData

    suspend fun getQuotes(page: Int) {
        if(NetworkUtils.isInternetAvailable(applicationContext)) {
            val result = quoteService.getQuotes(page)
            if(result.body() != null) {
                quoteDatabase.quoteDao().addQuotes(result.body()!!.quotes)
                quotesLiveData.postValue(result.body())
            }
        }
        else{
            val quotes = quoteDatabase.quoteDao().getQuotes()
            val quoteList = QuoteList(1,1,1,quotes, 1, 1)
            quotesLiveData.postValue(quoteList)
        }
    }

    suspend fun getQuotesBackGround() {
        val randomNumber = (Math.random() * 10).toInt()
        val result = quoteService.getQuotes(randomNumber)
        if (result.body() != null) {
            quoteDatabase.quoteDao().addQuotes(result.body()!!.quotes)
        }
    }
}