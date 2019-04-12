package com.alandvg.androidpaginglibrarystudy.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alandvg.androidpaginglibrarystudy.R
import com.alandvg.androidpaginglibrarystudy.datasource.State
import kotlinx.android.synthetic.main.activity_main.view.*

class ListFooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(status: State?) {
        itemView.progress_bar.visibility = if (status == State.LOADING) VISIBLE else INVISIBLE
        itemView.txt_error.visibility = if (status == State.ERROR) VISIBLE else INVISIBLE
    }

    companion object {
        fun create(retry: () -> Unit, parent: ViewGroup): ListFooterViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_footer, parent, false)
            view.txt_error.setOnClickListener { retry() }
            return ListFooterViewHolder(view)
        }
    }
}