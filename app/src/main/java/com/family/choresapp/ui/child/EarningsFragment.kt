package com.family.choresapp.ui.child

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.family.choresapp.databinding.FragmentEarningsBinding
import com.family.choresapp.viewmodel.ChoreViewModel
import com.family.choresapp.viewmodel.UserViewModel

class EarningsFragment : Fragment() {
    
    private var _binding: FragmentEarningsBinding? = null
    private val binding get() = _binding!!
    
    private val args: EarningsFragmentArgs by navArgs()
    private val choreViewModel: ChoreViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var completionAdapter: CompletionAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEarningsBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        observeData()
        
        userViewModel.setCurrentUser(args.childId)
        userViewModel.updateBalance(args.childId)
    }
    
    private fun setupRecyclerView() {
        completionAdapter = CompletionAdapter()
        
        binding.recyclerViewCompletions.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = completionAdapter
        }
    }
    
    private fun observeData() {
        userViewModel.userBalance.observe(viewLifecycleOwner) { balance ->
            binding.textTotalEarnings.text = String.format("$%.2f", balance)
        }
        
        choreViewModel.getCompletionsForChild(args.childId).observe(viewLifecycleOwner) { completions ->
            completionAdapter.submitList(completions)
            
            if (completions.isEmpty()) {
                binding.textEmptyState.visibility = View.VISIBLE
                binding.recyclerViewCompletions.visibility = View.GONE
            } else {
                binding.textEmptyState.visibility = View.GONE
                binding.recyclerViewCompletions.visibility = View.VISIBLE
            }
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}