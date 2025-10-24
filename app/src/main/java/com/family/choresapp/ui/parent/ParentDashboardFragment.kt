package com.family.choresapp.ui.parent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.family.choresapp.R
import com.family.choresapp.data.database.entities.UserEntity
import com.family.choresapp.databinding.FragmentParentDashboardBinding
import com.family.choresapp.viewmodel.ChoreViewModel
import com.family.choresapp.viewmodel.UserViewModel

class ParentDashboardFragment : Fragment() {
    
    private var _binding: FragmentParentDashboardBinding? = null
    private val binding get() = _binding!!
    
    private val userViewModel: UserViewModel by viewModels()
    private val choreViewModel: ChoreViewModel by viewModels()
    private lateinit var childrenAdapter: ChildrenAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentParentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        setupButtons()
        observeData()
    }
    
    private fun setupRecyclerView() {
        childrenAdapter = ChildrenAdapter { child ->
            // Show child details or payout
            showPayoutDialog(child)
        }
        
        binding.recyclerViewChildren.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = childrenAdapter
        }
    }
    
    private fun setupButtons() {
        binding.buttonReviewChores.setOnClickListener {
            findNavController().navigate(R.id.action_parentDashboard_to_reviewChores)
        }
        
        binding.buttonManageChores.setOnClickListener {
            findNavController().navigate(R.id.action_parentDashboard_to_manageChores)
        }
    }
    
    private fun observeData() {
        userViewModel.childUsers.observe(viewLifecycleOwner) { children ->
            childrenAdapter.submitList(children)
        }
        
        choreViewModel.pendingCompletions.observe(viewLifecycleOwner) { completions ->
            binding.textPendingCount.text = "${completions.size} pending reviews"
        }
    }
    
    private fun showPayoutDialog(child: UserEntity) {
        // Simple confirmation dialog for payout
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setTitle("Pay Out ${child.userName}")
        builder.setMessage("Reset ${child.userName}'s balance to $0.00?")
        builder.setPositiveButton("Yes") { _, _ ->
            userViewModel.payoutChild(child.id)
        }
        builder.setNegativeButton("No", null)
        builder.show()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}