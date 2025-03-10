package com.example.booksearch.ui

import android.net.Uri
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.booksearch.R
import com.example.booksearch.network.Book
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BookListItemCustom(navController: NavController, book: Book) {
    var isZoomed by rememberSaveable { mutableStateOf(false) }
    var isPositioned by remember { mutableStateOf(false) }
    val innerPadding = 16.dp

    LaunchedEffect(navController.currentBackStackEntry) {
        delay(300)
        isZoomed = false
    }

    BoxWithConstraints {
        val screenWidth = constraints.maxWidth.toFloat()
        val imagePositionX = screenWidth / 2 - dpToFloat(innerPadding + 50.dp)
        var imageY by remember { mutableFloatStateOf(0f) }

        val scale by animateFloatAsState(
            targetValue = if (isZoomed) screenWidth / dpToFloat(100.dp) else 1f,
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
            label = "scaleAnim"
        )
        val offsetX by animateFloatAsState(
            targetValue = if (isZoomed) imagePositionX else 0f,
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
            label = "offsetXAnim"
        )
        val offsetY by animateFloatAsState(
            targetValue = if (isZoomed) -500f else 0f,
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
            label = "offsetYAnim"
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .clickable {
                    CoroutineScope(Dispatchers.Main).launch {
                        isPositioned = true
                        isZoomed = true
                        delay(300)
                        val encodedImageUri = Uri.encode(book.imageUrl)
                        navController.navigate("BookDetailScreen/$encodedImageUri/${book.title}")
                    }
                }
                .onGloballyPositioned {
                    if (isPositioned) {
                        imageY = it.positionInRoot().y
                        isPositioned = false
                    }
                }
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
                    .zIndex(1f),
                placeholder = painterResource(R.drawable.ic_launcher_background),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(horizontal = innerPadding / 2)) {
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
}

@Composable
fun dpToFloat(dp: Dp): Float {
    return with(LocalDensity.current) { dp.toPx() }
}