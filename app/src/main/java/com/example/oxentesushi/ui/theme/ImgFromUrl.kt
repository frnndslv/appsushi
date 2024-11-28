package com.example.oxentesushi.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun ImageFromUrl(imageUrl: String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = imageUrl,
        contentDescription = "Image from URL",
        modifier = modifier,
        contentScale = ContentScale.Crop // Ajuste conforme necessário (Crop, Fit, etc.)
    )
}