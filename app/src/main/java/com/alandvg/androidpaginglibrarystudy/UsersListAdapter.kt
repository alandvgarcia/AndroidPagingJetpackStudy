package com.alandvg.androidpaginglibrarystudy

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alandvg.androidpaginglibrarystudy.datasource.State
import com.alandvg.androidpaginglibrarystudy.entities.User
import com.alandvg.androidpaginglibrarystudy.viewholders.ListFooterViewHolder

class UsersListAdapter(private val retry: () -> Unit) :
    PagedListAdapter<User, RecyclerView.ViewHolder>(UsersDiffCallback) {

    private val DATA_VIEW_TYPE = 1
    private val FOOTER_VIEW_TYPE = 2
    private var state = State.LOADING


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATA_VIEW_TYPE) UsersViewHolder.create(parent) else ListFooterViewHolder.create(
            retry,
            parent
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE)
            (holder as UsersViewHolder).bind(getItem(position))
        else (holder as ListFooterViewHolder).bind(state)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }


    companion object {
        val UsersDiffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }


    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (state == State.LOADING || state == State.ERROR)
    }

    fun setState(state: State) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }


}