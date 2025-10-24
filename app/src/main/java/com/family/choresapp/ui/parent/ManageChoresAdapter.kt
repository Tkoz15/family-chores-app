package com.family.choresapp.ui.parent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.family.choresapp.data.database.entities.ChoreEntity
import com.family.choresapp.databinding.ItemManageChoreBinding

class ManageChoresAdapter(
    private val onEdit: (ChoreEntity) -> Unit,
    private val onDelete: (ChoreEntity) -> Unit
) : ListAdapter<ChoreEntity, ManageChoresAdapter.ManageChoreViewHolder>(ChoreDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManageChoreViewHolder {
        val binding = ItemManageChoreBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ManageChoreViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: ManageChoreViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    inner class ManageChoreViewHolder(
        private val binding: ItemManageChoreBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(chore: ChoreEntity) {
            binding.textChoreName.text = chore.name
            binding.textReward.text = String.format("$%.2f", chore.reward)
            
            binding.buttonEdit.setOnClickListener {
                onEdit(chore)
            }
            
            binding.buttonDelete.setOnClickListener {
                onDelete(chore)
            }
        }
    }
    
    class ChoreDiffCallback : DiffUtil.ItemCallback<ChoreEntity>() {
        override fun areItemsTheSame(oldItem: ChoreEntity, newItem: ChoreEntity): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: ChoreEntity, newItem: ChoreEntity): Boolean {
            return oldItem == newItem
        }
    }
}