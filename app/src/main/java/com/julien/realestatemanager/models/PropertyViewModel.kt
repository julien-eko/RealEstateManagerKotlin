package com.julien.realestatemanager.models

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class PropertyViewModel(application: Application) : AndroidViewModel(application) {
    private val propertyRepository: PropertyRepository
    private val mediaRepository: MediaRepository
    val allProperties: LiveData<List<Property>>
    //val allMedia:LiveData<List<Media>>

    init {
        val propertyDao = RealEstateManagerDatabase.getDatabase(application,viewModelScope).propertyDao()
        val mediaDao = RealEstateManagerDatabase.getDatabase(application,viewModelScope).mediaDao()
        propertyRepository = PropertyRepository(propertyDao)
        mediaRepository = MediaRepository(mediaDao)
        allProperties = propertyRepository.allProperties
        //allMedia = mediaRepository.getMedia()
    }

    //---------------
    // Property
    //--------------

    fun insert(property: Property) = viewModelScope.launch(Dispatchers.IO) {
        propertyRepository.insert(property)

    }

    fun updateProperty(property: Property) =viewModelScope.launch(Dispatchers.IO) {
        propertyRepository.updateProperty(property)
    }
    fun getProperty(id:String):LiveData<Property> = propertyRepository.getProperties(id)

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
    )=propertyRepository.getPropertyResearch(typeProperty,minArea,maxArea,minPrice,maxPrice,minDateOfSale,maxDateOfSale,statut,minDateOfCreated,maxDateOfCreated,city,minRoom,maxRoom)

    fun getAllProperty(): LiveData<List<Property>> = propertyRepository.allProperties



    //-------------
    // Media
    //-------------

    fun insertMedia(media: Media) = viewModelScope.launch(Dispatchers.IO) {
        mediaRepository.insert(media)
    }

    fun updateMedia(media: Media) =viewModelScope.launch(Dispatchers.IO) {
        mediaRepository.updateMedia(media)
    }
        fun getMedia(id:String):LiveData<List<Media>> = mediaRepository.getMedia(id)

        fun getMediaId(id:String):LiveData<Media> = mediaRepository.getMediaId(id)

}