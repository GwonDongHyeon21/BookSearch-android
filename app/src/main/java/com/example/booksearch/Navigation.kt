package com.example.booksearch

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.booksearch.ui.BookDetailScreen
import com.example.booksearch.ui.BookSearchScreen

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "BookSearchScreen"
    ) {
        composable("BookSearchScreen") {
            BookSearchScreen(navController)
        }
        composable(
            route = "BookDetailScreen/{imageUrl}/{title}",
            arguments = listOf(
                navArgument("imageUrl") { type = NavType.StringType },
                navArgument("title") { type = NavType.StringType },
            )
        ) {
            val imageUrl = it.arguments?.getString("imageUrl") ?: "알 수 없음"
            val title = it.arguments?.getString("title") ?: "제목 없음"

            BookDetailScreen(navController, imageUrl, title)
        }
    }
}