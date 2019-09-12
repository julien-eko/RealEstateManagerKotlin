package com.julien.realestatemanager.models

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MediaDao {

    @Query("SELECT * FROM media WHERE property_id = :propertyId")
    fun getALLPhotoPropertyId(propertyId: String): LiveData<List<Media>>

    @Query("SELECT * FROM media WHERE id = :mediaId")
    fun getMediaId(mediaId: String): LiveData<Media>

    @Insert
    fun insert(media: Media)

    @Update
    fun updateMedia(media: Media)

    @Delete
    fun delete(media: Media)

    @Query("DELETE FROM media")
    fun deleteAll()
}