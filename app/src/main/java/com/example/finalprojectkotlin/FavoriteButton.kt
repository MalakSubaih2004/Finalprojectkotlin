package com.example.finalprojectkotlin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.finalprojectkotlin.dataClass.Product
import com.example.finalprojectkotlin.viewModel.FavoritesViewModel

@Composable
fun FavoriteButton(
    product: Product,
    viewModel: FavoritesViewModel,
    modifier: Modifier = Modifier
) {
    val favoriteItems by viewModel.favoriteItems.collectAsState()

    val isFavorite = favoriteItems.any { it.id == product.id }

    IconButton(
        onClick = { viewModel.toggleFavorite(product) },
        modifier = modifier
            .background(Color.White.copy(0.7f), CircleShape)
    ) {
        Icon(
            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = null,
            tint = if (isFavorite) Color.Red else Color.Black,
            modifier = Modifier.size(24.dp)
        )
    }
}