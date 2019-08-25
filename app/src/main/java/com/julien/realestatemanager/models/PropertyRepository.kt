package com.julien.realestatemanager.models

import androidx.lifecycle.LiveData
import androidx.annotation.WorkerThread

class PropertyRepository(private val propertyDao: PropertyDao) {

    //get property
    //fun getProperties(propertyId: Int): LiveData<Property> {return propertyDao.getPropertyId(propertyId)}
    fun getProperties(propertyId: String) = propertyDao.getPropertyId(propertyId)



    //Create
    //fun createProperty(property: Property) = propertyDao.insertProperty(property)

    //Delete
    fun deleteProperty(property: Property) = propertyDao.delete(property)

    val allProperties: LiveData<List<Property>> = propertyDao.getAll()

    @WorkerThread
    suspend fun insert(property: Property) {
        propertyDao.insert(property)
    }
}