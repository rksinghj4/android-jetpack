package com.example.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * 1. List all entities here in db
 * 2. If we want to supply updated db with new release. Then increase the version.
 */
@Database(entities = [Contact::class], version = 1, exportSchema = true)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao

    companion object {

        /**
         * Volatile annotation:
         * Marks the JVM backing field of the annotated property as volatile, meaning that
         * writes to this field are immediately made visible to other threads.
         */
        @Volatile
        private var INSTANCE: ContactDatabase? = null

        fun getDatabase(context: Context): ContactDatabase {
            /**
             * synchronized block is used for Thread safety.
             *
             */

            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext, ContactDatabase::class.java, "contactDB"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}