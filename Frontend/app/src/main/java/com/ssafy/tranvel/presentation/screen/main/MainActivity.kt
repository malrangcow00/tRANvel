package com.ssafy.tranvel.presentation.screen.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ssafy.tranvel.presentation.screen.found.FoundPasswordScreen
import com.ssafy.tranvel.presentation.screen.login.LoginScreen
import com.ssafy.tranvel.presentation.screen.userWithdrawal.UserWithdrawalScreen
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
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    FoundPasswordScreen()
                }
            }
        }
    }
}
