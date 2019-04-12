package com.alandvg.androidpaginglibrarystudy.viewholders

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.alandvg.androidpaginglibrarystudy.datasource.State
import com.alandvg.androidpaginglibrarystudy.datasource.UserDataSource
import com.alandvg.androidpaginglibrarystudy.datasource.UserDataSourceFactory
import com.alandvg.androidpaginglibrarystudy.entities.User
import com.alandvg.androidpaginglibrarystudy.network.NetworkService
import io.reactivex.disposables.CompositeDisposable

class UsersListViewModel : ViewModel() {

    private val networkService = NetworkService.getService()
    var userList: LiveData<PagedList<User>>
    private val compositeDisposable = CompositeDisposable()
    private val pageSize = 3
    private val userDataSourceFactory: UserDataSourceFactory

    init {
        userDataSourceFactory = UserDataSourceFactory(compositeDisposable, networkService)
        val config = PagedList.Config.Builder()
            .setPageSize(3)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(false)
            .build()

        userList = LivePagedListBuilder<Int, User>(userDataSourceFactory, config).build()
    }


    fun getState(): LiveData<State> = Transformations.switchMap<UserDataSource, State>(
        userDataSourceFactory.usersDataSourceLiveData, UserDataSource::state
    )

    fun retry() {
        userDataSourceFactory.usersDataSourceLiveData.value?.retry()
    }

    fun listIsEmpty(): Boolean = userList.value?.isEmpty() ?: true

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }


}