package com.raj.paginationdemo.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.raj.paginationdemo.model.RickAndMortyResponse

@Composable
fun RickAndMortyMainScreen(
    pagingItems: LazyPagingItems<RickAndMortyResponse.Result>,
    modifier: Modifier
) {
    LazyColumn(modifier = modifier) {
        items(pagingItems.itemCount) { index ->
            val item = pagingItems[index]
            item?.let {
                RowItem(it)
            }
        }

        pagingItems.apply {
            when {
                loadState.prepend is LoadState.Loading -> {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier.background(color = Color.Green)
                                .padding(16.dp)
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                }

                loadState.prepend is LoadState.Error -> {
                    val error = loadState.prepend as LoadState.Error
                    item {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = "Error: ${error.error.localizedMessage}",
                            color = Color.Red
                        )
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier.background(color = Color.Green)
                                .padding(16.dp)
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                }

                loadState.append is LoadState.Error -> {
                    val error = loadState.append as LoadState.Error
                    item {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = "Error: ${error.error.localizedMessage}",
                            color = Color.Red
                        )
                    }
                }

                loadState.refresh is LoadState.Loading -> {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier.background(color = Color.Green)
                                .padding(16.dp)
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                }

                loadState.refresh is LoadState.Error -> {
                    val error = loadState.refresh as LoadState.Error
                    item {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = "Error: ${error.error.localizedMessage}",
                            color = Color.Red
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun RowItem(result: RickAndMortyResponse.Result) {
    Column {
        Text(text = result.name)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = result.gender)

    }
}
