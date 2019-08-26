package com.julien.realestatemanager.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.FileDescriptor

@Entity(foreignKeys = arrayOf(
    ForeignKey(
    entity = Property::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("property_id"))
)
)
data class Media(
    @PrimaryKey(autoGenerate = false) val id: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "photo") val photo: String?,
    @ColumnInfo(name = "property_id") val propertyId: String


)