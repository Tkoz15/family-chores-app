package com.family.choresapp.ui.parent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.family.choresapp.R
import com.family.choresapp.data.database.entities.ChoreCompletionEntity
import com.family.choresapp.databinding.ItemReviewBinding
import java.io.File

class ReviewAdapter(
    private val onApprove: (ChoreCompletionEntity) -> Unit,
    private val onReject: (ChoreCompletionEntity) -> Unit
) : ListAdapter<ChoreCompletionEntity, ReviewAdapter.ReviewViewHolder>(ReviewDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ItemReviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReviewViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    inner class ReviewViewHolder(
        private val binding: ItemReviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(completion: ChoreCompletionEntity) {
            binding.textAmount.text = String.format("$%.2f", completion.amountEarned)
            binding.textChildName.text = "Child #${completion.childId}" // We'd normally lookup the name
            
            // Load image if available
            completion.picturePath?.let { path ->
                val file = File(path)
                if (file.exists()) {
                    binding.imageChoreProof.load(file) {
                        placeholder(R.drawable.ic_image_placeholder)
                        error(R.drawable.ic_image_error)
                    }
                }
            }
            
            binding.buttonApprove.setOnClickListener {
                onApprove(completion)
            }
            
            binding.buttonReject.setOnClickListener {
                onReject(completion)
            }
        }
    }
    
    class ReviewDiffCallback : DiffUtil.ItemCallback<ChoreCompletionEntity>() {
        override fun areItemsTheSame(oldItem: ChoreCompletionEntity, newItem: ChoreCompletionEntity): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: ChoreCompletionEntity, newItem: ChoreCompletionEntity): Boolean {
            return oldItem == newItem
        }
    }
}