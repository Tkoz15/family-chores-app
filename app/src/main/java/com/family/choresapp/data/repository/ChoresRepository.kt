package com.family.choresapp.data.repository

import androidx.lifecycle.LiveData
import com.family.choresapp.data.database.ChoresDatabase
import com.family.choresapp.data.database.entities.*

class ChoresRepository(private val database: ChoresDatabase) {
    
    private val userDao = database.userDao()
    private val choreDao = database.choreDao()
    private val completionDao = database.choreCompletionDao()
    
    // User operations
    fun getAllUsers() = userDao.getAllUsers()
    fun getChildUsers() = userDao.getUsersByType(UserType.CHILD)
    fun getParentUsers() = userDao.getUsersByType(UserType.PARENT)
    suspend fun getUserById(userId: Int) = userDao.getUserById(userId)
    suspend fun validateParentPin(userName: String, pin: String) = userDao.validateParentPin(userName, pin)
    suspend fun updateUserBalance(userId: Int, balance: Double) = userDao.updateBalance(userId, balance)
    suspend fun resetUserBalance(userId: Int) = userDao.resetBalance(userId)
    
    // Chore operations
    fun getActiveChores() = choreDao.getActiveChores()
    fun getAllChores() = choreDao.getAllChores()
    suspend fun getChoreById(choreId: Int) = choreDao.getChoreById(choreId)
    suspend fun insertChore(chore: ChoreEntity) = choreDao.insertChore(chore)
    suspend fun updateChore(chore: ChoreEntity) = choreDao.updateChore(chore)
    suspend fun deleteChore(chore: ChoreEntity) = choreDao.deleteChore(chore)
    
    // Completion operations
    fun getCompletionsForChild(childId: Int) = completionDao.getCompletionsForChild(childId)
    fun getPendingCompletions() = completionDao.getPendingCompletions()
    suspend fun getInProgressForChild(childId: Int) = completionDao.getInProgressForChild(childId)
    suspend fun getCompletionById(completionId: Int) = completionDao.getCompletionById(completionId)
    suspend fun getTotalEarnedForChild(childId: Int) = completionDao.getTotalEarnedForChild(childId) ?: 0.0
    
    suspend fun startChore(childId: Int, choreId: Int): Long {
        val chore = choreDao.getChoreById(choreId) ?: return -1
        
        val completion = ChoreCompletionEntity(
            childId = childId,
            choreId = choreId,
            amountEarned = chore.reward,
            status = ChoreStatus.IN_PROGRESS
        )
        
        return completionDao.insertCompletion(completion)
    }
    
    suspend fun completeChore(completionId: Int, picturePath: String) {
        completionDao.updatePicturePath(completionId, picturePath)
        completionDao.updateCompletionStatus(completionId, ChoreStatus.COMPLETED)
    }
    
    suspend fun approveChore(completionId: Int) {
        val completion = completionDao.getCompletionById(completionId) ?: return
        val user = userDao.getUserById(completion.childId) ?: return
        
        completionDao.updateCompletionStatus(
            completionId, 
            ChoreStatus.APPROVED, 
            System.currentTimeMillis()
        )
        
        val newBalance = user.allowanceBalance + completion.amountEarned
        userDao.updateBalance(completion.childId, newBalance)
    }
    
    suspend fun rejectChore(completionId: Int) {
        completionDao.updateCompletionStatus(completionId, ChoreStatus.REJECTED)
    }
    
    suspend fun payoutChild(childId: Int) {
        userDao.resetBalance(childId)
        completionDao.clearApprovedCompletions(childId)
    }
}