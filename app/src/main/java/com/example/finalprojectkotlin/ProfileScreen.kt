package com.example.finalprojectkotlin

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import com.example.finalprojectkotlin.viewModel.CartViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(cartViewModel: CartViewModel) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    val myOrders by cartViewModel.myOrders.collectAsState()
    val totalOrdersCount = myOrders.sumOf { it.quantity }.toString()

    var userProfile by remember {
        mutableStateOf(
            UserProfile(
                "Mona Fadl Al-Harthy",
                "009665211043",
                "Mona.Fadl@gmail.com",
                R.drawable.profile_img
            )
        )
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContainerColor = Color.White,
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        sheetContent = {
            EditProfileBottomSheetContent(
                currentProfile = userProfile,
                onSave = { updated ->
                    userProfile = updated
                    scope.launch { scaffoldState.bottomSheetState.partialExpand() }
                },
                onClose = {
                    scope.launch { scaffoldState.bottomSheetState.partialExpand() }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Account",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = userProfile.imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(65.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = userProfile.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(text = userProfile.phone, color = Color.Gray, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = userProfile.email, color = Color.Gray, fontSize = 13.sp)
                        IconButton(
                            onClick = { scope.launch { scaffoldState.bottomSheetState.expand() } },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = null,
                                tint = Color(0xFF063BFA),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                HorizontalDivider(thickness = 1.dp, color = Color(0xFFE3E3E3))

                ProfileMenuItem(
                    R.drawable.group1,
                    "My order",
                    if (totalOrdersCount == "0") null else totalOrdersCount
                )

                ProfileMenuItem(R.drawable.payment_method, "Payment method")

                ProfileMenuItem(R.drawable.shipping_address, "Shipping address")

                HorizontalDivider(thickness = 1.dp, color = Color(0xFFE3E3E3))

                ProfileMenuItem(R.drawable.fqa, "FQA")

                ProfileMenuItem(R.drawable.invite_friends, "Invite friends")

                ProfileMenuItem(R.drawable.settings, "Settings")

                HorizontalDivider(thickness = 1.dp, color = Color(0xFFE3E3E3))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp, top = 16.dp)
                    .clickable { /* Logout Logic */ },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logout),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = "Logout", color = Color.Gray, fontSize = 15.sp)
            }
        }
    }
}

@Composable
fun ProfileMenuItem(imageRes: Int, title: String, badge: String? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title, fontSize = 15.sp)
        Spacer(modifier = Modifier.weight(1f))
        badge?.let { Text(text = it, color = Color(0xFFAD2629), fontWeight = FontWeight.Bold) }
    }
}

@Composable
fun EditProfileBottomSheetContent(
    currentProfile: UserProfile,
    onSave: (UserProfile) -> Unit,
    onClose: () -> Unit
) {
    var name by remember { mutableStateOf(currentProfile.name) }
    var phone by remember { mutableStateOf(currentProfile.phone) }
    var email by remember { mutableStateOf(currentProfile.email) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.7f)
            .padding(24.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            IconButton(onClick = onClose) {
                Icon(Icons.Default.Close, contentDescription = null, tint = Color.Red)
            }
        }
        Text(
            "Edit Profile",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        EditField(label = "Your Name", value = name, onValueChange = { name = it })
        EditField(label = "Your Phone number", value = phone, onValueChange = { phone = it })
        EditField(label = "Your Email Address", value = email, onValueChange = { email = it })

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { onSave(UserProfile(name, phone, email, currentProfile.imageRes)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAD2629)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Done", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun EditField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 4.dp, start = 4.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFF7F7F7),
                focusedContainerColor = Color(0xFFF7F7F7),
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color(0xFFAD2629)
            ),
            shape = RoundedCornerShape(8.dp),
            singleLine = true
        )
    }
}