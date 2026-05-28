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
import com.example.socialmediaf.posts.PostData

@Composable
fun HomeScreen(
    rootNavController: NavController
){

    val context= LocalContext.current

    val sessionManager= SessionManager(context)

    val repository= PostRepository(sessionManager)
    val viewModel: PostViewModel=viewModel(
        factory= PostViewModelFactory(repository)
    )

    val token=sessionManager.getAuthToken()

    LaunchedEffect(Unit) {

        token?.let {

            viewModel.fetchAllPosts(it)
        }
    }

    val getPostState by viewModel.getAllPostState.collectAsState()

    Column(
        modifier=Modifier.fillMaxSize()
    )
    {

        when( getPostState){

            is GetAllPostState.Idle->{
                Text(text = "Nothing to Show")
            }

            is GetAllPostState.Loading->{
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is GetAllPostState.Success-> {

                val posts = (getPostState as GetAllPostState.Success).posts
                PostVerticalList(
                    posts = posts,
                    onViewClick = {},
                    mainNavController = rootNavController
                )
            }


            is GetAllPostState.Error -> {
                Text(
                    text = (getPostState as GetAllPostState.Error).message,
                    color = Color.Red
                )
            }





            }
        }

    }




@Composable
fun PostVerticalList(
    posts: List<PostData>,
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
    post: PostData,
    onViewClick: () -> Unit = {},
    //onCommentClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {

            // 🔹 User Info
            Text(
                text = post.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )


            Text(
                text = post.category,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )


            Spacer(modifier = Modifier.height(8.dp))

            // 🔹 Title
            Text(
                text = post.content,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(4.dp))



            // 🔹 Actions Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                // ❤️ Like Button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onViewClick() }
                ) {
                    Button(
                        onClick = { onViewClick() },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(text = "View")
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    //Text(text = "${post.likes_count}")
                }
            }
        }
    }
}