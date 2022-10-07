package com.example.quoteswithmvvm.com.example.quoteswithmvvm

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.quoteswithmvvm.Quote
import com.google.gson.Gson

class MainViewModel(val context: Context): ViewModel() {
    private var quoteList: Array<Quote> = emptyArray()
    private var index = 0

    init {
        quoteList = loadQuoteFromAssets()
    }

    private fun loadQuoteFromAssets(): Array<Quote> {
        //Open quotes.json, it returns InputStream
        val inputStream = context.assets.open("quotes.json")
        val size: Int = inputStream.available() //Find size of opened file.
        val buffer = ByteArray(size) //Create buffer(i.e ByteArray) of required size
        inputStream.read(buffer) //read from input stream to buffer
        inputStream.close() //Close inputStream
        val json = String(buffer, Charsets.UTF_8)
        val gson = Gson()
        return gson.fromJson(json, Array<Quote>::class.java)
    }

    fun getQuote() = quoteList[index]

    fun nextQuote() = quoteList[++index % quoteList.size]

    fun previousQuote() = quoteList[(--index + quoteList.size) % quoteList.size]
}