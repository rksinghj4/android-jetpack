package com.example.tweetsy

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tweetsy.api.TweetsyAPI
import com.example.tweetsy.presentation.CategoryScreen
import com.example.tweetsy.presentation.DetailsScreen
import com.example.tweetsy.ui.theme.TweetsyTheme
import com.example.tweetsy.utils.CATEGORY
import com.example.tweetsy.utils.DETAILS
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var tweetsyAPI: TweetsyAPI

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO) {
            val response = tweetsyAPI.getCategories()
            Log.d(TAG, response.body()?.distinct().toString())
        }
        setContent {
            TweetsyTheme {
                Scaffold(topBar =
                {
                    TopAppBar(
                        title = {
                            Text(text = "Tweetsy")
                        }
                    )
                }) {
                    Box(modifier = Modifier.padding(it)) {
                        App()
                    }
                }
            }
        }
    }


    @Composable
    fun App() {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = CATEGORY) {
            composable(route = CATEGORY) {
                CategoryScreen { category ->
                    navController.navigate(route = "$DETAILS/${category}")
                }
            }
            composable(route = "$DETAILS/{$CATEGORY}", arguments = listOf(
                navArgument(CATEGORY) {
                    type = NavType.StringType
                }
            )) {
                //val category = it.arguments?.getString(CATEGORY, "Motivation") ?: "Motivation"
                //No need to pass category as parameter in DetailsScreen().
                //navigation arguments are saved in SavedStateHandle so
                // It can be get from SavedStateHandle in ViewModel
                DetailsScreen()
            }
        }

    }


    companion object {
        const val TAG = "TweetsyAPI"
    }
}
