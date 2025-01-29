package com.kotlinflow.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/**
 * UserDao is RoomDatabase specific contract/interface, which Room library will implement/support
 * to provide the right mapping of query with declared function.
 *
 * Prerequisite: We need our own database(with specific name) object to get Dao objects.
 */
@Dao
interface UserDao {
    @Query("SELECT * FROM user_table")//Name of the table is lowercase
    suspend fun getUsers(): List<UserDb>

    @Insert
    suspend fun insertAll(users: List<UserDb>)

    @Delete
    suspend fun deleteUser(userDb: UserDb)
}