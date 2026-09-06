package io.dronuts.medihelp.ui.screens

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.dronuts.medihelp.viewmodel.IncidentViewModel

@Composable
fun HomeScreen(onStartTracking: (String) -> Unit, onOpenHistory: () -> Unit, onOpenProfile: () -> Unit, viewModel: IncidentViewModel = hiltViewModel()) {
    val context = LocalContext.current
    var showingConfirm by remember { mutableStateOf(false) }
    var isTriggering by remember { mutableStateOf(false) }

    val requestPermissionLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(), onResult = { granted ->
        if (granted) {
            showingConfirm = true
        } else {
            // show rationale or fallback
        }
    })

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "MediHelp — Home", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(24.dp))
            // Large SOS button
            Button(onClick = {
                // request location permission first
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }, modifier = Modifier
                .size(200.dp)) {
                Text(text = "SOS", style = MaterialTheme.typography.headlineLarge)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { 
                // open dialer
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"112"))
                context.startActivity(intent)
            }) {
                Text(text = "Call Emergency")
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(onClick = onOpenHistory) { Text(text = "History") }
                Button(onClick = onOpenProfile) { Text(text = "Profile") }
            }
        }
    }

    if (showingConfirm) {
        AlertDialog(onDismissRequest = { showingConfirm = false }, confirmButton = {
            TextButton(onClick = {
                showingConfirm = false
                isTriggering = true
                // Use mock coordinates for demo
                viewModel.triggerEmergency(12.9716, 77.5946, "medical") { res ->
                    isTriggering = false
                    res.onSuccess { id -> onStartTracking(id) }
                }
            }) { Text(text = "Confirm") }
        }, dismissButton = {
            TextButton(onClick = { showingConfirm = false }) { Text(text = "Cancel") }
        }, title = { Text(text = "Confirm emergency at:\nDemo Location") }, text = { Text(text = "A drone will be dispatched to your current location. Proceed?") })
    }

    if (isTriggering) {
        // show blocking progress
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Card(modifier = Modifier.wrapContentSize(), elevation = CardDefaults.cardElevation(8.dp)) {
                Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "Triggering emergency...")
                }
            }
        }
    }
}
