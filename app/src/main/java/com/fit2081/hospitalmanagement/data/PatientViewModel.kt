package com.fit2081.hospitalmanagement.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PatientViewModel(context: Context) : ViewModel() {
    private val patientRepo = PatientsRepository(context)

    val allPatients: Flow<List<Patient>> = patientRepo.getAllPatients()

    fun insert(patient: Patient) = viewModelScope.launch {
        patientRepo.insert(patient)
    }

    fun delete() = viewModelScope.launch {
        patientRepo.deleteAllPatients()
    }

    fun update(patient: Patient) = viewModelScope.launch {
        patientRepo.update(patient)
    }

    fun deleteAllPatients() = viewModelScope.launch {
        patientRepo.deleteAllPatients()
    }

    // ViewModel factory that sets the context for the viewmodel and the
    // ViewModelProvider.Factory interface is used to create new viewmodels
    class PatientViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            PatientViewModel(context) as T
    }
}