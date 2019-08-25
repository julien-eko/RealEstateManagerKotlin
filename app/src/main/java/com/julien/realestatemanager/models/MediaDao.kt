package com.julien.realestatemanager.models

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MediaDao {

    @Query("SELECT * FROM media WHERE property_id = :propertyId")
    fun getALLPhotoPropertyId(propertyId: String): LiveData<List<Media>>

    @Insert
    fun insert(media: Media)

    @Delete
    fun delete(media: Media)

    @Query("DELETE FROM media")
    fun deleteAll()
}