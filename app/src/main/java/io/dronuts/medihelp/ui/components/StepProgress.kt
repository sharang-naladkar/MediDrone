package io.dronuts.medihelp.ui.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun StepProgress(steps: List<String>, currentIndex: Int) {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        steps.forEachIndexed { idx, title ->
            val active = idx <= currentIndex
            val transition = updateTransition(targetState = active, label = "stepTransition")
            val color by transition.animateColor(label = "colorAnim") { state ->
                if (state) Color(0xFF0077CC) else Color(0xFFBDBDBD)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier
                    .size(28.dp)
                    .background(color, shape = CircleShape))
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = title, color = color, style = MaterialTheme.typography.bodyLarge)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
