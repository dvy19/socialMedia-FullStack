package com.example.socialmediaf.userDetails


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.socialmediaf.SessionManager
import androidx.compose.material.icons.filled.ArrowBack

val DeepPlum = Color(0xFF4A2840)       // Primary branding purple
val WarmCappuccino = Color(0xFF6E554F) // Subdued secondary brown
val LightLavender = Color(0xFFF3EDF2)  // Soft light purple background accent
val CreamBackground = Color(0xFFFAF8F5) // Clean off-white surface color
val OnyxText = Color(0xFF262124)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(
    rootNavController: NavController
) {


    val context=LocalContext.current


    val sessionManager= SessionManager(context)
    val repository= DetailRepository(sessionManager)
    val viewModel: UserDetailViewModel=viewModel(
        factory= DetailViewModelFactory(repository)
    )



    val detailState by  viewModel.userDetailState.collectAsState()




    // State variables for each input field
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var bio by remember{mutableStateOf("")}

    // Logic to check if all fields are populated



    val scrollState = rememberScrollState()
    val genderOptions = listOf("male", "female", "other")

    // Reusable custom styles to clean up field logic
    val customTextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = DeepPlum,
        unfocusedBorderColor = WarmCappuccino.copy(alpha = 0.4f),
        focusedLabelColor = DeepPlum,
        unfocusedLabelColor = WarmCappuccino,
        focusedTextColor = OnyxText,
        unfocusedTextColor = OnyxText
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Set Up Profile", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)) },
                navigationIcon = {
                    IconButton(onClick = { /* Handle navigation back */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = OnyxText)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CreamBackground)
            )
        },
        containerColor = CreamBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // Subtitle text greeting
            Text(
                text = "Tell us a bit about yourself to kickstart your feed.",
                style = MaterialTheme.typography.bodyMedium,
                color = WarmCappuccino
            )

            // 1. Name Row (First Name & Last Name)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("First Name") },
                    shape = RoundedCornerShape(16.dp),
                    colors = customTextFieldColors,
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Last Name") },
                    shape = RoundedCornerShape(16.dp),
                    colors = customTextFieldColors,
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }

            // 2. Custom Gender Card Selector
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Gender",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = WarmCappuccino
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    genderOptions.forEach { option ->
                        val isSelected = gender == option

                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .clickable { gender = option },
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) LightLavender else Color.White
                            ),
                            border = BorderStroke(
                                width = if (isSelected) 2.dp else 1.dp,
                                color = if (isSelected) DeepPlum else WarmCappuccino.copy(alpha = 0.2f)
                            )
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = option,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                    ),
                                    color = if (isSelected) DeepPlum else OnyxText
                                )
                            }
                        }
                    }
                }
            }

            // 3. Location & Birthday Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = { Text("City") },
                    shape = RoundedCornerShape(16.dp),
                    colors = customTextFieldColors,
                    modifier = Modifier.weight(1.2f), // Give City slightly more structural space
                    singleLine = true
                )
                OutlinedTextField(
                    value = dob,
                    onValueChange = { dob = it },
                    label = { Text("YYYY-MM-DD") }, // Ideal for manual entry or clicking custom pickers
                    shape = RoundedCornerShape(16.dp),
                    colors = customTextFieldColors,
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }

            // 4. Bio Text Area
            OutlinedTextField(
                value = bio,
                onValueChange = { bio = it },
                label = { Text("Bio") },
                placeholder = { Text("Share a little about who you are...") },
                shape = RoundedCornerShape(16.dp),
                colors = customTextFieldColors,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp), // Height creates the textual paragraph room
                maxLines = 5
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 5. Submit Action Button
            Button(
                onClick = {
                    val request= UserDetailRequest(
                        bio = bio,
                        city = city,
                        date_of_birth = dob,
                        first_name = firstName,
                        last_name = lastName,
                        gender = gender
                    )

                    viewModel.createUserProfile(request)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DeepPlum,
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
            ) {

                if( detailState is UserDetailState.Loading) {
                    CircularProgressIndicator()
                }
                else{

                    Text(
                        text = "Save & Continue",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
            }
        }
    }



        LaunchedEffect(detailState) {
            when (detailState) {



                is UserDetailState.Success -> {
                    rootNavController.navigate("main")
                }

                is UserDetailState.Error -> {
                    // optional: show toast/snackbar
                }

                else -> {}
            }
        }
    }
}


@Preview
@Composable
fun RecruiterDetailsScreenPreview() {
    UserDetailScreen(rootNavController = rememberNavController())
}