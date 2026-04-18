package com.example.finalprojectkotlin

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController) {
    var phoneNumber by remember { mutableStateOf("") }
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFCFD0C3))
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(340.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.login),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color(0x66CFD0C3),
                                    Color(0xFFCFD0C3)
                                ),
                                startY = 500f
                            )
                        )
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "Your Phone number",
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { input ->
                        if (input.length <= 9 && input.all { it.isDigit() }) {
                            phoneNumber = input
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("5X XXX XXXX", color = Color.Gray) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    leadingIcon = {
                        Text(
                            "+972 ",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = Color(0xFFAD2629),
                        unfocusedBorderColor = Color.Transparent,
                        cursorColor = Color(0xFFAD2629)
                    )
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = "Sign in with Email",
                    color = Color(0xFFAD2629),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { },
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        when {
                            phoneNumber.isEmpty() -> {
                                scope.launch { snackBarHostState.showSnackbar("please enter phone number") }
                            }

                            phoneNumber.length != 9 -> {
                                scope.launch {
                                    snackBarHostState.showSnackbar("The number must consist of 9 digits")
                                }
                            }

                            else -> {
                                navController.navigate("home") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAD2629))
                ) {
                    Text(
                        text = "Login",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                TermsAndConditionsText()
            }

            Spacer(modifier = Modifier.height(30.dp))

            LoginDivider()

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SocialIcon(iconRes = R.drawable.google)
                Spacer(modifier = Modifier.width(25.dp))
                SocialIcon(iconRes = R.drawable.facebook)
                Spacer(modifier = Modifier.width(25.dp))
                SocialIcon(iconRes = R.drawable.twiter)
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun TermsAndConditionsText() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("By clicking Login you agree to our ", fontSize = 15.sp, color = Color(0xFF5B5B5B))
            Text("terms & conditions", fontSize = 15.sp, color = Color(0xFF1877F2))
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("and ", fontSize = 15.sp, color = Color(0xFF5B5B5B))
            Text(" privacy policy", fontSize = 15.sp, color = Color(0xFF1877F2))
        }
    }
}

@Composable
fun LoginDivider() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(modifier = Modifier.weight(1f), color = Color.White)
        Text(
            "OR",
            modifier = Modifier.padding(horizontal = 16.dp),
            color = Color.Black,
            fontSize = 13.sp
        )
        HorizontalDivider(modifier = Modifier.weight(1f), color = Color.White)
    }
}

@Composable
fun SocialIcon(iconRes: Int) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .background(Color.White, CircleShape)
            .border(1.dp, Color(0xFFEEEEEE), CircleShape)
            .clickable { },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}