package com.example.githubuserapp3

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuserapp3.databinding.ActivityUserDetailBinding

import com.example.githubuserapp3.data.Resource
import com.example.githubuserapp3.model.User
import com.example.githubuserapp3.utill.Constanta.EXTRA_USER
import com.example.githubuserapp3.utill.Constanta.TAB_TITLES
import com.example.githubuserapp3.utill.ViewStateCallback
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserDetailActivity : AppCompatActivity(), ViewStateCallback<User?> {

    private lateinit var detailBinding: ActivityUserDetailBinding
    private val viewModel  by viewModels<UserDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            elevation = 0f
        }

        val username = intent.getStringExtra(EXTRA_USER)

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getDetailUser(username.toString()).observe(this@UserDetailActivity, {
                when (it) {
                    is Resource.Error -> onFailed(it.message)
                    is Resource.Loading -> onLoading()
                    is Resource.Success -> onSuccess(it.data)
                }
            })
        }

        val pageAdapter = FollowPageAdapter(this, username.toString())

        detailBinding.apply {
            viewPager.adapter = pageAdapter
            TabLayoutMediator(tabs, viewPager) { tabs, position ->
                tabs.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onSuccess(data: User?) {

        detailBinding.apply {
            tvDetailNumberOfRepos.text = data?.repository.toString()
            tvDetailNumberOfFollowers.text = data?.follower.toString()
            tvDetailNumberOfFollowing.text = data?.following.toString()
            tvDetailName.text = data?.name
            tvDetailCompany.text = data?.company
            tvDetailLocation.text = data?.location

            Glide.with(this@UserDetailActivity)
                .load(data?.avatar)
                .apply(RequestOptions.circleCropTransform())
                .into(ivDetailAvatar)

            ivFavorite.visibility = View.VISIBLE

            if (data?.isFavorite == true)
                ivFavorite.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite_24))
            else
                ivFavorite.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_favorite_border_24))

            supportActionBar?.title = data?.username

            ivFavorite.setOnClickListener {
                if (data?.isFavorite == true) {
                    data.isFavorite = false
                    viewModel.deleteFavoriteUser(data)
                    ivFavorite.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_favorite_border_24))
                } else {
                    data?.isFavorite = true
                    data?.let { it1 -> viewModel.insertFavoriteUser(it1) }
                    ivFavorite.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite_24))
                }
            }
        }
    }

    override fun onLoading() {
        detailBinding.ivFavorite.visibility = View.INVISIBLE
    }

    override fun onFailed(message: String?) {
        detailBinding.ivFavorite.visibility = View.INVISIBLE
    }
}