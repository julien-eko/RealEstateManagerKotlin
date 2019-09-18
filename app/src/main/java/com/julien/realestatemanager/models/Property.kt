package com.julien.realestatemanager.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Property(
        @PrimaryKey(autoGenerate = false) val id: String,
        @ColumnInfo(name = "city") val city: String?,
        @ColumnInfo(name = "type") val type: String?,
        @ColumnInfo(name = "price") val price: Int,
        @ColumnInfo(name = "area") val area: Int,
        @ColumnInfo(name = "number_of_rooms") val numberOfRooms: Int,
        @ColumnInfo(name = "description") val description: String?,
        @ColumnInfo(name = "adress") val adress: String?,
        @ColumnInfo(name = "place_nearby") val placeNearby: String?,
        @ColumnInfo(name = "status") val status: String?,
        @ColumnInfo(name = "creation_date") val creationDate: Long,
        @ColumnInfo(name = "date_of_sale") val dateOfSale: Long,
        @ColumnInfo(name = "real_estate_agent") val realEstateAgent: String?,
        @ColumnInfo(name = "photo") val photo: String?,
        @ColumnInfo(name= "number_of_bathrooms") val numberOfBathrooms: Int,
        @ColumnInfo(name = "number_of_bedrooms") val numberOfBedrooms: Int,
        @ColumnInfo(name = "additional_adress") val additionalAdress: String?,
        @ColumnInfo(name = "postal_code") val postalCode:String?,
        @ColumnInfo(name = "country") val country:String?,
        @ColumnInfo(name = "latitude") val latitude:Double,
        @ColumnInfo(name = "longitude")val longitude:Double



)