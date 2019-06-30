package com.julien.realestatemanager.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PropertyViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PropertyRepository
    val allProperties: LiveData<List<Property>>

    init {
        val propertyDao = RealEstateManagerDatabase.getDatabase(application,viewModelScope).propertyDao()
        repository = PropertyRepository(propertyDao)
        allProperties = repository.allProperties
    }

    fun insert(property: Property) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(property)
    }
}