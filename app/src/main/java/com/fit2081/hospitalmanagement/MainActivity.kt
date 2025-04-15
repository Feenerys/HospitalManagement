package com.fit2081.hospitalmanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.fit2081.hospitalmanagement.data.PatientViewModel
import com.fit2081.hospitalmanagement.ui.theme.HospitalManagementTheme

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
                    AddPatientScreen(innerPadding, viewModel)
                }
            }
        }
    }
}

@Composable
fun AddPatientScreen(innerPadding: PaddingValues, viewModel: PatientViewModel) {
    var patientName by remember { mutableStateOf(TextFieldValue(""))}
    var patientAge by remember {mutableStateOf(TextFieldValue(""))}
    var patientAddress by remember {mutableStateOf(TextFieldValue(""))}
    // Use state so that when any change in data occurs the list is updated
    val numberOfPatients by viewModel.allPatients.collectAsState(initial = emptyList())

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


    }

}