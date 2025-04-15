package com.fit2081.hospitalmanagement.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Database class for the app. A Room Database.
// Contains one entity: [Patient], vers 1, exportSchema is false
@Database(entities = [Patient::class], version = 1, exportSchema = false)
abstract class HospitalDatabase: RoomDatabase() {
    // Returns a [PatientDao] object, abstract function that is implemented by Room
    abstract fun patientDao(): PatientDao

    companion object {
        // Volatile variable that holds database instance
        // Volatile so that it is immediately visible to all threads
        @Volatile
        private var Instance: HospitalDatabase? = null

        // function so that the HospitalDatabase is a singleton, only one instance across the entire
        // app
        fun getDatabase(context: Context): HospitalDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, HospitalDatabase::class.java, "hospital_database")
                    .build().also { Instance = it }
            }
        }
    }
}