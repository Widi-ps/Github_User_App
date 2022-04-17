package com.example.githubuserapp3

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp3.data.Repository
import com.example.githubuserapp3.data.Resource
import com.example.githubuserapp3.data.remote.ApiService
import com.example.githubuserapp3.data.remote.RetrofitService
import com.example.githubuserapp3.model.SearchResponse
import com.example.githubuserapp3.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository = Repository(application)

    fun searchUser(query: String) = repository.searchUser(query)
}