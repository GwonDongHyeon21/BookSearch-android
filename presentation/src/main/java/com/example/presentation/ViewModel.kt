package com.example.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Book
import com.example.domain.usecase.SearchBooksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val searchBooksUseCase: SearchBooksUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _searchResultText = MutableStateFlow("책을 검색해주세요.")
    val searchResultText: StateFlow<String> = _searchResultText

    private val _isTopBar = MutableStateFlow(true)
    val isTopBar: StateFlow<Boolean> = _isTopBar

    private val _query = MutableStateFlow("")

    private val _displayNumber = MutableStateFlow(10)

    private val _startNumber = MutableStateFlow(1)

    private val _isAddBooks = MutableStateFlow(true)
    val isAddBooks: StateFlow<Boolean> = _isAddBooks

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: MutableStateFlow<List<Book>> = _books

    fun searchBooks(query: String) {
        viewModelScope.launch {
            if (_query.value == query) {
                _startNumber.value += _displayNumber.value
            } else {
                _query.value = query
                _books.value = emptyList()
                _startNumber.value = 1
                _isLoading.value = true
            }

            val addBooks =
                searchBooksUseCase.searchBooks(
                    query,
                    _displayNumber.value,
                    _startNumber.value
                ).books
            if (addBooks.isEmpty()) {
                _isAddBooks.value = false
                if (_books.value.isEmpty())
                    _searchResultText.value = "검색 결과가 없습니다."
            } else {
                _books.value += addBooks
            }
            _isLoading.value = false
        }
    }

    fun isTopBarFalse() {
        _isTopBar.value = false
    }
}