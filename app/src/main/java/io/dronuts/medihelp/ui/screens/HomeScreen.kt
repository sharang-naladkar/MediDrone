package io.dronuts.medihelp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(onStartTracking: (String) -> Unit) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "MediHelp — Home", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(24.dp))
            // Large SOS button placeholder
            Button(onClick = { onStartTracking("demo-incident-123") }, modifier = Modifier
                .size(200.dp)) {
                Text(text = "SOS", style = MaterialTheme.typography.headlineLarge)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { /* open dialer */ }) {
                Text(text = "Call Emergency")
            }
        }
    }
}
