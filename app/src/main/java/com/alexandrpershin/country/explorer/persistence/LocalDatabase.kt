package com.alexandrpershin.country.explorer.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alexandrpershin.country.explorer.model.Country
import com.alexandrpershin.country.explorer.persistence.dao.CountryDao

@Database(
    entities = [Country::class],
    version = 17,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun countryDao(): CountryDao

    companion object {
        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun getInstance(context: Context): LocalDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                LocalDatabase::class.java, DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()

        fun destroyInstance() {
            INSTANCE = null
        }


        private const val DATABASE_NAME = "moneybox_db"
    }
}

