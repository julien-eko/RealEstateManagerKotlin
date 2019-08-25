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
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "photo") val photo: ByteArray?,
    @ColumnInfo(name = "property_id") val propertyId: String


)