package com.example.navigationwithcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.navigationwithcompose.ui.theme.NavigationWithComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationWithComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App()
                }
            }
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()// 1. NavController

    //2. NavHost
    NavHost(navController = navController, startDestination = "registration") { //3. NavGraphBuilder
        //composable function is used to locate the node in NavGraph
        composable(route = "registration") {
            RegistrationScreen(navController) //Draw this screen when coming to registration node
        }

        composable(route = "login") {
            LoginScreen {
                val id = 1234
                navController.navigate(route = "main/${it}/1222AAA/${id}")
            } //Draw this screen when coming to login node
        }

        composable(route = "main/{email}/{password}/{id}", arguments = listOf(
            navArgument("email") {
                type = NavType.StringType
            },
            navArgument("password") {
                type = NavType.StringType
            },
            navArgument("id") {
                type = NavType.IntType
            }
        )) {
            val email = it.arguments?.getString("email").orEmpty()
            val password = it.arguments?.getString("password").orEmpty()
            val id = it.arguments?.getInt("id", 0) ?: 0

            MainScreen(email + password + id) //Draw this screen when coming to main node
        }
    }
}

@Composable
fun RegistrationScreen(navController: NavController) {
    Text(
        text = "Registration",
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier.clickable {
            navController.navigate(route = "login")
        }
    )
}

@Composable
fun LoginScreen(onClick: (email: String) -> Unit) {
    Text(
        text = "Login",
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier.clickable {
            onClick.invoke("raj@care.com")
        }
    )
}

@Composable
fun MainScreen(email: String) {
    Text(
        text = "Main Screen - $email",
        style = MaterialTheme.typography.headlineLarge
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NavigationWithComposeTheme {
        MainScreen("raj@care.com")
    }
}