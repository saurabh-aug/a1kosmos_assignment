package com.sample.a1kosmos.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sample.a1kosmos.data.model.user.User
import com.sample.a1kosmos.databinding.ItemUserBinding


class UserListAdapter() : RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    private val userList = ArrayList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount() = userList.size

    fun setList(users: List<User>) {
//        userList.clear()
        userList.addAll(users)
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(user: User) {
            binding.apply {
                txtName.text = "${user.first_name} ${user.last_name}"
                txtEmail.text = user.email
                Glide.with(binding.root.context).load(user.avatar).into(imageView)
            }
        }
    }
}


