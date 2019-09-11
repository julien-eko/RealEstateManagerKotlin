package com.julien.realestatemanager.models

import androidx.lifecycle.LiveData
import androidx.annotation.WorkerThread
import java.util.*

class PropertyRepository(private val propertyDao: PropertyDao) {

    //get property
    //fun getProperties(propertyId: Int): LiveData<Property> {return propertyDao.getPropertyId(propertyId)}
    fun getProperties(propertyId: String) = propertyDao.getPropertyId(propertyId)

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
    )=propertyDao.getPropertyResearch(typeProperty,minArea,maxArea,minPrice,maxPrice,minDateOfSale,maxDateOfSale,statut,minDateOfCreated,maxDateOfCreated,city,minRoom,maxRoom)



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