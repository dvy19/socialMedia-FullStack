package com.example.socialmediaf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.socialmediaf.ui.theme.SocialMediaFTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SocialMediaFTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RootNav(innerPadding)
                }
            }
        }
    }
}

sealed class Screens(var route: String){

    data object SignupScreen:Screens("signup")
    data object LoginScreen:Screens("login")

    data object MainScreen:Screens("main")
    data object Home:Screens("home")

}
