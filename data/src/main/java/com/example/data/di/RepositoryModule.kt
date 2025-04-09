package com.example.data.di

import com.example.data.network.BookApi
import com.example.data.repository.BookRepositoryImpl
import com.example.domain.repository.BookRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideBookRepository(bookApi: BookApi): BookRepository {
        return BookRepositoryImpl(bookApi)
    }
}
