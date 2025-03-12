package com.example.booksearch.repository

import com.example.booksearch.network.Book
import com.example.booksearch.network.RetrofitClient

suspend fun searchBooks(query: String, start: Int): List<Book> {
    return try {
        val response = RetrofitClient.instance.searchBooks(query = query, start = start)
        response.books
    } catch (e: Exception) {
        emptyList()
    }
}