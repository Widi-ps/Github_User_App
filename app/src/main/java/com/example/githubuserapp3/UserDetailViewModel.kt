package com.example.githubuserapp3

import android.app.Application
import androidx.lifecycle.*
import com.example.githubuserapp3.data.Repository
import com.example.githubuserapp3.data.Resource
import com.example.githubuserapp3.data.remote.RetrofitService
import com.example.githubuserapp3.model.User
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel(application: Application): AndroidViewModel(application) {

    val repository = Repository(application)

    suspend fun getDetailUser(username: String) = repository.getDetailUser(username)

    fun insertFavoriteUser(user: User) = viewModelScope.launch {
        repository.insertFavoriteUser(user)
    }

    fun deleteFavoriteUser(user: User) = viewModelScope.launch {
        repository.deleteFavoriteUser(user)
    }
}