package com.example.booksearch.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.booksearch.network.Book

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun BookListItem(navController: NavController, books: List<Book>, listState: LazyListState) {
    var state by remember { mutableStateOf<Screen>(Screen.List) }

    SharedTransitionLayout(modifier = Modifier.fillMaxWidth()) {
        AnimatedContent(
            targetState = state,
            label = "",
            transitionSpec = { fadeIn() togetherWith fadeOut() }
        ) {
            when (it) {
                Screen.List -> {
                    LazyColumn(state = listState) {
                        items(books) { book ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ) { state = Screen.Details(book.imageUrl, book.title) }
                            ) {
                                AsyncImage(
                                    model = book.imageUrl,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(100.dp, 150.dp)
                                        .sharedElement(
                                            state = rememberSharedContentState(
                                                key = "item-image${book.imageUrl}"
                                            ),
                                            animatedVisibilityScope = this@AnimatedContent,
                                        ),
                                    contentScale = ContentScale.Crop
                                )

                                Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                                    Text(
                                        text = book.title,
                                        fontSize = 16.sp,
                                        color = Color.Black
                                    )
                                    Text(
                                        text = "${book.author}, ${book.publisher}",
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }

                is Screen.Details -> {
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { state = Screen.List }
                    ) {
                        AsyncImage(
                            model = it.imageUrl,
                            modifier = Modifier
                                .sharedElement(
                                    state = rememberSharedContentState(key = "item-image${it.imageUrl}"),
                                    animatedVisibilityScope = this@AnimatedContent,
                                )
                                .fillMaxWidth(),
                            contentScale = ContentScale.Fit,
                            contentDescription = null
                        )
                        Text(
                            text = "Item ${it.title}",
                            fontSize = 24.sp
                        )
                    }
                }
            }
        }
    }
}