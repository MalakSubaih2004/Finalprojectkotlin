package com.example.finalprojectkotlin

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.finalprojectkotlin.dataClass.BottomNavItem
import com.example.finalprojectkotlin.dataClass.Product
import com.example.finalprojectkotlin.dataClass.Screen
import com.example.finalprojectkotlin.dataClass.globalProductList
import com.example.finalprojectkotlin.viewModel.CartViewModel
import com.example.finalprojectkotlin.viewModel.FavoritesViewModel


@Composable
fun HomeScreen() {
    val navController = rememberNavController()

    val favoritesViewModel: FavoritesViewModel = viewModel()
    val cartViewModel: CartViewModel = viewModel()
    val items = listOf(
        BottomNavItem(Screen.Home.route, R.drawable.home_icon),
        BottomNavItem(Screen.CategoriesScreen.route, R.drawable.category_icon),
        BottomNavItem(Screen.Cart.route, R.drawable.cart_icon),
        BottomNavItem(Screen.FavoritesScreen.route, R.drawable.fav),
        BottomNavItem(Screen.ProfileScreen.route, R.drawable.profile_icon)
    )

    Scaffold(
        bottomBar = {
            CustomBottomNavigation(items = items, navController = navController)
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Home.route) {
                MyHomeScreen(favoritesViewModel, navController)
            }

            composable(Screen.CategoriesScreen.route) {
                CategoriesScreen(navController)
            }

            composable(Screen.CategoryProducts.route + "/{catName}") { backStackEntry ->
                val categoryName = backStackEntry.arguments?.getString("catName") ?: ""
                CategoryProductsScreen(categoryName, favoritesViewModel, navController)
            }

            composable(Screen.ProductDetails.route + "/{productId}") { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull()
                val product = globalProductList.find { it.id == productId }
                if (product != null) {
                    ProductDetailsScreen(product, cartViewModel, favoritesViewModel, navController)
                }
            }
            composable(Screen.Cart.route) { CartScreen(cartViewModel, navController) }
            composable(Screen.FavoritesScreen.route) {
                FavoritesScreen(
                    favoritesViewModel,
                    navController
                )
            }
            composable(Screen.ProfileScreen.route) { ProfileScreen(cartViewModel) }
        }
    }
}

@Composable
fun MyHomeScreen(viewModel: FavoritesViewModel, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
    ) {

        Box(modifier = Modifier.padding(horizontal = 16.dp)) { TopHeader() }

        MainBanner()

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
        ) {
            items(globalProductList) { product ->
                ProductCard(product = product, viewModel = viewModel, navController = navController)
            }
        }
    }
}

@Composable
fun ProductCard(product: Product, viewModel: FavoritesViewModel, navController: NavController) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .clickable {
                navController.navigate(Screen.ProductDetails.passId(product.id))
            }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = product.imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                Surface(
                    color = Color.White.copy(alpha = 0.8f),
                    shape = RoundedCornerShape(bottomEnd = 12.dp),
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Text(
                        text = product.discountPercentage,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 11.sp, color = Color.Red, fontWeight = FontWeight.Bold
                    )
                }
                FavoriteButton(
                    product = product,
                    viewModel = viewModel,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(text = product.name, fontSize = 16.sp, color = Color.Black, maxLines = 1)

                Text(
                    text = product.brand,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = product.originalPrice,
                            fontSize = 12.sp,
                            color = Color.Gray,
                            textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = product.discountPrice,
                            fontSize = 14.sp,
                            color = Color(0xFFAD2629),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = "${product.soldCount} sold",
                        fontSize = 14.sp,
                        color = Color.LightGray
                    )
                }
            }
        }
    }
}

@Composable
fun TopHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Good morning", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Row {
            Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Icon(
                Icons.Default.Notifications,
                contentDescription = null,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
fun MainBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.home),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }
}

@Composable
fun CustomBottomNavigation(items: List<BottomNavItem>, navController: NavController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    NavigationBar(containerColor = Color.White, tonalElevation = 8.dp) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconRes),
                        modifier = Modifier.size(26.dp),
                        contentDescription = null,
                        tint = if (isSelected) Color(0xFFAD2629) else Color.LightGray
                    )
                },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
            )
        }
    }
}