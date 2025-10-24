package com.family.choresapp.ui.child

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.family.choresapp.databinding.FragmentChoreDetailBinding
import com.family.choresapp.viewmodel.ChoreViewModel

class ChoreDetailFragment : Fragment() {
    
    private var _binding: FragmentChoreDetailBinding? = null
    private val binding get() = _binding!!
    
    private val args: ChoreDetailFragmentArgs by navArgs()
    private val choreViewModel: ChoreViewModel by viewModels()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChoreDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupButtons()
        observeData()
        
        // Start the chore immediately
        choreViewModel.startChore(args.childId, args.choreId)
    }
    
    private fun setupButtons() {
        binding.buttonTakePicture.setOnClickListener {
            choreViewModel.currentCompletionId.value?.let { completionId ->
                val action = ChoreDetailFragmentDirections
                    .actionChoreDetailToCamera(completionId.toInt())
                findNavController().navigate(action)
            } ?: run {
                Toast.makeText(context, "Please wait...", Toast.LENGTH_SHORT).show()
            }
        }
        
        binding.buttonCancel.setOnClickListener {
            findNavController().navigateUp()
        }
    }
    
    private fun observeData() {
        choreViewModel.currentCompletionId.observe(viewLifecycleOwner) { completionId ->
            if (completionId != null && completionId > 0) {
                binding.buttonTakePicture.isEnabled = true
                binding.textStatus.text = "Chore started! Take a picture when done."
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