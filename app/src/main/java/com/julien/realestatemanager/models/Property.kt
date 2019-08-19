package com.julien.realestatemanager.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Property(
        @PrimaryKey(autoGenerate = true) val uid: Int,
        @ColumnInfo(name = "city") val city: String?,
        @ColumnInfo(name = "type") val type: String?,
        @ColumnInfo(name = "price") val price: String?,
        @ColumnInfo(name = "area") val area: String?,
        @ColumnInfo(name = "number_of_rooms") val numberOfRooms: String?,
        @ColumnInfo(name = "description") val description: String?,
        @ColumnInfo(name = "adress") val adress: String?,
        @ColumnInfo(name = "place_nearby") val placeNearby: String?,
        @ColumnInfo(name = "status") val status: String?,
        @ColumnInfo(name = "creation_date") val creationDate: String?,
        @ColumnInfo(name = "date_of_sale") val dateOfSale: String?,
        @ColumnInfo(name = "real_estate_agent") val realEstateAgent: String?,
        @ColumnInfo(name = "photo") val photo: ByteArray?



)