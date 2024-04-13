package com.example.fwafawf.db

import android.content.Context
import androidx.room.Database

import androidx.room.Room.databaseBuilder

import androidx.room.RoomDatabase

@Database(entities = [Pacient::class], version = 1)
abstract class MainDb : RoomDatabase() {
    abstract fun pacientDao(): PacientDao?

    companion object {
        private var INSTANCE: MainDb? = null

        @JvmStatic
        fun getINSTANCE(context: Context): MainDb? {
            return INSTANCE ?: synchronized(this) {
                val instance = databaseBuilder(
                    context.applicationContext,
                    MainDb::class.java,
                    "word_database"
                ).allowMainThreadQueries().build()
                INSTANCE = instance

                instance
            }
        }
    }
}