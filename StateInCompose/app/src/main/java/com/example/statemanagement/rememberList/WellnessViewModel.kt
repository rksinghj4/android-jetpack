package com.example.statemanagement.rememberList

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

internal fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i") }

class WellnessViewModel : ViewModel() {

    //Note: SnapshotStateList can't be save with rememberSaveable.
    // So it's always better to use viewModel
    /*
     * By using toMutableStateList()
     * SnapshotStateList is observable by compose for any addition and deletion in list
     * but not for deep update in item (WellnessTask) of the list.
     *
     * If we want observe any deep update in list item then
     * that particular field should also be observable (e.g checked in WellnessTask).
     *
     * Note: If we replace toMutableStateList() with toMutableList()
     * then addition/deletion will not be observe by the compose.
     */
    private val _tasks = getWellnessTasks().toMutableStateList()

    val tasks: List<WellnessTask>
        get() = _tasks

    fun remove(item: WellnessTask) {
        _tasks.remove(item)
    }

    fun changeTaskChecked(item: WellnessTask, checked: Boolean) =
    tasks.find { it.id == item.id }?.let { task ->
        task.checked = checked
    }
}