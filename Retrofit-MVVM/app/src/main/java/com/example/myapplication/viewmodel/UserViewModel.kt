package com.example.myapplication.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.myapplication.model.NetworkState
import com.example.myapplication.repository.UserRepository
import com.example.myapplication.webservice.RetrofitBuilder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class UserViewModel(
    private val userRepository: UserRepository,
    private val savedInstantHandle: SavedStateHandle,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _usersStateFlow = MutableStateFlow<NetworkState>(NetworkState.Loading)

    internal val usersStateFlow get() = _usersStateFlow.asStateFlow()

    init {
        getUsers()
    }

    private fun getUsers() {
        viewModelScope.launch(dispatcher) {
            try {
                val listOfUsers = userRepository.getUsers()
                _usersStateFlow.value = NetworkState.Success(listOfUsers)
            } catch (e: Exception) {
                _usersStateFlow.value = NetworkState.Error("An unknown error occurred")
            }
        }
    }


    companion object {
        val FACTORY: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val savedInstantHandle = extras.createSavedStateHandle()
                return UserViewModel(
                    UserRepository(RetrofitBuilder.apiServiceImpl),
                    savedInstantHandle = savedInstantHandle
                ) as T
            }
        }
    }

}