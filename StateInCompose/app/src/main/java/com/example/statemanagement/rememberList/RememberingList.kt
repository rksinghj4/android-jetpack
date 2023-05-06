package com.example.statemanagement.rememberList

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.Checkbox
import androidx.compose.material.IconButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * It is Stateful composable
 *
 * WellnessTaskItemWithCheckbox was used to demo the rememberSaveable
 * to persist the checked state of the task during configuration change.
 */
@Composable
fun WellnessTaskItemWithCheckbox(
    taskName: String,
    onClose: () -> Unit,
    modifier: Modifier = Modifier) {
    //Note1:As we know remember api stores the objects in composition,
    // While scrolling offscreen items leaves the Composition and the state that was remembered
    // using remember api is forgotten.

    // As we know rememberSaveable api stores the objects using saved instance state mechanism.
    //Note2 :Therefore rememberSaveable can store the state of the offscreen items of the list while scrolling.
    var checkedState by rememberSaveable{ mutableStateOf(false) } // try with remember

    WellnessTaskItem(
        taskName = taskName,
        checked = checkedState,
        onCheckedChange = { newValue -> checkedState = newValue },
        onClose = onClose,
        modifier = modifier,
    )
}

/**
* Stateless composable
*/
@Composable
fun WellnessTaskItem(
    taskName: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            text = taskName
        )
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        IconButton(onClick = onClose) {
            Icon(Icons.Filled.Close, contentDescription = "Close")
        }
    }
}
