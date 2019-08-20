package com.julien.realestatemanager.models

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import android.nfc.NfcAdapter
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Property::class,Media::class), version = 1)
abstract class RealEstateManagerDatabase: RoomDatabase() {
    abstract fun propertyDao(): PropertyDao
    abstract fun mediaDao(): MediaDao

    companion object {
        @Volatile
        private var INSTANCE: RealEstateManagerDatabase? = null

        fun getDatabase(context: Context,scope:CoroutineScope): RealEstateManagerDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        RealEstateManagerDatabase::class.java,
                        "Real_estate_database"
                ).addCallback(PropertyDatabaseCallback(scope)).build()
                INSTANCE = instance
                return instance
            }
        }
    }

    private class PropertyDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.propertyDao())
                }
            }
        }

        fun populateDatabase(propertyDao: PropertyDao) {
            //propertyDao.deleteAll()

            //var property1 = Property(1,"house","34","35m²","4","beautifull house","34 place ","champ","status","12/04/2019","non","julien","jolie photo")
            //propertyDao.insert(property1)
            //property1 = Property(2,"house","34","35m²","4","beautifull house","34 place ","champ","status","12/04/2019","non","julien","jolie photo")
            //propertyDao.insert(property1)
        }
    }


}