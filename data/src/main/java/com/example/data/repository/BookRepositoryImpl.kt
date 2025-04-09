package com.example.data.repository

import android.util.Log
import com.example.data.network.BookApi
import com.example.domain.model.BookResponse
import com.example.domain.repository.BookRepository
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val bookApi: BookApi
) : BookRepository {

    override suspend fun searchBooks(query: String, display: Int, start: Int): BookResponse {
        return try {
            bookApi.searchBooks(query, display, start)
        } catch (e: Exception) {
            Log.d("BookRepository", e.toString())
            throw e
        }
    }
}