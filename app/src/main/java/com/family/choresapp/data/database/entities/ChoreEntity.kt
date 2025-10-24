package com.family.choresapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chores")
data class ChoreEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    @ColumnInfo(name = "name")
    val name: String,
    
    @ColumnInfo(name = "reward")
    val reward: Double,
    
    @ColumnInfo(name = "is_active")
    val isActive: Boolean = true
)