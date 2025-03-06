package com.example.booksearch.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.booksearch.R
import com.example.booksearch.network.Book

@Composable
fun BookListItem(book: Book) {
    var isZoomed by remember { mutableStateOf(false) }
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val screenHeight = LocalConfiguration.current.screenHeightDp
    var imageX by remember { mutableFloatStateOf(0f) }
    var imageY by remember { mutableFloatStateOf(0f) }
    val scale by animateFloatAsState(
        targetValue = if (isZoomed) 2.5f else 1f,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
        label = "scaleAnim"
    )
    val offsetX by animateFloatAsState(
        targetValue = if (isZoomed) (screenWidth / 2 - imageX) else 0f, // X축 중앙으로 이동
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
        label = "offsetXAnim"
    )
    val offsetY by animateFloatAsState(
        targetValue = if (isZoomed) (screenHeight / 2 - imageY) else 0f, // Y축 중앙으로 이동
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
        label = "offsetYAnim"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { isZoomed = !isZoomed }
    ) {
        AsyncImage(
            model = book.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(100.dp, 150.dp)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    translationX = offsetX
                    translationY = offsetY
                }
                .onGloballyPositioned { coordinates ->
                    if (isZoomed) {
                        imageX = coordinates.positionInRoot().x
                        imageY = coordinates.positionInRoot().y
                    }
                },
            placeholder = painterResource(R.drawable.ic_launcher_background),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            Text(
                text = book.title,
                style = TextStyle(fontSize = 16.sp, color = Color.Black)
            )
            Text(
                text = "${book.author}, ${book.publisher}",
                style = TextStyle(fontSize = 12.sp, color = Color.Gray)
            )
        }
    }
}