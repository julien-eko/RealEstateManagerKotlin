package com.julien.realestatemanager.Database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class MediaRepository(private val mediaDao: MediaDao) {

    fun getMedia(propertyId: String): LiveData<List<Media>> =
        mediaDao.getALLPhotoPropertyId(propertyId)

    fun getMediaId(mediaId: String) = mediaDao.getMediaId(mediaId)

    //Delete
    fun deleteMedia(media: Media) = mediaDao.delete(media)


    fun updateMedia(media: Media) = mediaDao.updateMedia(media)

    @WorkerThread
    suspend fun insert(media: Media) {
        mediaDao.insert(media)
    }
}