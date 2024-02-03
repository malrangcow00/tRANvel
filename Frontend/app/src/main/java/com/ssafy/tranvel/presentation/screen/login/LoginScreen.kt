package com.ssafy.tranvel.presentation.screen.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ssafy.tranvel.R
import com.ssafy.tranvel.presentation.screen.login.component.ButtonComponent
import com.ssafy.tranvel.presentation.screen.login.component.LoginTextFieldComponent
import com.ssafy.tranvel.presentation.screen.login.component.TextButtonComponent


@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    context: Context = LocalContext.current,
    onNextButtonClicked: () -> (Unit),
    onLoginButtonClicked: () -> (Unit),
) {
    val uiState: String by loginViewModel.uiState.collectAsState(initial = "")

    when (uiState) {
        // 로그인 성공시
        "SUCCESS" -> {
            // 메인 화면으로 이동
            Log.d("MYTAG", "LoginScreen: success")
        }
        // 로그인 실패시
        "ERROR" -> {
            // 현재 페이지 그대로
            Toast.makeText(context, "Please, enter your value", Toast.LENGTH_LONG).show()
        }
        // 통신중
        "LOADING" -> {
            // 로딩 화면 재생
            Log.d("MYTAG", "LoginScreen: loading")
        }
    }

    Column(
        modifier = Modifier.padding(50.dp).background(color = Color.White),
        verticalArrangement = Arrangement.Center
    ) {
        LoginLogo(modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(30.dp))

        LoginTextFieldComponent(
            info = "아이디", loginViewModel.id,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        LoginTextFieldComponent(
            info = "비밀번호", loginViewModel.password,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        ButtonComponent("sign in") {
            loginViewModel.loginUser()
            onLoginButtonClicked()
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButtonComponent(info = "회원가입") {
                // 회원가입 화면으로 이동
                onNextButtonClicked()
            }
            TextButtonComponent(info = "비밀번호 찾기") {
                // 비밀번호 찾기 화면으로 이동
            }
        }
    }
}

@Composable
fun LoginLogo(
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.logorotation))
    val progress by animateLottieCompositionAsState(
        composition, true, iterations = LottieConstants.IterateForever, restartOnPlay = false
    )
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier.fillMaxSize(0.5f)
    )
}
