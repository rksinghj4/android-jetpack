package com.kotlinflow.view.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kotlinflow.database.UserDb
import com.kotlinflow.models.User

@Composable
fun CommonScreen(users: List<User>) {
    LazyColumn {
        itemsIndexed(users, key = { _, user -> user.hashCode() }) { index, user ->
            Row(modifier = Modifier.padding(8.dp)) {
                Text(modifier = Modifier.weight(1f), text = index.toString())

                Text(modifier = Modifier.weight(3f), text = user.name)
                //Text(modifier = Modifier.weight(1f), text = it.email)
            }
        }
    }
}

@Composable
fun CommonScreenForDatabaseUsers(users: List<UserDb>) {
    LazyColumn {
        itemsIndexed(users, key = { _, user -> user.hashCode() }) { index, user ->
            Row(modifier = Modifier.padding(8.dp)) {
                Text(modifier = Modifier.weight(1f), text = index.toString())

                user.name?.let { Text(modifier = Modifier.weight(3f), text = it) }
                //Text(modifier = Modifier.weight(1f), text = it.email)
            }
        }
    }
}