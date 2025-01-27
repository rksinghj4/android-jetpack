package com.kotlinflow.view.retrofit.single

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kotlinflow.models.User

@Composable
fun SingleNetworkScreen(users: List<User>) {
    LazyColumn {
        items(users, key = { it.hashCode() }) {
            Row {
                Text(modifier = Modifier.weight(1f), text = it.name)
                Text(modifier = Modifier.weight(1f), text = it.email)
            }
        }
    }
}