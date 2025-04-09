package com.example.domain.usecase

import android.util.Log
import com.example.domain.repository.BookRepository
import com.example.domain.model.BookResponse
import javax.inject.Inject

class SearchBooksUseCase @Inject constructor(
    private val bookRepository: BookRepository
) {

    suspend fun searchBooks(query: String, display: Int, start: Int): BookResponse {
        return try {
            bookRepository.searchBooks(query, display, start)
        } catch (e: Exception) {
            Log.e("SearchBooksUseCase", e.toString())
            throw e
        }
    }
}
