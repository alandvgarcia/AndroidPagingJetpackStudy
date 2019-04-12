package com.alandvg.androidpaginglibrarystudy.entities

import com.google.gson.annotations.SerializedName

data class User(
    val id : Int? = null,
    @SerializedName("first_name")val firstName : String = "",
    @SerializedName("last_name")val lastName : String = "",
    val avatar : String = ""
)