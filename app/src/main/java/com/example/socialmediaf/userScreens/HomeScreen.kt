package com.example.socialmediaf.userScreens

import androidx.compose.foundation.lazy.items
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.socialmediaf.SessionManager
import com.example.socialmediaf.posts.PostRepository
import com.example.socialmediaf.posts.PostViewModel
import com.example.socialmediaf.posts.PostViewModelFactory
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.socialmediaf.posts.GetAllPostState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.socialmediaf.posts.PostResponse
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    rootNavController: NavController,
    onAddPostClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onMessageClick: () -> Unit
){

    val context= LocalContext.current

    val sessionManager= SessionManager(context)

    val repository= PostRepository(sessionManager)
    val viewModel: PostViewModel=viewModel(
        factory= PostViewModelFactory(repository)
    )

    val lightBackground = Color(0xFFFAFAFC) // Crisp, soft off-white
    val primaryBrand = Color(0xFF6366F1)    // Creative Indigo accent
    val textPrimary = Color(0xFF1E1E24)

    val token=sessionManager.getAuthToken()

    LaunchedEffect(Unit) {

        token?.let {

            Log.d("TOKEN", token.toString())


            viewModel.fetchAllPosts()
        }
    }

    val getPostState by viewModel.getAllPostState.collectAsState()

    Scaffold(
        containerColor = lightBackground,

        // 🔹 TOP APP BAR
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Home",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = textPrimary
                    )
                },
                // Moving action items to the left/navigation section as requested
                navigationIcon = {
                    Row(
                        modifier = Modifier.padding(start = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = onNotificationClick) {
                            Icon(
                                imageVector = Icons.Outlined.Notifications,
                                contentDescription = "Notifications",
                                tint = textPrimary
                            )
                        }
                        IconButton(onClick = onMessageClick) {
                            Icon(
                                imageVector = Icons.Outlined.ChatBubbleOutline,
                                contentDescription = "Messages",
                                tint = textPrimary
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = lightBackground,
                    titleContentColor = textPrimary
                )
            )
        },

        // ➕ FLOATING ACTION BUTTON (Add Post)
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddPostClick,
                containerColor = primaryBrand,
                contentColor = Color.White,
                shape = FloatingActionButtonDefaults.shape
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Create Post",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    ) { paddingValues -> // ⚠️ Critical: standard scaffold inner padding handles system/top bar spacing

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (getPostState) {
                is GetAllPostState.Idle -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Nothing to Show",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray
                        )
                    }
                }

                is GetAllPostState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = primaryBrand)
                    }
                }

                is GetAllPostState.Success -> {
                    val posts = (getPostState as GetAllPostState.Success).posts
                    PostVerticalList(
                        posts = posts,
                        onViewClick = {},
                        mainNavController = rootNavController
                    )
                }

                is GetAllPostState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = (getPostState as GetAllPostState.Error).message,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}




@Composable
fun PostVerticalList(
    posts: List<PostResponse>,
    onViewClick: () -> Unit,
    //onCommentClick: (JobResponse) -> Unit = {},
    mainNavController: NavController
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(posts) { post ->

            PostItem(
                post=post,
                onViewClick = {
                    Log.d("job id", post.id.toString())
                    mainNavController.navigate("jobDetail/${post.category}")
                },
                //onCommentClick = { onCommentClick(job) }
            )
        }
    }
}


@Composable
fun PostItem(
    post: PostResponse,
    onViewClick: () -> Unit,
    onLikeClick: (Boolean) -> Unit = {},
    onSaveClick: (Boolean) -> Unit = {}
) {
    // Local state for UI responsiveness (or pass these down from your viewmodel)
    var isLiked by remember { mutableStateOf(false) }
    var isSaved by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // 👤 USER INFO HEADER
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Profile Picture (Using placeholder logic - swap with Coil/Glide painter as needed)
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray) // Fallback background
                ) {
                    /* Uncomment if using Coil:
                    Image(
                        painter = rememberAsyncImagePainter(post.userProfilePicUrl),
                        contentDescription = "Profile Picture",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    */
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Name & Location
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = post.user.first_name?:"null",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = post.user.city?:"null",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 📝 POST CONTENT
            Text(
                text = post.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.1.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = post.content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(12.dp))
            Divider(color = Color.LightGray.copy(alpha = 0.3f), thickness = 1.dp)
            Spacer(modifier = Modifier.height(4.dp))

            // 🏎️ ACTIONS ROW (Like & Save)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // ❤️ Like Button
                IconButton(onClick = {
                    isLiked = !isLiked
                    onLikeClick(isLiked)
                }) {
                    Icon(
                        imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Like Button",
                        tint = if (isLiked) Color.Red else Color.Gray
                    )
                }

                // 🔖 Save Button
                IconButton(onClick = {
                    isSaved = !isSaved
                    onSaveClick(isSaved)
                }) {
                    Icon(
                        imageVector = if (isSaved) Icons.Outlined.Bookmark else Icons.Outlined.BookmarkBorder,
                        contentDescription = "Save Button",
                        tint = if (isSaved) MaterialTheme.colorScheme.primary else Color.Gray
                    )
                }
            }
        }
    }
}