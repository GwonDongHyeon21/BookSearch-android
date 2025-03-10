package com.example.booksearch.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.booksearch.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(navController: NavController, imageUrl: String, title: String) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "",
                        modifier = Modifier.clickable { navController.popBackStack() }
                    )
                }
            )
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "",
                modifier = Modifier.fillMaxWidth(),
                placeholder = painterResource(R.drawable.ic_launcher_background),
                error = painterResource(R.drawable.ic_launcher_foreground),
                contentScale = ContentScale.Fit
            )
            Text(text = title)
        }
    }
}

@Preview
@Composable
fun BookDetailPreview() {
    BookDetailScreen(rememberNavController(), "", "")
}