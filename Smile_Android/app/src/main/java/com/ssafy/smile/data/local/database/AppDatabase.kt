package com.ssafy.smile.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ssafy.smile.data.local.database.dao.AddressDao
import com.ssafy.smile.data.local.database.entity.AddressEntity

@Database(entities = [AddressEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun addressDao() : AddressDao

    companion object{
        private var INSTANCE: AppDatabase?= null

        fun getDatabase(context: Context) : AppDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "SMILE"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}
