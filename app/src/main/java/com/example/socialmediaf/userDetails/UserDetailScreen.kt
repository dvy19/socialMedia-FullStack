package com.example.socialmediaf.userDetails


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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




    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Social Media",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3852B4),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Add Your Details ",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3852B4),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Reusable Input Fields
        CustomTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = "First Name",
            icon = Icons.Default.Business)

        CustomTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = "Last Name",
            icon = Icons.Default.Person)

        CustomTextField(
            value = gender,
            onValueChange = { gender = it },
            label = "Gender ( Male, Female, Other )",
            icon = Icons.Default.Person)

        CustomTextField(
            value = city,
            onValueChange = { city = it },
            label = "City",
            icon = Icons.Default.LocationOn)

        CustomTextField(
            value = bio,
            onValueChange = { bio = it },
            label = "Bio",
            icon = Icons.Default.Home)

        CustomTextField(
            value = dob,
            onValueChange = { dob = it },
            label = "Date of Birth",
            icon = Icons.Default.Home)

        Spacer(modifier = Modifier.height(24.dp))

        // Submit Button
        Button(
            onClick = {
                val request = UserDetailRequest(
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
                .height(56.dp),
            enabled = firstName.isNotBlank() &&
                    lastName.isNotBlank() &&
                    dob.isNotBlank() &&
                    city.isNotBlank() &&
                    bio.isNotBlank(), // Button is only active if logic is true
            shape = MaterialTheme.shapes.medium
        ) {

            if(detailState is UserDetailState.Loading){
                CircularProgressIndicator()
            }
            else{
                Text(text = "Submit")
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

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = { Icon(imageVector = icon, contentDescription = null) },
        modifier = Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline
        )
    )
}

@Preview
@Composable
fun RecruiterDetailsScreenPreview() {
    UserDetailScreen(rootNavController = rememberNavController())
}