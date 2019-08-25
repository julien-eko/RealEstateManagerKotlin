package com.julien.realestatemanager.models

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PropertyDao {

    @Query("SELECT * FROM property")
    fun getAll(): LiveData<List<Property>>

    @Query("SELECT * FROM property WHERE id = :propertyId")
    fun getPropertyId(propertyId: String): LiveData<Property>


    @Query("SELECT * FROM property WHERE id IN (:propertyIds)")
    fun loadAllByIds(propertyIds: IntArray): List<Property>

    //@Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
    //        "last_name LIKE :last LIMIT 1")
    //fun findByName(first: String, last: String): Property

    //@Insert
    //fun insertAll(vararg propertys: Property)

    //@Insert
    //fun insertProperty(property: Property)

    @Insert
    fun insert(property: Property)


    @Delete
    fun delete(property: Property)

    @Query("DELETE FROM property")
    fun deleteAll()
}