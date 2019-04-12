package com.alandvg.androidpaginglibrarystudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alandvg.androidpaginglibrarystudy.datasource.State
import com.alandvg.androidpaginglibrarystudy.network.NetworkService
import com.alandvg.androidpaginglibrarystudy.viewholders.UsersListViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    lateinit var viewModel: UsersListViewModel
    lateinit var usersListAdapter: UsersListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(UsersListViewModel::class.java)

        initAdapter()
        initState()

//        val compositeDisposable = CompositeDisposable()
//        compositeDisposable.add(
//            NetworkService.getService()
//                .getUsers(1, 3)
//                .subscribeOn(Schedulers.io())
//                .doOnSuccess {
//                    Log.d("Teste", "$it")
//                }
//                .subscribe()
//        )


    }


    fun initAdapter() {

        usersListAdapter = UsersListAdapter { viewModel.retry() }
        recycler_view.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycler_view.adapter = usersListAdapter
        viewModel.userList.observe(this, Observer {
            usersListAdapter.submitList(it)
        })

    }

    fun initState() {

        txt_error.setOnClickListener { viewModel.retry() }
        viewModel.getState().observe(this, Observer { state ->
            progress_bar.visibility = if (viewModel.listIsEmpty() && state == State.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (viewModel.listIsEmpty() && state == State.ERROR) View.VISIBLE else View.GONE
            if (!viewModel.listIsEmpty()) {
                usersListAdapter.setState(state ?: State.DONE)
            }
        })


    }
}
