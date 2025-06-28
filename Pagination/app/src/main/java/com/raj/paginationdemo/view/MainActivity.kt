package com.raj.paginationdemo.view

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.raj.paginationdemo.model.QuotesResponse
import com.raj.paginationdemo.model.RickAndMortyResponse
import com.raj.paginationdemo.ui.theme.PaginationDemoTheme
import com.raj.paginationdemo.viewmodel.QuotesViewModel
import com.raj.paginationdemo.viewmodel.RickAndMortyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel by viewModels<RickAndMortyViewModel>()

            PaginationDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val pagingItems: LazyPagingItems<RickAndMortyResponse.Result> =
                        viewModel.rickAndMortyFlow.collectAsLazyPagingItems()
                    RickAndMortyMainScreen(pagingItems, Modifier.padding(innerPadding))
                    /*val pagingItems: LazyPagingItems<QuotesResponse.Result> =
                        viewModel.quotesFlow.collectAsLazyPagingItems()
                    Log.d("quotesFlow", pagingItems.itemSnapshotList.items.toString())
                    MainScreen(
                        quotesPagingItems = pagingItems,
                        modifier = Modifier.padding(innerPadding)
                    )*/
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PaginationDemoTheme {
    }
}