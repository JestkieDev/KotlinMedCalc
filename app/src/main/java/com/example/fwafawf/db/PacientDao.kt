package com.example.fwafawf.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PacientDao {


    @get:Query("SELECT * FROM pacient")
    val all: List<Pacient>?

    @Query("SELECT * FROM pacient WHERE name LIKE :search")
    fun getWithName(search: String?): List<Pacient?>?

    @Query("SELECT * FROM pacient WHERE id = :id")
    fun getById(id: Long): Pacient?

    @Insert
    fun insert(pacient: Pacient)

    @Update
    fun update(pacient: Pacient)

    @Delete
    fun delete(pacient: Pacient)
}