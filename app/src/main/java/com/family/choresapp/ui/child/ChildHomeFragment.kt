package com.family.choresapp.ui.child

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.family.choresapp.R
import com.family.choresapp.data.database.entities.ChoreEntity
import com.family.choresapp.databinding.FragmentChildHomeBinding
import com.family.choresapp.viewmodel.ChoreViewModel
import com.family.choresapp.viewmodel.UserViewModel

class ChildHomeFragment : Fragment() {
    
    private var _binding: FragmentChildHomeBinding? = null
    private val binding get() = _binding!!
    
    private val args: ChildHomeFragmentArgs by navArgs()
    private val choreViewModel: ChoreViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var choreAdapter: ChoreAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChildHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        setupButtons()
        observeData()
        
        userViewModel.setCurrentUser(args.userId)
    }
    
    private fun setupRecyclerView() {
        choreAdapter = ChoreAdapter { chore ->
            startChore(chore)
        }
        
        binding.recyclerViewChores.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = choreAdapter
        }
    }
    
    private fun setupButtons() {
        binding.buttonMyChores.setOnClickListener {
            val action = ChildHomeFragmentDirections
                .actionChildHomeToEarnings(args.userId)
            findNavController().navigate(action)
        }
        
        binding.buttonEarnings.setOnClickListener {
            val action = ChildHomeFragmentDirections
                .actionChildHomeToEarnings(args.userId)
            findNavController().navigate(action)
        }
    }
    
    private fun observeData() {
        userViewModel.currentUser.observe(viewLifecycleOwner) { user ->
            user?.let {
                binding.textChildName.text = it.userName
            }
        }
        
        userViewModel.userBalance.observe(viewLifecycleOwner) { balance ->
            binding.textEarnings.text = String.format("$%.2f", balance)
        }
        
        choreViewModel.activeChores.observe(viewLifecycleOwner) { chores ->
            choreAdapter.submitList(chores)
            
            if (chores.isEmpty()) {
                binding.textEmptyState.visibility = View.VISIBLE
                binding.recyclerViewChores.visibility = View.GONE
            } else {
                binding.textEmptyState.visibility = View.GONE
                binding.recyclerViewChores.visibility = View.VISIBLE
            }
        }
    }
    
    private fun startChore(chore: ChoreEntity) {
        val action = ChildHomeFragmentDirections
            .actionChildHomeToChoreDetail(chore.id, args.userId)
        findNavController().navigate(action)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}