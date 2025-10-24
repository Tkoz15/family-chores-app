package com.family.choresapp.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.family.choresapp.data.database.entities.UserEntity
import com.family.choresapp.data.database.entities.UserType
import com.family.choresapp.databinding.ItemUserBinding

class UserAdapter(
    private val onUserClick: (UserEntity) -> Unit
) : ListAdapter<UserEntity, UserAdapter.UserViewHolder>(UserDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    inner class UserViewHolder(
        private val binding: ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(user: UserEntity) {
            binding.textUserName.text = user.userName
            
            // Set emoji based on user type
            binding.textUserEmoji.text = when (user.userType) {
                UserType.PARENT -> "👨‍👩‍👧‍👦"
                UserType.CHILD -> "👦"
            }
            
            binding.root.setOnClickListener {
                onUserClick(user)
            }
        }
    }
    
    class UserDiffCallback : DiffUtil.ItemCallback<UserEntity>() {
        override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
            return oldItem == newItem
        }
    }
}