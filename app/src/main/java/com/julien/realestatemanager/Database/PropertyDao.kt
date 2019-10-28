package com.julien.realestatemanager.Database

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PropertyDao {



    @Query("SELECT * FROM property")
    fun getAll(): LiveData<List<Property>>

    @Query("SELECT * FROM property WHERE id = :propertyId")
    fun getPropertyId(propertyId: String): LiveData<Property>


    @Query("SELECT * FROM property WHERE id IN (:propertyIds)")
    fun loadAllByIds(propertyIds: IntArray): List<Property>

    @Query("SELECT * FROM property WHERE (type LIKE :typeProperty) AND (:minArea < area AND area < :maxArea) AND (:minPrice < price AND price <:maxPrice) AND (:minDateOfCreated < creation_date AND creation_date < :maxDateOfCreated) AND (:minDateOfSale < date_of_sale AND date_of_sale<:maxDateOfSale) AND (status = :statut) AND (city LIKE :city) AND (:minRoom < number_of_rooms AND number_of_rooms < :maxRoom)")
    fun getPropertyResearch(typeProperty: String,
                                     minArea: Int,
                                     maxArea: Int,
                                     minPrice: Int,
                                     maxPrice: Int,
                                     minDateOfSale: Long,
                                     maxDateOfSale: Long,
                                     statut: String,
                                     minDateOfCreated: Long,
                                     maxDateOfCreated: Long,
                                     city:String,
                                     minRoom: Int,
                                     maxRoom: Int
    ): LiveData<List<Property>>


    @Query("SELECT * FROM property WHERE id = :propertyId")
    fun getPropertyWithCursor(propertyId: String):Cursor


    @Insert
    fun insert(property: Property):Long

    @Update
    fun updateProperty(property: Property):Int

    @Delete
    fun delete(property: Property)

    @Query("DELETE FROM property")
    fun deleteAll()
}