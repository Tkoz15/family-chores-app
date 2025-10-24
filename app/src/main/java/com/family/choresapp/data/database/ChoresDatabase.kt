package com.family.choresapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.family.choresapp.data.database.dao.ChoreCompletionDao
import com.family.choresapp.data.database.dao.ChoreDao
import com.family.choresapp.data.database.dao.UserDao
import com.family.choresapp.data.database.entities.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        UserEntity::class,
        ChoreEntity::class,
        ChoreCompletionEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ChoresDatabase : RoomDatabase() {
    
    abstract fun userDao(): UserDao
    abstract fun choreDao(): ChoreDao
    abstract fun choreCompletionDao(): ChoreCompletionDao
    
    companion object {
        @Volatile
        private var INSTANCE: ChoresDatabase? = null
        
        fun getDatabase(context: Context): ChoresDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ChoresDatabase::class.java,
                    "chores_database"
                )
                    .addCallback(DatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
    
    private class DatabaseCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    populateDatabase(database)
                }
            }
        }
        
        suspend fun populateDatabase(database: ChoresDatabase) {
            val userDao = database.userDao()
            val choreDao = database.choreDao()
            
            // Add default parent user
            userDao.insertUser(
                UserEntity(
                    userName = "Parent",
                    userType = UserType.PARENT,
                    pin = "1234"
                )
            )
            
            // Add sample children
            userDao.insertUser(
                UserEntity(
                    userName = "Sarah",
                    userType = UserType.CHILD
                )
            )
            
            userDao.insertUser(
                UserEntity(
                    userName = "Michael",
                    userType = UserType.CHILD
                )
            )
            
            // Add sample chores
            choreDao.insertChore(ChoreEntity(name = "Clean Bedroom", reward = 5.00))
            choreDao.insertChore(ChoreEntity(name = "Take Out Trash", reward = 2.00))
            choreDao.insertChore(ChoreEntity(name = "Wash Dishes", reward = 3.00))
            choreDao.insertChore(ChoreEntity(name = "Vacuum Living Room", reward = 4.00))
            choreDao.insertChore(ChoreEntity(name = "Feed Pets", reward = 1.50))
            choreDao.insertChore(ChoreEntity(name = "Set Table", reward = 1.00))
            choreDao.insertChore(ChoreEntity(name = "Clear Table", reward = 1.00))
            choreDao.insertChore(ChoreEntity(name = "Water Plants", reward = 2.00))
        }
    }
}