package com.julien.realestatemanager.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import android.content.ClipData.Item
import android.content.ContentValues



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



){

        companion object {
                fun fromContentValues(values:ContentValues):Property{
                        return Property(values.getAsString("id"),
                                values.getAsString("city"),
                                values.getAsString("type"),
                                values.getAsInteger("price"),
                                values.getAsInteger("area"),
                                values.getAsInteger("number_of_rooms"),
                                values.getAsString("description"),
                                values.getAsString("adress"),
                                values.getAsString("place_nearby"),
                                values.getAsString("status"),
                                values.getAsLong("creation_date"),
                                values.getAsLong("date_of_sale"),
                                values.getAsString("real_estate_agent"),
                                values.getAsString("photo"),
                                values.getAsInteger("number_of_bathrooms"),
                                values.getAsInteger("number_of_bedrooms"),
                                values.getAsString("additional_adress"),
                                values.getAsString("postal_code"),
                                values.getAsString("country"),
                                values.getAsDouble("latitude"),
                                values.getAsDouble("longitude")
                                )
                }
        }
}