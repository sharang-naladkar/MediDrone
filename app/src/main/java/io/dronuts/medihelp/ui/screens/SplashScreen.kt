package io.dronuts.medihelp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    // Simple splash: show for 1.2s then navigate
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
    // In the real app we would use LaunchedEffect with delay and animation
    // For scaffold stage we'll call onTimeout from the Activity in a moment; keep simple for tests
    // TODO: add animation and branding
    onTimeout()
}
