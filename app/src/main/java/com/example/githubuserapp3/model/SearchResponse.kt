package com.example.githubuserapp3.model

import com.squareup.moshi.Json

class SearchResponse (
    @field:Json(name = "items")
    val items: List<User>
)