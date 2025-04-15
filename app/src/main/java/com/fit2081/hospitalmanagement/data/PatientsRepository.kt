package com.fit2081.hospitalmanagement.data

import android.content.Context
import kotlinx.coroutines.flow.Flow

// Repository, mediator between Room Database and ViewModel
class PatientsRepository {
    var patientDao: PatientDao

    constructor(context: Context){
        patientDao = HospitalDatabase.getDatabase(context).patientDao()
    }

    suspend fun insert(patient: Patient){
        patientDao.insert(patient)
    }

    suspend fun delete(patient: Patient){
        patientDao.delete(patient)
    }

    suspend fun update(patient: Patient){
        patientDao.update(patient)
    }

    suspend fun deleteAllPatients() {
        patientDao.deleteAllPatients()
    }

    fun getAllPatients(): Flow<List<Patient>> = patientDao.getAllPatients()
}