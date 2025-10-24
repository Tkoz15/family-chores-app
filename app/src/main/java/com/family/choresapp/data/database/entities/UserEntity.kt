package com.family.choresapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    @ColumnInfo(name = "user_name")
    val userName: String,
    
    @ColumnInfo(name = "allowance_balance")
    val allowanceBalance: Double = 0.0,
    
    @ColumnInfo(name = "user_type")
    val userType: UserType,
    
    @ColumnInfo(name = "pin")
    val pin: String? = null // Only for parents
)

enum class UserType {
    CHILD,
    PARENT
}