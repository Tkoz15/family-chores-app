package com.family.choresapp.ui.child

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.family.choresapp.R
import com.family.choresapp.data.database.entities.ChoreCompletionEntity
import com.family.choresapp.data.database.entities.ChoreStatus
import com.family.choresapp.databinding.ItemCompletionBinding

class CompletionAdapter : ListAdapter<ChoreCompletionEntity, CompletionAdapter.CompletionViewHolder>(CompletionDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompletionViewHolder {
        val binding = ItemCompletionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CompletionViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: CompletionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    class CompletionViewHolder(
        private val binding: ItemCompletionBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(completion: ChoreCompletionEntity) {
            binding.textAmount.text = String.format("$%.2f", completion.amountEarned)
            
            when (completion.status) {
                ChoreStatus.IN_PROGRESS -> {
                    binding.textStatus.text = "In Progress"
                    binding.textStatus.setTextColor(ContextCompat.getColor(itemView.context, R.color.in_progress))
                    binding.statusIcon.text = "⏳"
                }
                ChoreStatus.COMPLETED -> {
                    binding.textStatus.text = "Pending"
                    binding.textStatus.setTextColor(ContextCompat.getColor(itemView.context, R.color.pending))
                    binding.statusIcon.text = "⏰"
                }
                ChoreStatus.APPROVED -> {
                    binding.textStatus.text = "Approved"
                    binding.textStatus.setTextColor(ContextCompat.getColor(itemView.context, R.color.approved))
                    binding.statusIcon.text = "✅"
                }
                ChoreStatus.REJECTED -> {
                    binding.textStatus.text = "Rejected"
                    binding.textStatus.setTextColor(ContextCompat.getColor(itemView.context, R.color.rejected))
                    binding.statusIcon.text = "❌"
                }
            }
        }
    }
    
    class CompletionDiffCallback : DiffUtil.ItemCallback<ChoreCompletionEntity>() {
        override fun areItemsTheSame(oldItem: ChoreCompletionEntity, newItem: ChoreCompletionEntity): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: ChoreCompletionEntity, newItem: ChoreCompletionEntity): Boolean {
            return oldItem == newItem
        }
    }
}