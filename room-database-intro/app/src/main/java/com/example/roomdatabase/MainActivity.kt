package com.example.roomdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var contactDatabase: ContactDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        contactDatabase = ContactDatabase.getDatabase(this)

        contactDatabase.contactDao().getContact().observe(this) {
            Log.d(TAG, "$it")
        }

        GlobalScope.launch {
            contactDatabase.contactDao().insertContact(Contact(0, "Raj", "2222", Date(), 1))
        }

    }

    fun getData(view: View) {
        contactDatabase.contactDao().getContact().observe(this) {
            Log.d(TAG, "$it")
        }
    }
}