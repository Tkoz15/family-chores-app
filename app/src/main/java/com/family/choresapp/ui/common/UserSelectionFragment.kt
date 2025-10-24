package com.family.choresapp.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.family.choresapp.R
import com.family.choresapp.data.database.entities.UserEntity
import com.family.choresapp.data.database.entities.UserType
import com.family.choresapp.databinding.FragmentUserSelectionBinding
import com.family.choresapp.viewmodel.UserViewModel

class UserSelectionFragment : Fragment() {
    
    private var _binding: FragmentUserSelectionBinding? = null
    private val binding get() = _binding!!
    
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        observeUsers()
    }
    
    private fun setupRecyclerView() {
        userAdapter = UserAdapter { user ->
            onUserSelected(user)
        }
        
        binding.recyclerViewUsers.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = userAdapter
        }
    }
    
    private fun observeUsers() {
        userViewModel.childUsers.observe(viewLifecycleOwner) { users ->
            val allUsers = users.toMutableList()
            // Add parent option
            allUsers.add(UserEntity(id = -1, userName = "Parent", userType = UserType.PARENT))
            userAdapter.submitList(allUsers)
        }
    }
    
    private fun onUserSelected(user: UserEntity) {
        if (user.userType == UserType.PARENT) {
            findNavController().navigate(R.id.action_userSelection_to_parentPin)
        } else {
            val action = UserSelectionFragmentDirections
                .actionUserSelectionToChildHome(user.id)
            findNavController().navigate(action)
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}