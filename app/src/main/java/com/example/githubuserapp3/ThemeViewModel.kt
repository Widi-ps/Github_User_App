package com.example.githubuserapp3

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubuserapp3.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ThemeViewModel (application: Application): AndroidViewModel(application) {

    private val repository = Repository(application)

    fun saveThemeSetting(isDarkModeActive: Boolean) = viewModelScope.launch {
        repository.saveThemeSetting(isDarkModeActive)
    }

    fun getThemeSetting() = repository.getThemeSetting().asLiveData(Dispatchers.IO)

}