package com.family.choresapp.ui.parent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.family.choresapp.data.database.entities.ChoreCompletionEntity
import com.family.choresapp.databinding.FragmentReviewChoresBinding
import com.family.choresapp.viewmodel.ChoreViewModel

class ReviewChoresFragment : Fragment() {
    
    private var _binding: FragmentReviewChoresBinding? = null
    private val binding get() = _binding!!
    
    private val choreViewModel: ChoreViewModel by viewModels()
    private lateinit var reviewAdapter: ReviewAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReviewChoresBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        observeData()
    }
    
    private fun setupRecyclerView() {
        reviewAdapter = ReviewAdapter(
            onApprove = { completion ->
                choreViewModel.approveChore(completion.id)
            },
            onReject = { completion ->
                choreViewModel.rejectChore(completion.id)
            }
        )
        
        binding.recyclerViewPendingChores.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = reviewAdapter
        }
    }
    
    private fun observeData() {
        choreViewModel.pendingCompletions.observe(viewLifecycleOwner) { completions ->
            reviewAdapter.submitList(completions)
            
            if (completions.isEmpty()) {
                binding.textEmptyState.visibility = View.VISIBLE
                binding.recyclerViewPendingChores.visibility = View.GONE
            } else {
                binding.textEmptyState.visibility = View.GONE
                binding.recyclerViewPendingChores.visibility = View.VISIBLE
            }
        }
        
        choreViewModel.operationStatus.observe(viewLifecycleOwner) { status ->
            Toast.makeText(context, status, Toast.LENGTH_SHORT).show()
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}