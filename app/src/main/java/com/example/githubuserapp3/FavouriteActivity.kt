package com.example.githubuserapp3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp3.data.Resource
import com.example.githubuserapp3.databinding.ActivityFavouriteBinding
import com.example.githubuserapp3.model.User
import com.example.githubuserapp3.utill.ViewStateCallback
import kotlinx.coroutines.*

class FavouriteActivity : AppCompatActivity(), ViewStateCallback<List<User>> {

    private lateinit var favoriteBinding: ActivityFavouriteBinding
    private lateinit var userAdapter: UserAdapter
    private val viewModel: FavouriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteBinding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(favoriteBinding.root)

        userAdapter = UserAdapter()

        favoriteBinding.rvFavorite.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@FavouriteActivity, LinearLayoutManager.VERTICAL, false)
        }

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getFavoriteList().observe(this@FavouriteActivity) {
                when (it) {
                    is Resource.Error -> onFailed(it.message)
                    is Resource.Loading -> onLoading()
                    is Resource.Success -> it.data?.let { it1 -> onSuccess(it1) }
                }
            }
        }
    }

    override fun onSuccess(data: List<User>) {
        favoriteBinding.apply {
            favoriteProgressBar.visibility = invisible
        }
        userAdapter.setAllData(data)
    }

    override fun onLoading() {
        favoriteBinding.apply {
            favoriteProgressBar.visibility = visible
        }
    }

    override fun onFailed(message: String?) {
        if (message == null) {
            favoriteBinding.apply {
                favoriteProgressBar.visibility = invisible
                tvFavoriteError.text = "Tidak ada pengguna di daftar favorit"
                rvFavorite.visibility = invisible
            }
        }
    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getFavoriteList().observe(this@FavouriteActivity) {
                when (it) {
                    is Resource.Error -> onFailed(it.message)
                    is Resource.Loading -> onLoading()
                    is Resource.Success -> it.data?.let { it1 -> onSuccess(it1) }
                }
            }
        }
    }


}