package com.example.finalprojectkotlin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.finalprojectkotlin.viewModel.FavoritesViewModel

@Composable
fun FavoritesScreen(viewModel: FavoritesViewModel, navController: NavController) {
    val favoriteList by viewModel.favoriteItems.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Favorite",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )

        HorizontalDivider(color = Color.LightGray, thickness = 0.5.dp)

        if (favoriteList.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "No favorites yet", color = Color.Gray)
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
            ) {
                items(favoriteList) { product ->
                    ProductCard(
                        product = product,
                        viewModel = viewModel,
                        navController = navController
                    )
                }
            }
        }
    }
}