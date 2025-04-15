package com.fit2081.hospitalmanagement

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.fit2081.hospitalmanagement.data.Patient
import com.fit2081.hospitalmanagement.data.PatientViewModel
import com.fit2081.hospitalmanagement.ui.theme.HospitalManagementTheme
import kotlinx.coroutines.flow.Flow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HospitalManagementTheme {
                // Obtains an instance of the ViewModel
                val viewModel: PatientViewModel = ViewModelProvider(
                    this, PatientViewModel.PatientViewModelFactory(this@MainActivity)
                )[PatientViewModel::class.java]
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AddPatientScreen(this@MainActivity, innerPadding, viewModel)
                }
            }
        }
    }
}

@Composable
fun AddPatientScreen(context: Context, innerPadding: PaddingValues, viewModel: PatientViewModel) {
    var patientName by remember { mutableStateOf(TextFieldValue(""))}
    var patientAge by remember {mutableStateOf(TextFieldValue(""))}
    var patientAddress by remember {mutableStateOf(TextFieldValue(""))}
    // Use state so that when any change in data occurs the list is updated
    val listOfPatients by viewModel.allPatients.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Hospital Management",
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        PatientInputFields(patientName, patientAge, patientAddress,
            onNameChange = {patientName = it},
            onAgeChange = {patientAge = it},
            onAddressChange = {patientAddress = it})

        FormButtons(
            onAdd = {
                viewModel.insert(
                    Patient(
                        name = patientName.text,
                        age = patientAge.text.toInt(),
                        address = patientAddress.text
                    )
                )
            },
            onDeleteAll = { viewModel.deleteAllPatients() }
        )

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(thickness = 4.dp)
        Spacer(modifier = Modifier.height(16.dp))

        PatientList(listOfPatients, context, viewModel)
    }
}

@Composable
fun PatientInputFields(
    patientName: TextFieldValue,
    patientAge: TextFieldValue,
    patientAddress: TextFieldValue,
    onNameChange: (TextFieldValue) -> Unit,
    onAgeChange: (TextFieldValue) -> Unit,
    onAddressChange: (TextFieldValue) -> Unit
) {
    OutlinedTextField(
        value = patientName,
        onValueChange = onNameChange,
        label = { Text("Patient Name") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    )

    OutlinedTextField(
        value = patientAge,
        onValueChange = onAgeChange,
        label = { Text("Patient Age") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )

    OutlinedTextField(
        value = patientAddress,
        onValueChange = onAddressChange,
        label = { Text("Patient Address") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    )
}

@Composable
fun FormButtons(
    onAdd: () -> Unit,
    onDeleteAll: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(onClick = onAdd) {
            Text("Add Patient")
        }
        Button(
            onClick = onDeleteAll
        ) { Text(text = "Delete All Patients") }
    }
}

@Composable
fun PatientList(patients: List<Patient>, context: Context, viewModel: PatientViewModel) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(patients) { patient ->
            PatientCard(patient = patient, context, {
                viewModel.deletePatientById(patient.id)
            })
        }
    }
}

@Composable
fun PatientCard(patient: Patient, context: Context, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .height(100.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp))
    {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            ){
                Text("Name: ${patient.name}", fontWeight = FontWeight.Bold)
                Text("Age: ${patient.age}")
                Text("Address: ${patient.address}")
            }

            Column {
                IconButton(onClick = onDelete) {
                    Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = Color.Red)
                }

            }
        }
    }
}