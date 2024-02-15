package com.ssafy.tranvel.presentation.screen.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ssafy.tranvel.presentation.navigation.NavGraph
import com.ssafy.tranvel.presentation.screen.home.navigation.homeRoute
import com.ssafy.tranvel.presentation.screen.login.User
import com.ssafy.tranvel.presentation.ui.theme.TRANvelTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val start = if (User.id == -1L) "login_screen" else homeRoute
        Log.d("TAG", "onCreate: $start")
        setContent {
            TRANvelTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .background(color = Color.White)
                        .fillMaxSize(),
                ) {
                    NavGraph(start)
                }
            }
        }
    }
}
