package com.example.bottomandtopappbardemo

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController

/**
 * Resource: Bottom Navigation Bar With Badges - UX With Material3
 *
 * https://www.youtube.com/watch?v=c8XP_Ee7iqY&t=702s
 * Full Guide to Nested Navigation Graphs in Jetpack Compose
 * https://www.youtube.com/watch?v=FIEnIBq7Ups
 */
data class BottomNavigationItem(
    val title: String,
    val seclectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null,
    val alwaysShowLabel: Boolean = true
)

val HOME_TAG = "Home"
val NOTIFICATION_TAG = "Notifications"
val SEARCH_TAG = "Search"
val MESSAGES_TAG = "Messages"
val PHONE_TAG = "Phone"

val navigationItemList = listOf(
    BottomNavigationItem(
        title = HOME_TAG,
        seclectedIcon = Icons.Filled.Home,
        unSelectedIcon = Icons.Outlined.Home,
        hasNews = false,
    ),

    BottomNavigationItem(
        title = NOTIFICATION_TAG,
        seclectedIcon = Icons.Filled.Notifications,
        unSelectedIcon = Icons.Outlined.Notifications,
        hasNews = false,
        badgeCount = 31
    ),

    BottomNavigationItem(
        title = SEARCH_TAG,
        seclectedIcon = Icons.Filled.Search,
        unSelectedIcon = Icons.Outlined.Search,
        hasNews = false,
    ),

    BottomNavigationItem(
        title = MESSAGES_TAG,
        seclectedIcon = Icons.Filled.Email,
        unSelectedIcon = Icons.Outlined.Email,
        hasNews = true
    ),

    BottomNavigationItem(
        title = PHONE_TAG,
        seclectedIcon = Icons.Filled.Phone,
        unSelectedIcon = Icons.Outlined.Phone,
        hasNews = true,
        alwaysShowLabel = false
    )
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomAppBarDemo() {
    var selectedItemIndexed by rememberSaveable {
        mutableStateOf(0)
    }
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                navigationItemList.forEachIndexed { index, bottomNavigationItem ->
                    NavigationBarItem(
                        selected = selectedItemIndexed == index,
                        onClick = {
                            selectedItemIndexed = index
                            navController.navigate(bottomNavigationItem.title)
                            //navController.navigate(item.title)
                        },
                        label = {
                            Text(
                                text = bottomNavigationItem.title,
                                fontSize = 8.sp
                            )
                        },
                        alwaysShowLabel = bottomNavigationItem.alwaysShowLabel, //Default is true
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
        }) {

        NavHost(navController = navController, startDestination = HOME_TAG) {

            composable(route = HOME_TAG) {
                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(10.dp),
                    text = "You are on Dashboard"
                )
            }

            navigation(
                startDestination = "notification_empty",
                route = NOTIFICATION_TAG
            ) {

                composable("notification_empty") {
                    Box {
                        Text(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(10.dp),
                            text = "No notification"
                        )
                    }
                }

                composable("notification_non_empty") {
                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(10.dp),
                        text = "Hey please red more notification"
                    )
                }
            }

            navigation(
                startDestination = "empty_search",
                route = SEARCH_TAG
            ) {
                composable("empty_search") {
                    Box {
                        Text(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(10.dp),
                            text = "No Search Result"
                        )
                    }
                }

                composable("non_empty_serach") {
                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(10.dp),
                        text = "Here is search result"
                    )
                }
            }

            navigation(
                startDestination = "empty_messages",
                route = MESSAGES_TAG
            ) {

                composable("empty_messages") {
                    Box {
                        Text(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(10.dp),
                            text = "No Message Result"
                        )
                    }
                }

                composable("non_empty_serach") {
                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(10.dp),
                        text = "Here is Message result"
                    )
                }
            }


            navigation(
                startDestination = "empty_phone",
                route = PHONE_TAG
            ) {

                composable("empty_phone") {
                    Box {
                        Text(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(10.dp),
                            text = "No Phone Calls"
                        )
                    }
                }

                composable("non_empty_serach") {
                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(10.dp),
                        text = "Here is list of phone calls"
                    )
                }
            }
        }


        /*Text(
               modifier = Modifier.padding(innerPadding),
               text = "Example of a scaffold with a bottom app bar."
           )*/
    }
}

