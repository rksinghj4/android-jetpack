package com.example.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * 1. List all entities here in db
 * 2. If we want to supply updated db with new release. Then increase the version.
 */
@Database(entities = [Contact::class], version = 2, exportSchema = true)
@TypeConverters(Convertors::class)
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

            val migration_1_2 = object : Migration(1, 2) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL("ALTER TABLE Contact ADD COLUMN isActive INTEGER NOT NULL DEFAULT(1)")
                }
            }

            /**
             * synchronized block is used for Thread safety.
             *
             */


            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext, ContactDatabase::class.java, "contactDB"
                    ).addMigrations(migration_1_2).build()
                }
            }
            return INSTANCE!!
        }
    }
}