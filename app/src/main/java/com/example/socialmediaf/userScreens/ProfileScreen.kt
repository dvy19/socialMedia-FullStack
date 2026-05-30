
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.navigation.NavController
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.socialmediaf.SessionManager
import com.example.socialmediaf.userDetails.DetailRepository
import com.example.socialmediaf.userDetails.DetailViewModelFactory
import com.example.socialmediaf.userDetails.UserDetailViewModel
import androidx.compose.runtime.collectAsState
import com.example.socialmediaf.userDetails.UserDetailState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.PersonAdd
import com.example.socialmediaf.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
                  mainNavController: NavController


) {

    val accentColor = Color(0xFFBB86FC)
    val surfaceColor = Color(0xFF1E1E1E)

    val context= LocalContext.current
    val sessionManager= SessionManager(context)
    val repository= DetailRepository(sessionManager)

    val viewModel: UserDetailViewModel=viewModel(
        factory= DetailViewModelFactory(repository)
    )

    var state = viewModel.userDetailState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getUserProfile()
    }

    Scaffold(

        topBar = {

            TopAppBar(

                title = {
                    Text("Your Profile")
                },

                navigationIcon = {

                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = null
                        )
                    }
                },

                actions = {

                    IconButton(
                        onClick = {
                           // mainNavController.navigate(Screens.Suggestion.route)


                        }
                    ) {
                        Icon(
                            Icons.Default.Message,
                            contentDescription = null
                        )
                    }
                }
            )
        },

        floatingActionButton = {

            FloatingActionButton(
                onClick = {

                }
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = null
                )
            }
        }

    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            when (val result = state) {

                is UserDetailState.Idle -> {

                    Text("Loading...")
                }

                is UserDetailState.Loading -> {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is UserDetailState.Success -> {

                    val profile = result.userData

                    ProfileScreenLayout(

                        onLogout = {
                            sessionManager.logout()

                            mainNavController.navigate("login")
                        },

                        onActivity = {

                        },

                        first_name = profile.first_name,
                        bio = profile.bio
                    )
                }

                is UserDetailState.Error -> {

                    Text("Error")
                }
            }
        }
    }
}



@Composable
private fun ProfileStat(label: String, count: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = count.toString(),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun ProfileScreenLayout(
    onLogout: () -> Unit,
    onActivity:()->Unit,
    first_name:String,
    bio:String
){

    val backgroundColor = Color(0xFF121212)
    val surfaceColor = Color(0xFF1E1E1E)
    val accentColor = Color(0xFFBB86FC)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        // --- Profile Header Section ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Photo Placeholder
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(surfaceColor)
                    .border(2.dp, accentColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("JD", color = accentColor, fontSize = 32.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (false) {
                CircularProgressIndicator()
            } else {
                Text(
                    text = first_name,
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Stats
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                    ProfileStat("Posts", 0)
                    ProfileStat("Followers", 0)
                    ProfileStat("Following", 0)
                }

            }




            Spacer(modifier = Modifier.height(24.dp))

            // --- Action Buttons ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onActivity,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = surfaceColor),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("View Activity")
                }

                Button(
                    onClick = onLogout,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCF6679)), // Error/Red tone
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.ExitToApp, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Logout", color = Color.White)
                }
            }
        }

        Divider(color = Color.DarkGray, thickness = 1.dp)

        // --- User's Posts Area ---
        Text(
            text = "My Posts",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(16.dp)
        )

        // Posts
    /*
        LazyColumn {
            items(posts) { post ->
                SocialPostItem(
                    post = post,
                    onProfileClick = {} // already on profile
                )
            }


     */


        }



