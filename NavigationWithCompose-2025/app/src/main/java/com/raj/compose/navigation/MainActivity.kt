package com.raj.compose.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.raj.compose.navigation.ui.theme.NavigationWithCompose2025Theme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationWithCompose2025Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    NewApp()
                }
            }
        }
    }
}

/**
 * Compose frame work still use strings after applying serialization on routes
 * For that we added followings
 * kotlinx-serialization-json = { module = "org.jetbrains.kotlinx:kotlinx-serialization-json", version.ref = "kotlinSerialization" }
 * kotlin-serialization = { id = "org.jetbrains.kotlin.plugin.serialization", version.ref = "kotlin" }
 *  alias(libs.plugins.kotlin.serialization)
 *
 */
@Serializable
object Registration

@Serializable
object Login

@Serializable
data class Main(val id: Int) {
    lateinit var email: String
    lateinit var password: String
}


@Composable
fun NewApp() {
    val navController = rememberNavController()// 1. NavController
    NavHost(navController = navController, startDestination = Registration) {
        composable<Registration> {
            //Draw this screen when coming to registration node
            RegistrationScreen {
                navController.navigate(Login)//Registration -> Login
            }
        }

        composable<Login> { navBackStackEntry ->
            LoginScreen { email, pass ->
                val main = Main(
                    id = 1234
                )
                main.email = email
                main.password = pass
                navController.navigate(main) //navigate Login to Main
            }
        }

        composable<Main> { navBackStackEntry ->
            val mainObj: Main = navBackStackEntry.toRoute<Main>()//Deserialize the passed data
            MainScreen(mainObj.id.toString() + mainObj.email)
        }
    }
}

//Old way of navigation compose, we had to pass serialized routes
@Composable
fun OldApp() {
    val navController = rememberNavController()// 1. NavController

    //2. NavHost
    NavHost(navController = navController, startDestination = "registration") { //3. NavGraphBuilder
        //composable function is used to locate the node in NavGraph
        composable(route = "registration") {
            RegistrationScreen {
                navController.navigate("login")//registration -> login
            } //Draw this screen when coming to registration node
        }

        composable(route = "login") {
            //Draw this screen when coming to login node
            LoginScreen { email, pass ->
                val id = 1234
                navController.navigate(route = "main/${email}/${pass}/${id}")
            }
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
fun RegistrationScreen(navigationBlock: () -> Unit) {
    Text(
        text = "Registration",
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier.clickable {
            navigationBlock.invoke()
        }
    )
}

@Composable
fun LoginScreen(onClick: (email: String, password: String) -> Unit) {
    Text(
        text = "Login",
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier.clickable {
            onClick.invoke("raj@care.com", "password123")
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
    NavigationWithCompose2025Theme {
    }
}