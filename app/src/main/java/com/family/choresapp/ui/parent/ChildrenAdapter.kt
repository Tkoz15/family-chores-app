package com.family.choresapp.ui.parent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.family.choresapp.data.database.entities.UserEntity
import com.family.choresapp.databinding.ItemChildSummaryBinding

class ChildrenAdapter(
    private val onChildClick: (UserEntity) -> Unit
) : ListAdapter<UserEntity, ChildrenAdapter.ChildViewHolder>(ChildDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val binding = ItemChildSummaryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ChildViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    inner class ChildViewHolder(
        private val binding: ItemChildSummaryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(child: UserEntity) {
            binding.textChildName.text = child.userName
            binding.textBalance.text = String.format("$%.2f", child.allowanceBalance)
            
            binding.buttonPayout.setOnClickListener {
                onChildClick(child)
            }
        }
    }
    
    class ChildDiffCallback : DiffUtil.ItemCallback<UserEntity>() {
        override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
            return oldItem == newItem
        }
    }
}