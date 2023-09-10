package com.example.adi.domain

import com.example.adi.database.FavouriteEntity
import com.example.adi.database.WarriorEntity
import com.example.adi.models.King
import com.example.adi.network.ApiResponse

/*
    Mapper functions used in caching
 */

//  DTO -> Database Object
fun ApiResponse<List<King>>.asDataBaseModel(): List<WarriorEntity> {
    return this.dataBody.map {
        WarriorEntity(
            name = it.name,
            img = it.img,
            Id = it.Id
        )
    }
}



// Bookmarks Mnapper functions
fun King.asDataBaseModel(): FavouriteEntity {
    return FavouriteEntity(
        name = this.name,
        img = this.img
    )
}

fun List<FavouriteEntity>.asDomainsModel(): List<King> {
    return map {
        King(
            name = it.name,
            img = it.img,
            Id = 0,
            intro = ""
        )
    }
}
