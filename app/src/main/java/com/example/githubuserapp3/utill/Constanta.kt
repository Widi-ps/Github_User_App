package com.example.githubuserapp3.utill

import androidx.annotation.StringRes
import com.example.githubuserapp3.R

object Constanta {
    const val TIME_SPLASH = 1500L

    const val EXTRA_USER = "EXTRA_USER"

    @StringRes
    val TAB_TITLES = intArrayOf(
        R.string.followers,
        R.string.following
    )

    const val GITHUB_TOKEN = "055044b02ef50083d69daf0h7b96531f2928e049"

    const val BASE_URL = "https://api.github.com"
}