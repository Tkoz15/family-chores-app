package com.family.choresapp.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.family.choresapp.R
import com.family.choresapp.databinding.FragmentParentPinBinding
import com.family.choresapp.viewmodel.UserViewModel

class ParentPinFragment : Fragment() {
    
    private var _binding: FragmentParentPinBinding? = null
    private val binding get() = _binding!!
    
    private val userViewModel: UserViewModel by viewModels()
    private var pinInput = ""
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentParentPinBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupPinButtons()
        observePinValidation()
    }
    
    private fun setupPinButtons() {
        // Number buttons
        binding.button0.setOnClickListener { addDigit("0") }
        binding.button1.setOnClickListener { addDigit("1") }
        binding.button2.setOnClickListener { addDigit("2") }
        binding.button3.setOnClickListener { addDigit("3") }
        binding.button4.setOnClickListener { addDigit("4") }
        binding.button5.setOnClickListener { addDigit("5") }
        binding.button6.setOnClickListener { addDigit("6") }
        binding.button7.setOnClickListener { addDigit("7") }
        binding.button8.setOnClickListener { addDigit("8") }
        binding.button9.setOnClickListener { addDigit("9") }
        
        // Clear button
        binding.buttonClear.setOnClickListener {
            pinInput = ""
            updatePinDisplay()
        }
        
        // Submit button
        binding.buttonSubmit.setOnClickListener {
            if (pinInput.length == 4) {
                userViewModel.validateParentPin(pinInput)
            } else {
                Toast.makeText(context, "Please enter a 4-digit PIN", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun addDigit(digit: String) {
        if (pinInput.length < 4) {
            pinInput += digit
            updatePinDisplay()
        }
    }
    
    private fun updatePinDisplay() {
        val display = "•".repeat(pinInput.length) + "○".repeat(4 - pinInput.length)
        binding.textPinDisplay.text = display
    }
    
    private fun observePinValidation() {
        userViewModel.pinValidation.observe(viewLifecycleOwner) { isValid ->
            if (isValid) {
                findNavController().navigate(R.id.action_parentPin_to_parentDashboard)
            } else {
                Toast.makeText(context, "Incorrect PIN", Toast.LENGTH_SHORT).show()
                pinInput = ""
                updatePinDisplay()
            }
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}