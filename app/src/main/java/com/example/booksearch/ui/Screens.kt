package com.example.booksearch.ui

sealed class Screen {
    data object List : Screen()
    data class Details(val imageUrl: String, val title: String) : Screen()
}