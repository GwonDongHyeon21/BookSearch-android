package com.example.booksearch.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.example.booksearch.network.Book
import com.example.booksearch.repository.searchBooks
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookSearchScreen() {
    var query by rememberSaveable { mutableStateOf("") }
    var isLoading by rememberSaveable { mutableStateOf(false) }
    var books by rememberSaveable { mutableStateOf<List<Book>>(emptyList()) }
    var bookSearchIndex by rememberSaveable { mutableIntStateOf(1) }
    val listState = rememberLazyListState()
    val keyboardController = LocalSoftwareKeyboardController.current
    var isAddBooks by remember { mutableStateOf(true) }

    LaunchedEffect(listState) {
        while (isAddBooks) {
            val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            if (lastVisibleItemIndex != null && lastVisibleItemIndex >= books.size - 1) {
                bookSearchIndex += 10
                val addBooks = searchBooks(query, bookSearchIndex)
                if (addBooks.isEmpty()) isAddBooks = false
                else books += addBooks
            }
            delay(100)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row {
                        TextField(
                            value = query,
                            onValueChange = { if (it.length <= 20) query = it },
                            modifier = Modifier
                                .weight(1f)
                                .background(Color.Transparent),
                            placeholder = {
                                Text(
                                    text = "Search Book",
                                    style = TextStyle(color = Color.Gray)
                                )
                            },
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Search
                            ),
                            keyboardActions = KeyboardActions(
                                onSearch = {
                                    keyboardController?.hide()
                                    bookSearchIndex = 1
                                    books = emptyList()
                                    CoroutineScope(Dispatchers.IO).launch {
                                        isLoading = true
                                        books = searchBooks(query, bookSearchIndex)
                                        isLoading = false
                                    }
                                    CoroutineScope(Dispatchers.Main).launch {
                                        listState.scrollToItem(0)
                                    }
                                }
                            )
                        )
                        IconButton(
                            onClick = {
                                keyboardController?.hide()
                                bookSearchIndex = 1
                                books = emptyList()
                                CoroutineScope(Dispatchers.IO).launch {
                                    isLoading = true
                                    books = searchBooks(query, bookSearchIndex)
                                    isLoading = false
                                }
                                CoroutineScope(Dispatchers.Main).launch {
                                    listState.scrollToItem(0)
                                }
                            },
                            modifier = Modifier.wrapContentWidth(),
                            enabled = query.isNotEmpty()
                        ) {
                            Icon(Icons.Default.Search, contentDescription = "")
                        }
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            if (isLoading)
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            else {
                if (books.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = "책을 검색해 주세요.",
                            modifier = Modifier.align(Alignment.Center),
                            style = TextStyle(color = Color.Gray)
                        )
                    }
                } else {
                    BookListScreen(books, listState, isAddBooks)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookSearchPreview() {
    BookSearchScreen()
}