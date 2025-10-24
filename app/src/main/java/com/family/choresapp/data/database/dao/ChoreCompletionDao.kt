package com.family.choresapp.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.family.choresapp.data.database.entities.ChoreCompletionEntity
import com.family.choresapp.data.database.entities.ChoreStatus

@Dao
interface ChoreCompletionDao {
    
    @Query("SELECT * FROM chore_completions WHERE child_id = :childId ORDER BY timestamp DESC")
    fun getCompletionsForChild(childId: Int): LiveData<List<ChoreCompletionEntity>>
    
    @Query("SELECT * FROM chore_completions WHERE status = :status ORDER BY timestamp DESC")
    fun getCompletionsByStatus(status: ChoreStatus): LiveData<List<ChoreCompletionEntity>>
    
    @Query("SELECT * FROM chore_completions WHERE status = 'COMPLETED' ORDER BY timestamp DESC")
    fun getPendingCompletions(): LiveData<List<ChoreCompletionEntity>>
    
    @Query("SELECT * FROM chore_completions WHERE child_id = :childId AND status = 'IN_PROGRESS'")
    suspend fun getInProgressForChild(childId: Int): List<ChoreCompletionEntity>
    
    @Query("SELECT * FROM chore_completions WHERE id = :completionId")
    suspend fun getCompletionById(completionId: Int): ChoreCompletionEntity?
    
    @Query("SELECT SUM(amount_earned) FROM chore_completions WHERE child_id = :childId AND status = 'APPROVED'")
    suspend fun getTotalEarnedForChild(childId: Int): Double?
    
    @Insert
    suspend fun insertCompletion(completion: ChoreCompletionEntity): Long
    
    @Update
    suspend fun updateCompletion(completion: ChoreCompletionEntity)
    
    @Query("UPDATE chore_completions SET status = :status, approved_timestamp = :timestamp WHERE id = :completionId")
    suspend fun updateCompletionStatus(completionId: Int, status: ChoreStatus, timestamp: Long? = null)
    
    @Query("UPDATE chore_completions SET picture_path = :path WHERE id = :completionId")
    suspend fun updatePicturePath(completionId: Int, path: String)
    
    @Delete
    suspend fun deleteCompletion(completion: ChoreCompletionEntity)
    
    @Query("DELETE FROM chore_completions WHERE child_id = :childId AND status = 'APPROVED'")
    suspend fun clearApprovedCompletions(childId: Int)
}