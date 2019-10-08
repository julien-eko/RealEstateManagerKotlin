package com.julien.realestatemanager

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.julien.realestatemanager.models.Property
import com.julien.realestatemanager.models.RealEstateManagerDatabase
import com.julien.realestatemanager.provider.PropertyContentProvider
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Before
import org.junit.Test

class PropertyContentProviderTest {

    private lateinit var contentResolver:ContentResolver


    @Before
    fun setUp(){
        Room.inMemoryDatabaseBuilder(androidx.test.InstrumentationRegistry.getContext(),RealEstateManagerDatabase::class.java).allowMainThreadQueries().build()

        contentResolver = androidx.test.InstrumentationRegistry.getContext().contentResolver
    }


    @Test
    fun getPropertyWhenPropertyInserted(){
        var cursor = contentResolver.query(ContentUris.withAppendedId(PropertyContentProvider.CONTENT_URI,12),null,null,null,null)
        assertThat(cursor,notNullValue())
        assertThat(cursor.count, `is`(0))
        cursor.close()
    }


    @Test
    fun insertAndGetProperty(){
        val propertyUri = contentResolver.insert(PropertyContentProvider.CONTENT_URI,generateProperty())

        val cursor = contentResolver.query(ContentUris.withAppendedId(PropertyContentProvider.CONTENT_URI,12),null,null,null,null)
        assertThat(cursor, notNullValue())
        //assertThat(cursor.count, `is`(1))
        //assertThat(cursor.moveToFirst(), `is`(true))
        /*
        assertThat(
            cursor.getString(cursor.getColumnIndexOrThrow("type")),
            `is`("Maison")
        )
*/

    }



    private fun generateProperty():ContentValues{
        val contentValues =ContentValues()
        contentValues.put("id","test")
        contentValues.put("city","Soulac-sur-mer")
        contentValues.put("type","Maison")
        contentValues.put("price",99999)
        contentValues.put("area",100)
        contentValues.put("number_of_rooms",10)
        contentValues.put("description","Test content provider")
        contentValues.put("adress","39 rue du test content provider")
        contentValues.put("place_nearby","content provider test")
        contentValues.put("status","Dispo")
        contentValues.put("creation_date",9890909202910)
        contentValues.put("date_of_sale",9990909202910)
        contentValues.put("real_estate_agent","content provider")
        contentValues.put("photo","nop")
        contentValues.put("number_of_bathrooms",10)
        contentValues.put("number_of_bedrooms",10)
        contentValues.put("additional_adress","content provider")
        contentValues.put("postal_code","12345")
        contentValues.put("country","france")
        contentValues.put("latitude",0.0)
        contentValues.put("longitude",0.0)

        return contentValues
    }
}