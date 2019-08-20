package com.julien.realestatemanager.models

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class MediaRepository(private val mediaDao: MediaDao) {

    fun getMedia(propertyId: Int):LiveData<List<Media>> = mediaDao.getALLPhotoPropertyId(propertyId)

    //Delete
    fun deleteMedia(media: Media) = mediaDao.delete(media)

    //val allMedia: LiveData<List<Property>> = mediaDao.getAll()

    @WorkerThread
    suspend fun insert(media: Media) {
        mediaDao.insert(media)
    }
}