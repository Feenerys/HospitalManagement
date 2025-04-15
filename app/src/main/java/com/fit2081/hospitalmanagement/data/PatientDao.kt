package com.fit2081.hospitalmanagement.data

import kotlinx.coroutines.flow.Flow
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

// Interface defines the data access object (DAO) for the Patient Entity
@Dao
interface PatientDao {
    // suspend is a coroutine function, it can pause and resumed at a later time
    @Insert
    suspend fun insert(patient: Patient)

    @Update
    suspend fun update(patient: Patient)

    @Delete
    suspend fun delete(patient: Patient)

    @Query("DELETE FROM patients")
    suspend fun deleteAllPatients()

    @Query("DELETE FROM patients WHERE id = :patientId")
    suspend fun deletePatientById(patientId: Int)

    // Flow is a data stream that can be observed for changes
    @Query("SELECT * FROM patients ORDER BY id ASC")
    fun getAllPatients(): Flow<List<Patient>>
}