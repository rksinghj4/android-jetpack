package com.example.workmanager.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.workmanager.models.Quote

@Dao
interface QuoteDao {
    @Insert
    suspend fun addQuotes(quotes: List<Quote>)

    @Query("SELECT * FROM quote")
    suspend fun getQuotes() : List<Quote>
}