package com.fit2081.hospitalmanagement.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// Is a table entity in the database. The name of the table is "patients"
@Entity(tableName = "patients")
data class Patient(
    // Automatically generates a unique key for each patient in the table
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val age: Int,
    val address: String
)
