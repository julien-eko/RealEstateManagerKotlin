package com.julien.realestatemanager.Database

import androidx.lifecycle.LiveData
import androidx.annotation.WorkerThread

class PropertyRepository(private val propertyDao: PropertyDao) {

    fun getProperties(propertyId: String) = propertyDao.getPropertyId(propertyId)

    fun getPropertyResearch(
        typeProperty: String,
        minArea: Int,
        maxArea: Int,
        minPrice: Int,
        maxPrice: Int,
        minDateOfSale: Long,
        maxDateOfSale: Long,
        statut: String,
        minDateOfCreated: Long,
        maxDateOfCreated: Long,
        city: String,
        minRoom: Int,
        maxRoom: Int
    ) = propertyDao.getPropertyResearch(
        typeProperty,
        minArea,
        maxArea,
        minPrice,
        maxPrice,
        minDateOfSale,
        maxDateOfSale,
        statut,
        minDateOfCreated,
        maxDateOfCreated,
        city,
        minRoom,
        maxRoom
    )

    fun updateProperty(property: Property) = propertyDao.updateProperty(property)


    //Delete
    fun deleteProperty(property: Property) = propertyDao.delete(property)

    val allProperties: LiveData<List<Property>> = propertyDao.getAll()

    @WorkerThread
    fun insert(property: Property) {
        propertyDao.insert(property)
    }
}