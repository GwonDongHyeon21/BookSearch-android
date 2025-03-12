package com.example.booksearch.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.booksearch.network.Book

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun BookListScreen(books: List<Book>, listState: LazyListState, isAddBooks: Boolean) {
    var state by remember { mutableStateOf<Screen>(Screen.List) }

    SharedTransitionLayout {
        AnimatedContent(
            targetState = state,
            label = "",
            transitionSpec = { fadeIn() togetherWith fadeOut() }
        ) {
            when (it) {
                is Screen.List -> {
                    LazyColumn(state = listState) {
                        items(books) { book ->
                            BookListItem(
                                book,
                                this@SharedTransitionLayout,
                                this@AnimatedContent
                            ) {
                                state = Screen.Details(book.imageUrl, book.title)
                            }
                        }
                        if (isAddBooks)
                            item {
                                Box(Modifier.fillMaxWidth()) {
                                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                                }
                            }
                    }
                }

                is Screen.Details -> {
                    BookDetailsScreen(
                        it.imageUrl,
                        it.title,
                        this@SharedTransitionLayout,
                        this@AnimatedContent
                    ) {
                        state = Screen.List
                    }
                }
            }
        }
    }
}