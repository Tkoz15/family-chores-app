package com.family.choresapp.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.family.choresapp.data.database.entities.UserEntity
import com.family.choresapp.data.database.entities.UserType

@Dao
interface UserDao {
    
    @Query("SELECT * FROM users")
    fun getAllUsers(): LiveData<List<UserEntity>>
    
    @Query("SELECT * FROM users WHERE user_type = :userType")
    fun getUsersByType(userType: UserType): LiveData<List<UserEntity>>
    
    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: Int): UserEntity?
    
    @Query("SELECT * FROM users WHERE user_name = :userName AND pin = :pin")
    suspend fun validateParentPin(userName: String, pin: String): UserEntity?
    
    @Insert
    suspend fun insertUser(user: UserEntity)
    
    @Update
    suspend fun updateUser(user: UserEntity)
    
    @Query("UPDATE users SET allowance_balance = :balance WHERE id = :userId")
    suspend fun updateBalance(userId: Int, balance: Double)
    
    @Query("UPDATE users SET allowance_balance = 0 WHERE id = :userId")
    suspend fun resetBalance(userId: Int)
    
    @Delete
    suspend fun deleteUser(user: UserEntity)
}