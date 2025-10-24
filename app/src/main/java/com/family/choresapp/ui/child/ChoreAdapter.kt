package com.family.choresapp.ui.child

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.family.choresapp.data.database.entities.ChoreEntity
import com.family.choresapp.databinding.ItemChoreBinding

class ChoreAdapter(
    private val onChoreClick: (ChoreEntity) -> Unit
) : ListAdapter<ChoreEntity, ChoreAdapter.ChoreViewHolder>(ChoreDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoreViewHolder {
        val binding = ItemChoreBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ChoreViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: ChoreViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    inner class ChoreViewHolder(
        private val binding: ItemChoreBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(chore: ChoreEntity) {
            binding.textChoreName.text = chore.name
            binding.textReward.text = String.format("$%.2f", chore.reward)
            
            binding.buttonStart.setOnClickListener {
                onChoreClick(chore)
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