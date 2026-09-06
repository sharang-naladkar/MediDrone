package io.dronuts.medihelp.ui.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest
import io.dronuts.medihelp.viewmodel.IncidentViewModel
import io.dronuts.medihelp.viewmodel.TelemetryPoint

@Composable
fun LiveTrackingScreen(incidentId: String, viewModel: IncidentViewModel = hiltViewModel()) {
    val telemetry = remember { mutableStateListOf<TelemetryPoint>() }
    LaunchedEffect(incidentId) {
        viewModel.simulateTelemetryFlow(incidentId).collectLatest { point ->
            telemetry.add(point)
        }
    }

    Scaffold(bottomBar = {
        Surface(shadowElevation = 8.dp, shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)) {
            Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
                Text(text = "Drone status: En route", style = MaterialTheme.typography.titleMedium)
                Text(text = "ETA: ~${(telemetry.size * 6)}s")
            }
        }
    }) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {
            // Placeholder for map: simple canvas drawing of path
            Canvas(modifier = Modifier.fillMaxSize()) {
                if (telemetry.size >= 2) {
                    val path = Path()
                    val w = size.width
                    val h = size.height
                    // map lat/lng to canvas coordinates by normalizing sample bounds
                    val lats = telemetry.map { it.lat }
                    val lngs = telemetry.map { it.lng }
                    val minLat = lats.minOrNull() ?: 0.0
                    val maxLat = lats.maxOrNull() ?: 1.0
                    val minLng = lngs.minOrNull() ?: 0.0
                    val maxLng = lngs.maxOrNull() ?: 1.0
                    fun toPoint(lat: Double, lng: Double): Offset {
                        val x = ((lng - minLng) / (maxLng - minLng).coerceAtLeast(0.000001)) * w
                        val y = (1f - ((lat - minLat) / (maxLat - minLat).coerceAtLeast(0.000001))).toFloat() * h
                        return Offset(x.toFloat(), y.toFloat())
                    }
                    telemetry.forEachIndexed { i, p ->
                        val pt = toPoint(p.lat, p.lng)
                        if (i == 0) path.moveTo(pt.x, pt.y) else path.lineTo(pt.x, pt.y)
                    }
                    drawPath(path = path, color = Color(0xFF1976D2), strokeWidth = 6f)
                    // draw drone at last point
                    val last = telemetry.last()
                    val lastPt = toPoint(last.lat, last.lng)
                    drawCircle(color = Color(0xFFFF7043), radius = 14f, center = lastPt)
                } else {
                    // placeholder center dot
                    drawCircle(color = Color(0xFF90CAF9), radius = 10f, center = center)
                }
            }
        }
    }
}
