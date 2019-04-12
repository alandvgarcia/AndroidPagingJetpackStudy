package com.alandvg.androidpaginglibrarystudy.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.alandvg.androidpaginglibrarystudy.entities.User
import com.alandvg.androidpaginglibrarystudy.network.NetworkService
import io.reactivex.disposables.CompositeDisposable

class UserDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val networkService: NetworkService
) : DataSource.Factory<Int, User>() {

    val usersDataSourceLiveData = MutableLiveData<UserDataSource>()

    override fun create(): DataSource<Int, User> {
        val userDataSource = UserDataSource(networkService, compositeDisposable)
        usersDataSourceLiveData.postValue(userDataSource)
        return userDataSource
    }

}