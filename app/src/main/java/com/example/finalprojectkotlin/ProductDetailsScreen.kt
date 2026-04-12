package com.example.finalprojectkotlin

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.finalprojectkotlin.dataClass.Product
import com.example.finalprojectkotlin.dataClass.Screen
import com.example.finalprojectkotlin.viewModel.CartViewModel
import com.example.finalprojectkotlin.viewModel.FavoritesViewModel

@Composable
fun ProductDetailsScreen(
    product: Product,
    cartViewModel: CartViewModel,
    favoritesViewModel: FavoritesViewModel,
    navController: NavController
) {
    var quantity by remember { mutableStateOf(1) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 100.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp)
            ) {
                Image(
                    painter = painterResource(product.imageRes),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopStart)
                        .background(Color.White.copy(0.7f), CircleShape)
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.Black)
                }

                FavoriteButton(
                    product = product,
                    viewModel = favoritesViewModel,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                )
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = product.discountPrice,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                    ) {
                        IconButton(onClick = { if (quantity > 1) quantity-- }) {
                            Icon(
                                painter = painterResource(id = R.drawable.min),
                                contentDescription = "Decrease",
                                modifier = Modifier.size(30.dp),
                                tint = Color.Unspecified
                            )
                        }
                        Text(
                            text = quantity.toString(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(onClick = { quantity++ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.add),
                                contentDescription = "Increase",
                                modifier = Modifier.size(30.dp),
                                tint = Color.Unspecified
                            )
                        }
                    }
                }

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
                    color = Color.White
                ) {
                    Row(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = null,
                                tint = Color(0xFFFFB400),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "4.8 | 50 Orders",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = null,
                            tint = Color.Gray
                        )
                    }
                }
                Spacer(
                    modifier = Modifier
                        .width(6.dp)
                        .height(10.dp)
                )

                HorizontalDivider(color = Color(0xFFEEEEEE), thickness = 1.dp)

                Text(
                    text = product.description,
                    fontSize = 15.sp,
                    color = Color.DarkGray,
                    lineHeight = 24.sp
                )
            }
        }

        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            color = Color.White
        ) {
            Column {
                HorizontalDivider(color = Color(0xFFEEEEEE), thickness = 1.dp)

                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(54.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.cart),
                        contentDescription = null,
                        tint = Color(0xFFAD2629),
                        modifier = Modifier.size(28.dp)
                    )


                    Spacer(modifier = Modifier.width(12.dp))

                    Button(
                        onClick = {
                            cartViewModel.addToCart(product, quantity)
                            navController.navigate(Screen.Cart.route)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAD2629)),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            "Buy now",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}