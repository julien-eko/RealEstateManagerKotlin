package com.julien.realestatemanager.models

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

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



    //@Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
    //        "last_name LIKE :last LIMIT 1")
    //fun findByName(first: String, last: String): Property

    //@Insert
    //fun insertAll(vararg propertys: Property)

    //@Insert
    //fun insertProperty(property: Property)

    @Insert
    fun insert(property: Property)

    @Update
    fun updateProperty(property: Property)

    @Delete
    fun delete(property: Property)

    @Query("DELETE FROM property")
    fun deleteAll()
}