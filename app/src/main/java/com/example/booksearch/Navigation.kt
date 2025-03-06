package com.example.booksearch

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.booksearch.ui.BookSearchScreen

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "BookSearchScreen") {
        composable("BookSearchScreen") {
            BookSearchScreen(navController)
        }
//        composable(
//            route = "BookDetailScreen/{imageUrl}/{title}",
//            arguments = listOf(
//                navArgument("imageUrl") { type = NavType.StringType },
//                navArgument("title") { type = NavType.StringType },
//            )
//        ) {
//            val imageUrl = it.arguments?.getString("imageUrl")
//            val title = it.arguments?.getString("title")
//
//            BookDetailScreen(navController, imageUrl, title)
//        }
    }
}