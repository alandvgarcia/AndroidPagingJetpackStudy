package com.alandvg.androidpaginglibrarystudy.entities

import com.google.gson.annotations.SerializedName

data class Response(
    val page: Int = 1,
    @SerializedName("per_page") val perPage: Int = 3,
    val total: Int = 4,
    @SerializedName("total_pages") val totalPages: Int = 0,
    val data: List<User> = listOf()
)