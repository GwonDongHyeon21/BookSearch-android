package com.example.booksearch.ui

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.booksearch.network.Book

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun BookListItem(
    book: Book,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
    ) {
        with(sharedTransitionScope) {
            AsyncImage(
                model = book.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp, 150.dp)
                    .sharedElement(
                        state = rememberSharedContentState(
                            key = "item-image${book.imageUrl}"
                        ),
                        animatedVisibilityScope = animatedVisibilityScope
                    ),
                contentScale = ContentScale.Crop
            )
        }
        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            Text(
                text = book.title,
                fontSize = 16.sp,
                color = Color.Black
            )
            Text(
                text = "${book.author}, ${book.publisher}",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}
