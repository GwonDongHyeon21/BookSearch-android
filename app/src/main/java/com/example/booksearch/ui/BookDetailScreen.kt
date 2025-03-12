package com.example.booksearch.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun BookDetailsScreen(
    imageUrl: String,
    title: String,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onBack: () -> Unit
) {
    BackHandler { onBack() }

    Column {
        with(sharedTransitionScope) {
            AsyncImage(
                model = imageUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .sharedElement(
                        state = rememberSharedContentState(
                            key = "item-image${imageUrl}"
                        ),
                        animatedVisibilityScope = animatedVisibilityScope
                    ),
                contentScale = ContentScale.Fit,
                contentDescription = null
            )
        }
        Text(
            text = title,
            fontSize = 24.sp
        )
    }
}