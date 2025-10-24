package com.family.choresapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.family.choresapp.data.database.ChoresDatabase
import com.family.choresapp.data.database.entities.ChoreCompletionEntity
import com.family.choresapp.data.database.entities.ChoreEntity
import com.family.choresapp.data.repository.ChoresRepository
import kotlinx.coroutines.launch

class ChoreViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository: ChoresRepository
    val activeChores: LiveData<List<ChoreEntity>>
    val pendingCompletions: LiveData<List<ChoreCompletionEntity>>
    
    private val _currentCompletionId = MutableLiveData<Long>()
    val currentCompletionId: LiveData<Long> = _currentCompletionId
    
    private val _operationStatus = MutableLiveData<String>()
    val operationStatus: LiveData<String> = _operationStatus
    
    init {
        val database = ChoresDatabase.getDatabase(application)
        repository = ChoresRepository(database)
        activeChores = repository.getActiveChores()
        pendingCompletions = repository.getPendingCompletions()
    }
    
    fun startChore(childId: Int, choreId: Int) {
        viewModelScope.launch {
            try {
                val completionId = repository.startChore(childId, choreId)
                if (completionId > 0) {
                    _currentCompletionId.value = completionId
                    _operationStatus.value = "Chore started!"
                } else {
                    _operationStatus.value = "Failed to start chore"
                }
            } catch (e: Exception) {
                _operationStatus.value = "Error: ${e.message}"
            }
        }
    }
    
    fun completeChore(completionId: Int, picturePath: String) {
        viewModelScope.launch {
            try {
                repository.completeChore(completionId, picturePath)
                _operationStatus.value = "Chore completed! Waiting for approval."
            } catch (e: Exception) {
                _operationStatus.value = "Error: ${e.message}"
            }
        }
    }
    
    fun approveChore(completionId: Int) {
        viewModelScope.launch {
            try {
                repository.approveChore(completionId)
                _operationStatus.value = "Chore approved!"
            } catch (e: Exception) {
                _operationStatus.value = "Error: ${e.message}"
            }
        }
    }
    
    fun rejectChore(completionId: Int) {
        viewModelScope.launch {
            try {
                repository.rejectChore(completionId)
                _operationStatus.value = "Chore rejected"
            } catch (e: Exception) {
                _operationStatus.value = "Error: ${e.message}"
            }
        }
    }
    
    fun getCompletionsForChild(childId: Int): LiveData<List<ChoreCompletionEntity>> {
        return repository.getCompletionsForChild(childId)
    }
    
    fun addChore(name: String, reward: Double) {
        viewModelScope.launch {
            try {
                repository.insertChore(ChoreEntity(name = name, reward = reward))
                _operationStatus.value = "Chore added successfully"
            } catch (e: Exception) {
                _operationStatus.value = "Error: ${e.message}"
            }
        }
    }
    
    fun updateChore(chore: ChoreEntity) {
        viewModelScope.launch {
            try {
                repository.updateChore(chore)
                _operationStatus.value = "Chore updated successfully"
            } catch (e: Exception) {
                _operationStatus.value = "Error: ${e.message}"
            }
        }
    }
    
    fun deleteChore(chore: ChoreEntity) {
        viewModelScope.launch {
            try {
                repository.deleteChore(chore)
                _operationStatus.value = "Chore deleted successfully"
            } catch (e: Exception) {
                _operationStatus.value = "Error: ${e.message}"
            }
        }
    }
}