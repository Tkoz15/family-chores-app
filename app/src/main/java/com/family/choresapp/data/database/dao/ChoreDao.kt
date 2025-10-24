package com.family.choresapp.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.family.choresapp.data.database.entities.ChoreEntity

@Dao
interface ChoreDao {
    
    @Query("SELECT * FROM chores WHERE is_active = 1 ORDER BY name")
    fun getActiveChores(): LiveData<List<ChoreEntity>>
    
    @Query("SELECT * FROM chores ORDER BY name")
    fun getAllChores(): LiveData<List<ChoreEntity>>
    
    @Query("SELECT * FROM chores WHERE id = :choreId")
    suspend fun getChoreById(choreId: Int): ChoreEntity?
    
    @Insert
    suspend fun insertChore(chore: ChoreEntity)
    
    @Update
    suspend fun updateChore(chore: ChoreEntity)
    
    @Delete
    suspend fun deleteChore(chore: ChoreEntity)
    
    @Query("UPDATE chores SET is_active = :isActive WHERE id = :choreId")
    suspend fun setChoreActive(choreId: Int, isActive: Boolean)
}