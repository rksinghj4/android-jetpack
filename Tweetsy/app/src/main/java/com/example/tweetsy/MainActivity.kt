package com.example.tweetsy

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.tweetsy.api.TweetsyAPI
import com.example.tweetsy.presentation.CategoryScreen
import com.example.tweetsy.presentation.DetailsScreen
import com.example.tweetsy.ui.theme.TweetsyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var tweetsyAPI: TweetsyAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO) {
            val response = tweetsyAPI.getCategories()
            Log.d(TAG, response.body()?.distinct().toString())
        }
        setContent {
            TweetsyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //CategoryScreen()
                    DetailsScreen()
                }
            }
        }
    }

    companion object {
        const val TAG = "TweetsyAPI"
    }
}
