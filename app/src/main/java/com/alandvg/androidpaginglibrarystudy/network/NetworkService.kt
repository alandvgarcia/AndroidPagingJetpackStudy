package com.alandvg.androidpaginglibrarystudy.network

import com.alandvg.androidpaginglibrarystudy.entities.Response
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("/api/users")
    fun getUsers(@Query("page") page: Int, @Query("per_page") pageSize: Int): Single<Response>

    companion object {
        fun getService(): NetworkService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://reqres.in/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(NetworkService::class.java)
        }
    }
}