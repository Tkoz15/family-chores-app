package com.family.choresapp.data.database.entities

import androidx.room.*

@Entity(
    tableName = "chore_completions",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["child_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ChoreEntity::class,
            parentColumns = ["id"],
            childColumns = ["chore_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("child_id"), Index("chore_id")]
)
data class ChoreCompletionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    @ColumnInfo(name = "child_id")
    val childId: Int,
    
    @ColumnInfo(name = "chore_id")
    val choreId: Int,
    
    @ColumnInfo(name = "picture_path")
    val picturePath: String? = null,
    
    @ColumnInfo(name = "amount_earned")
    val amountEarned: Double,
    
    @ColumnInfo(name = "status")
    val status: ChoreStatus,
    
    @ColumnInfo(name = "timestamp")
    val timestamp: Long = System.currentTimeMillis(),
    
    @ColumnInfo(name = "approved_timestamp")
    val approvedTimestamp: Long? = null
)

enum class ChoreStatus {
    IN_PROGRESS,
    COMPLETED,
    APPROVED,
    REJECTED
}