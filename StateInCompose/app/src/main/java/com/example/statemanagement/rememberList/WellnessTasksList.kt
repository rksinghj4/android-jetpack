package com.example.statemanagement.rememberList

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

internal fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i") }

@Composable
fun WellnessTasksList(
    list: List<WellnessTask>,
    onCloseTask: (WellnessTask) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(items = list, key = {task -> task.id}) { task ->
            WellnessTaskItemWithCheckbox(taskName = task.label, onClose = { onCloseTask(task) } , modifier = modifier)
        }
    }
}