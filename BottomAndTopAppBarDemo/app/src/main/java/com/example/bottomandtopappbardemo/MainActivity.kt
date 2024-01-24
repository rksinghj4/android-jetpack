package com.example.bottomandtopappbardemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.bottomandtopappbardemo.ui.theme.BottomAndTopAppBarDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BottomAndTopAppBarDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationWithNavGraph()
                    //AppBarExamples()
                }
            }
        }
    }
}

@Composable
fun NavigationWithNavGraph() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            Box {
                Column(modifier = Modifier.align(Alignment.Center)) {
                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(10.dp),
                        text = "Hey you are on Login page"
                    )
                    Button(onClick = {
                        navController.navigate(HOME_TAG)
                    }) {
                        Text(text = "Login")
                    }
                }
            }
        }

        navigation(
            startDestination = "Dashboard",
            route = HOME_TAG
        ) {
            composable("Dashboard") {
                BottomAppBarDemo()
               // val viewModel = it.sharedViewModel<SampleViewModel>(navController = navController)
            }

            composable("Empty_Home") {
               // val viewModel = it.sharedViewModel<SampleViewModel>(navController = navController)
            }
        }
    }
}

/*@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}*/
