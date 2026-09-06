package io.dronuts.medihelp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.dronuts.medihelp.data.local.entities.IncidentEntity
import io.dronuts.medihelp.viewmodel.IncidentViewModel

@Composable
fun HistoryScreen(onOpenIncident: (String) -> Unit, viewModel: IncidentViewModel = hiltViewModel()) {
    val items = viewModel.history.collectAsState()
    LaunchedEffect(Unit) { viewModel.loadHistory() }

    LazyColumn(modifier = Modifier.fillMaxSize().padding(12.dp)) {
        items(items.value) { it: IncidentEntity ->
            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable { onOpenIncident(it.id) }) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(text = "Incident: ${it.id}", style = MaterialTheme.typography.titleMedium)
                    Text(text = "Status: ${it.status}")
                    Text(text = "Time: ${java.util.Date(it.timestamp)}")
                }
            }
        }
    }
}
