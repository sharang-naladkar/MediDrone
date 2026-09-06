package io.dronuts.medihelp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start) {
        Text(text = "Profile", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "User: Demo User")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Phone: +91 90000 00000")
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = { /* Manage emergency contacts */ }) {
            Text(text = "Manage Emergency Contacts")
        }
    }
}
