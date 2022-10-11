package com.example.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ContactDao {

    /**
     * 1. All queries are verified at compile time.
     * 2. suspend keyword is used to perform long running operations on background thread
     * with the help of coroutine. If we run on main thread. It will throw exceptions.
     *
     */

    @Insert
    suspend fun insertContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Update
    suspend fun updateContact(contact: Contact)

    /**
     * 1. No suspend keyword needed here.
     * 2. Result of getters are wrapped inside LiveData then
     * room will perform this operation on bg thread and notify the observer for any data change.
     */

    @Query("SELECT * FROM contact")
    fun getContact(): LiveData<List<Contact>>
}