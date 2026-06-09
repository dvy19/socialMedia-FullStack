package com.example.socialmediaf.posts

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.socialmediaf.SessionManager

// Soft Light Palette Colors
val LightBackground = Color(0xFFFBFDFD)
val PrimaryColor = Color(0xFF006685)
val SurfaceVariant = Color(0xFFDCE3E9)
val OnSurfaceText = Color(0xFF191C1E)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostScreen(
    mainNavController: NavController
) {


    val context= LocalContext.current

    val sessionManager= SessionManager(context)

    val repo= PostRepository(sessionManager)

    val viewModel: PostViewModel=viewModel(
        factory= PostViewModelFactory(repo)
    )


    val state=viewModel.createPostState.collectAsState().value

    when(state){

        is CreatePostState.Idle -> {

        }

        is CreatePostState.Loading -> {

        }

        is CreatePostState.Success -> {
            Toast.makeText(context,"created",Toast.LENGTH_SHORT).show()

            Log.d("M", "done")



        }

        is CreatePostState.Error -> {


        }
    }

    // State management for input fields
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    // State for the Category Dropdown
    var expanded by remember { mutableStateOf(false) }
    val categories = listOf("tech", "education", "lifestyle")
    var selectedCategory by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Create Post",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {  }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Navigate back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = LightBackground,
                    titleContentColor = OnSurfaceText,
                    navigationIconContentColor = OnSurfaceText
                )
            )
        },
        containerColor = LightBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // 1. Title Input Field
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                placeholder = { Text("Give your post a catchy title") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryColor,
                    focusedLabelColor = PrimaryColor,
                    cursorColor = PrimaryColor
                )
            )

            // 2. Category Dropdown Menu
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedCategory,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Category") },
                    placeholder = { Text("Choose a topic") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(), // Standard M3 anchor alignment
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryColor,
                        focusedLabelColor = PrimaryColor
                    )
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(LightBackground)
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(text = category, style = MaterialTheme.typography.bodyLarge) },
                            onClick = {
                                selectedCategory = category
                                expanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }

            // 3. Content Input Field (Multi-line)
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Content") },
                placeholder = { Text("What is on your mind? Share it here...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp), // Fixed height to feel like a content drafting space
                maxLines = 10,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryColor,
                    focusedLabelColor = PrimaryColor,
                    cursorColor = PrimaryColor
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            // 4. Submit Button
            Button(
                onClick = {
                    if (title.isNotBlank() && content.isNotBlank() && selectedCategory.isNotBlank()) {

                        var req= PostRequest(
                            title=title,
                            content=content,
                            category=selectedCategory

                        )
                        viewModel.createAPost(req)
                    }
                },
                enabled = title.isNotBlank() && content.isNotBlank() && selectedCategory.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryColor,
                    contentColor = Color.White
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = "Publish Post",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

