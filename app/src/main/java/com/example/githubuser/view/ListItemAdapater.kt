package com.example.githubuser.view

import android.app.Activity
import android.opengl.Visibility
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.githubuser.R
import com.example.githubuser.model.UserListDto

class ListItemAdapater(private var userList: MutableList<UserListDto.UserItemDto>,
                       private var activity: Activity): RecyclerView.Adapter<ListItemAdapater.CustomViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(LayoutInflater.from(parent.context)
                                .inflate(R.layout.list_user_row, parent,false))
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        var userItem: UserListDto.UserItemDto = userList[position]

        //Setting the profile image
        Glide.with(activity)
            .load(userItem.avatar_url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.profileImage)

        //Setting username
        holder.userName.text = userItem.login

        //Setting detail
        holder.details.text = userItem.type

        if(userItem.note.isNullOrEmpty()) {
            holder.note.visibility = View.GONE
        }

    }

    fun addUpdateListData(userListParam: MutableList<UserListDto.UserItemDto>) {
        this.userList = userListParam
        notifyDataSetChanged()
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var profileImage: ImageView = itemView.findViewById(R.id.user_image)
        var userName: TextView = itemView.findViewById(R.id.user_name)
        var details: TextView = itemView.findViewById(R.id.user_details)
        var note: ImageView = itemView.findViewById(R.id.note_indicator)

    }

}