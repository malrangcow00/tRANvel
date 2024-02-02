package com.ssafy.tranvel.presentation.screen.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ssafy.tranvel.presentation.navigation.NavGraph
import com.ssafy.tranvel.presentation.screen.history.HistoryScreen
import com.ssafy.tranvel.presentation.ui.theme.TRANvelTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TRANvelTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .background(color = Color.White)
                        .fillMaxSize(),
                    color = Color.White
                ) {
                    NavGraph()
                }
            }
        }
    }
}
