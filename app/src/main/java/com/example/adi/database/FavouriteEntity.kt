package com.example.adi.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")
data class FavouriteEntity(
    @PrimaryKey(autoGenerate = true)
    val Id: Int = 0,
    val name: String,
    val img: String,
)