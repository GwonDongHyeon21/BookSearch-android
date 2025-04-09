package com.example.domain.repository

import com.example.domain.model.BookResponse

interface BookRepository {
    suspend fun searchBooks(query: String, display: Int, start: Int): BookResponse
}