package com.example.fwafawf.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Pacient(
    @JvmField @field:ColumnInfo(name = "name") var name: String, @JvmField @field:ColumnInfo(
        name = "age"
    ) var age: Int
) {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @JvmField
    @ColumnInfo(name = "imt")
    var imt = 0.0

    @JvmField
    @ColumnInfo(name = "nf")
    var nf = 0.0

    @JvmField
    @ColumnInfo(name = "cap")
    var cap = 0.0

    @JvmField
    @ColumnInfo(name = "def")
    var def = 0.0
}