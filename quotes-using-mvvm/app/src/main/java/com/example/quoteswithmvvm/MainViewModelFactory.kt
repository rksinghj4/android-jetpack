package com.example.quoteswithmvvm.com.example.quoteswithmvvm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * If we need params in viewmodel then we need to create a factory,
 * which will accept the required params for generating the demand.
 */
class MainViewModelFactory(val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(context) as T
    }
}