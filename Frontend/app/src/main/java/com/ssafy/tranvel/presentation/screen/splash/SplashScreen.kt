package com.ssafy.tranvel.presentation.screen.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.ssafy.tranvel.presentation.screen.login.User
import com.ssafy.tranvel.presentation.screen.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreen : ComponentActivity() {
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { true }

        lifecycleScope.launch {
            viewModel.isReady.collect{
                when(it){
                    "SUCCESS" ->  {
                        val intent = Intent(this@SplashScreen, MainActivity::class.java)
                        Log.d("TAG", "Splash onCreate:${User.id} ")
                        startActivity(intent)
                        finish()
                    }
                    "FAILED" -> {
                        val intent = Intent(this@SplashScreen, MainActivity::class.java)
                        Log.d("TAG", "Splash onCreate:${User.id} ")
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }
}