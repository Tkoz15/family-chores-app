package com.family.choresapp.ui.parent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.family.choresapp.data.database.entities.ChoreEntity
import com.family.choresapp.databinding.FragmentManageChoresBinding
import com.family.choresapp.viewmodel.ChoreViewModel

class ManageChoresFragment : Fragment() {
    
    private var _binding: FragmentManageChoresBinding? = null
    private val binding get() = _binding!!
    
    private val choreViewModel: ChoreViewModel by viewModels()
    private lateinit var manageAdapter: ManageChoresAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManageChoresBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        setupButtons()
        observeData()
    }
    
    private fun setupRecyclerView() {
        manageAdapter = ManageChoresAdapter(
            onEdit = { chore -> showEditDialog(chore) },
            onDelete = { chore -> showDeleteDialog(chore) }
        )
        
        binding.recyclerViewChores.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = manageAdapter
        }
    }
    
    private fun setupButtons() {
        binding.fabAddChore.setOnClickListener {
            showAddDialog()
        }
    }
    
    private fun observeData() {
        choreViewModel.activeChores.observe(viewLifecycleOwner) { chores ->
            manageAdapter.submitList(chores)
        }
        
        choreViewModel.operationStatus.observe(viewLifecycleOwner) { status ->
            Toast.makeText(context, status, Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun showAddDialog() {
        val dialogView = LayoutInflater.from(context).inflate(
            android.R.layout.simple_list_item_2, null
        )
        
        val editName = EditText(context).apply {
            hint = "Chore name"
        }
        val editReward = EditText(context).apply {
            hint = "Reward amount"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
        }
        
        val layout = android.widget.LinearLayout(context).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(50, 50, 50, 50)
            addView(editName)
            addView(editReward)
        }
        
        AlertDialog.Builder(requireContext())
            .setTitle("Add New Chore")
            .setView(layout)
            .setPositiveButton("Add") { _, _ ->
                val name = editName.text.toString().trim()
                val rewardText = editReward.text.toString().trim()
                
                if (name.isNotEmpty() && rewardText.isNotEmpty()) {
                    try {
                        val reward = rewardText.toDouble()
                        choreViewModel.addChore(name, reward)
                    } catch (e: NumberFormatException) {
                        Toast.makeText(context, "Invalid reward amount", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showEditDialog(chore: ChoreEntity) {
        val editName = EditText(context).apply {
            setText(chore.name)
        }
        val editReward = EditText(context).apply {
            setText(chore.reward.toString())
            inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
        }
        
        val layout = android.widget.LinearLayout(context).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(50, 50, 50, 50)
            addView(editName)
            addView(editReward)
        }
        
        AlertDialog.Builder(requireContext())
            .setTitle("Edit Chore")
            .setView(layout)
            .setPositiveButton("Save") { _, _ ->
                val name = editName.text.toString().trim()
                val rewardText = editReward.text.toString().trim()
                
                if (name.isNotEmpty() && rewardText.isNotEmpty()) {
                    try {
                        val reward = rewardText.toDouble()
                        val updatedChore = chore.copy(name = name, reward = reward)
                        choreViewModel.updateChore(updatedChore)
                    } catch (e: NumberFormatException) {
                        Toast.makeText(context, "Invalid reward amount", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showDeleteDialog(chore: ChoreEntity) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Chore")
            .setMessage("Are you sure you want to delete '${chore.name}'?")
            .setPositiveButton("Delete") { _, _ ->
                choreViewModel.deleteChore(chore)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}