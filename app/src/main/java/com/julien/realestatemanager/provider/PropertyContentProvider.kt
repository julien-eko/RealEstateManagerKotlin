package com.julien.realestatemanager.provider

import android.app.Application
import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.content.ContentUris
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.julien.realestatemanager.models.Property
import com.julien.realestatemanager.models.RealEstateManagerDatabase
import kotlinx.coroutines.CoroutineScope


class PropertyContentProvider : ContentProvider() {

    companion object {
        val AUTHORITY = "com.julien.realestatemanager.provider"
        private val PROPERTY_TABLE = "property"
        val CONTENT_URI: Uri = Uri.parse(
            "content://" + AUTHORITY + "/" +
                    PROPERTY_TABLE
        )
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {

        if (context != null) {
            val propertyId = ContentUris.parseId(uri).toString()
            var cursor = RealEstateManagerDatabase.getDatabase(context).propertyDao()
                .getPropertyWithCursor(propertyId)
            cursor.setNotificationUri(context.contentResolver, uri)
            return cursor
        }

        throw IllegalArgumentException("failed to query row for uri" + uri)

    }

    override fun getType(uri: Uri): String? {
        return "vnd.android.cursor.property/" + AUTHORITY + "." + PROPERTY_TABLE
    }


    override fun insert(uri: Uri, values: ContentValues): Uri? {
        if (context != null){
            var property:Long = RealEstateManagerDatabase.getDatabase(context).propertyDao().insert(
                Property.fromContentValues(values)
            )
            if (property != null){
                context.contentResolver.notifyChange(uri, null)
                return ContentUris.withAppendedId(uri,property)
            }
        }

        throw IllegalArgumentException("Failed to insert row into" + uri)
    }

    override fun delete(
        uri: Uri, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        // Implement this to handle requests to delete one or more rows.
        return 0
    }


    override fun update(
        uri: Uri, values: ContentValues, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        if (context != null){
            var count:Int = RealEstateManagerDatabase.getDatabase(context).propertyDao().updateProperty(Property.fromContentValues(values))
            context.contentResolver.notifyChange(uri,null)
            return count
        }
        throw IllegalArgumentException("Failed to update row into" + uri)
    }

}