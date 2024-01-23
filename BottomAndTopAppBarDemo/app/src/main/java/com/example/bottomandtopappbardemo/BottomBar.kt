package com.example.bottomandtopappbardemo

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp


data class BottomNavigationItem(
    val title: String,
    val seclectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)

val navigationItemList = listOf(
    BottomNavigationItem(
        title = "Home",
        seclectedIcon = Icons.Filled.Home,
        unSelectedIcon = Icons.Outlined.Home,
        hasNews = false,
    ),

    BottomNavigationItem(
        title = "Notifications",
        seclectedIcon = Icons.Filled.Notifications,
        unSelectedIcon = Icons.Outlined.Notifications,
        hasNews = false,
        badgeCount = 31
    ),

    BottomNavigationItem(
        title = "Search",
        seclectedIcon = Icons.Filled.Search,
        unSelectedIcon = Icons.Outlined.Search,
        hasNews = false,
    ),

    BottomNavigationItem(
        title = "Messages",
        seclectedIcon = Icons.Filled.Email,
        unSelectedIcon = Icons.Outlined.Email,
        hasNews = true
    ),

    BottomNavigationItem(
        title = "Phone",
        seclectedIcon = Icons.Filled.Phone,
        unSelectedIcon = Icons.Outlined.Phone,
        hasNews = true
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomAppBarDemo() {
    var selectedItemIndexed by rememberSaveable {
        mutableStateOf(0)
    }

    Scaffold(
        bottomBar = {
            NavigationBar() {
                navigationItemList.forEachIndexed { index, bottomNavigationItem ->
                    NavigationBarItem(
                        selected = selectedItemIndexed == index,
                        onClick = {
                            selectedItemIndexed = index
                            //navController.navigate(item.title)
                        },
                        label = {
                            Text(
                                text = bottomNavigationItem.title,
                                fontSize = 8.sp
                            )
                        },
                        icon = {
                            BadgedBox(badge = {
                                if (bottomNavigationItem.badgeCount != null) {
                                    Badge {
                                        Text(text = bottomNavigationItem.badgeCount.toString())
                                    }
                                } else if (bottomNavigationItem.hasNews) {
                                    Badge()
                                }
                            }) {
                                Icon(
                                    imageVector = if (selectedItemIndexed == index) {
                                        bottomNavigationItem.seclectedIcon
                                    } else {
                                        bottomNavigationItem.unSelectedIcon
                                    },
                                    contentDescription = bottomNavigationItem.title
                                )
                            }
                        }
                    )
                }
            }
        }) { innerPadding ->
        Text(
            modifier = Modifier.padding(innerPadding),
            text = "Example of a scaffold with a bottom app bar."
        )
    }
}

