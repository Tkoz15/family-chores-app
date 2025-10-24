package com.family.choresapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.family.choresapp.data.database.ChoresDatabase
import com.family.choresapp.data.database.entities.UserEntity
import com.family.choresapp.data.database.entities.UserType
import com.family.choresapp.data.repository.ChoresRepository
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository: ChoresRepository
    val childUsers: LiveData<List<UserEntity>>
    
    private val _currentUser = MutableLiveData<UserEntity?>()
    val currentUser: LiveData<UserEntity?> = _currentUser
    
    private val _pinValidation = MutableLiveData<Boolean>()
    val pinValidation: LiveData<Boolean> = _pinValidation
    
    private val _userBalance = MutableLiveData<Double>()
    val userBalance: LiveData<Double> = _userBalance
    
    private val _operationStatus = MutableLiveData<String>()
    val operationStatus: LiveData<String> = _operationStatus
    
    init {
        val database = ChoresDatabase.getDatabase(application)
        repository = ChoresRepository(database)
        childUsers = repository.getChildUsers()
    }
    
    fun setCurrentUser(userId: Int) {
        viewModelScope.launch {
            try {
                val user = repository.getUserById(userId)
                _currentUser.value = user
                if (user != null) {
                    updateBalance(userId)
                }
            } catch (e: Exception) {
                _operationStatus.value = "Error: ${e.message}"
            }
        }
    }
    
    fun validateParentPin(pin: String) {
        viewModelScope.launch {
            try {
                val parentUser = repository.validateParentPin("Parent", pin)
                _pinValidation.value = parentUser != null
                if (parentUser != null) {
                    _currentUser.value = parentUser
                }
            } catch (e: Exception) {
                _pinValidation.value = false
                _operationStatus.value = "Error: ${e.message}"
            }
        }
    }
    
    fun updateBalance(userId: Int) {
        viewModelScope.launch {
            try {
                val balance = repository.getTotalEarnedForChild(userId)
                _userBalance.value = balance
            } catch (e: Exception) {
                _userBalance.value = 0.0
            }
        }
    }
    
    fun payoutChild(childId: Int) {
        viewModelScope.launch {
            try {
                repository.payoutChild(childId)
                _operationStatus.value = "Balance reset successfully"
                updateBalance(childId)
            } catch (e: Exception) {
                _operationStatus.value = "Error: ${e.message}"
            }
        }
    }
}