package com.julien.realestatemanager.models

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

    fun getProperty(id:String):LiveData<Property> = propertyRepository.getProperties(id)

    fun getAllProperty(): LiveData<List<Property>> = propertyRepository.allProperties



    //-------------
    // Media
    //-------------

    fun insertMedia(media: Media) = viewModelScope.launch(Dispatchers.IO) {
        mediaRepository.insert(media)
    }

    fun getMedia(id:String):LiveData<List<Media>> = mediaRepository.getMedia(id)

    fun getMediaId(id:String):LiveData<Media> = mediaRepository.getMediaId(id)
}