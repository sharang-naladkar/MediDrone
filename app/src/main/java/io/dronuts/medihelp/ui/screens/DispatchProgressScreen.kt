package io.dronuts.medihelp.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.dronuts.medihelp.ui.components.StepProgress
import io.dronuts.medihelp.viewmodel.IncidentViewModel

@Composable
fun DispatchProgressScreen(incidentId: String?, onReady: (String) -> Unit, viewModel: IncidentViewModel = hiltViewModel()) {
    // Steps
    val steps = listOf("Trigger", "Location", "Backend", "Dispatch", "Takeoff", "Delivery", "Confirmation")
    var index by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        // Simulate progress
        for (i in 0 until steps.size) {
            index = i
            kotlinx.coroutines.delay(900)
        }
        // simulate getting incident id if not provided
        if (incidentId == null) {
            onReady("demo-${System.currentTimeMillis()}")
        } else {
            onReady(incidentId)
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text(text = "Finding nearest drone...", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(12.dp))
            // Animated progress indicator
            val progress by animateFloatAsState(targetValue = (index + 1).toFloat() / steps.size)
            LinearProgressIndicator(progress = progress, modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(8.dp), color = Color(0xFFFF7043))

            Spacer(modifier = Modifier.height(18.dp))
            StepProgress(steps = steps, currentIndex = index)
        }
    }
}
