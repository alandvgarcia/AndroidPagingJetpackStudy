package com.alandvg.androidpaginglibrarystudy.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.alandvg.androidpaginglibrarystudy.entities.User
import com.alandvg.androidpaginglibrarystudy.network.NetworkService
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

class UserDataSource(
    private val networkService: NetworkService,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, User>() {


    var state: MutableLiveData<State> = MutableLiveData()
    private var retryCompletable: Completable? = null


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, User>) {
        updateState(State.LOADING)
        compositeDisposable.add(
            networkService.getUsers(1, params.requestedLoadSize)
                .subscribe(
                    { response ->
                        updateState(State.DONE)
                        callback.onResult(response.data, null, 2)
                    },
                    {
                        updateState(State.ERROR)
                        setRetry(Action { loadInitial(params, callback) })

                    })
        )


    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {
        updateState(State.LOADING)
        compositeDisposable.add(
            networkService.getUsers(params.key, params.requestedLoadSize)
                .subscribe(
                    { response ->
                        updateState(State.DONE)
                        callback.onResult(
                            response.data,
                            params.key + 1
                        )

                    },
                    {
                        updateState(State.ERROR)
                        setRetry(Action { loadAfter(params, callback) })
                    }
                )
        )

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {

    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }

    fun retry() {
        if (retryCompletable != null) {
            retryCompletable?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe()?.let {
                    compositeDisposable.add(
                        it
                    )
                }
        }

    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }

}