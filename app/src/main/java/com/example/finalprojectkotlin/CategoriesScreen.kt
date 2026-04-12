package com.example.finalprojectkotlin

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.finalprojectkotlin.dataClass.Screen

@Composable
fun CategoriesScreen(navController: NavController) {
    val categories = listOf(
        Pair("Skin creams", R.drawable.skin_creams),
        Pair("Nail products", R.drawable.nail_products),
        Pair("Perfume", R.drawable.perfume),
        Pair("Skin care Tools", R.drawable.skin_care),
        Pair("Makeup", R.drawable.skin_creams),
        Pair("Hair care tools", R.drawable.nail_products),
        Pair("Nail products", R.drawable.nail_products),
        Pair("Perfume", R.drawable.perfume),
        Pair("Skin care Tools", R.drawable.skin_care),
        Pair("Makeup", R.drawable.skin_creams)

    )

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text(
            text = "Categories",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(categories) { category ->
                CategoryItem(
                    name = category.first,
                    imageRes = category.second,
                    onClick = { navController.navigate(Screen.CategoryProducts.route + "/${category.first}") }
                )
            }
        }
    }
}

@Composable
fun CategoryItem(name: String, imageRes: Int, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable { onClick() }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(0.15f),
                                Color.Transparent,
                                Color.Black.copy(0.5f)
                            )
                        )
                    )
            )

            Text(
                text = name,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp)
            )
        }
    }
}