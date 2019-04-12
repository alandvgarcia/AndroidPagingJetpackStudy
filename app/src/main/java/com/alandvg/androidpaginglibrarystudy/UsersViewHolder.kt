package com.alandvg.androidpaginglibrarystudy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alandvg.androidpaginglibrarystudy.entities.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_users.view.*

class UsersViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(users: User?) {
        if (users != null) {
            itemView.txt_news_name.text = users.firstName
            Picasso.get().load(users.avatar).into(itemView.img_news_banner)
        }
    }

    companion object {
        fun create(parent: ViewGroup): UsersViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_users, parent, false)
            return UsersViewHolder(view)
        }
    }
}