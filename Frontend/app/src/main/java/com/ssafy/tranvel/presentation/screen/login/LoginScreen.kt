package com.ssafy.tranvel.presentation.screen.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.tranvel.R
import com.ssafy.tranvel.presentation.screen.login.component.ButtonComponent
import com.ssafy.tranvel.presentation.screen.login.component.LoginTextFieldComponent
import com.ssafy.tranvel.presentation.screen.login.component.TextButtonComponent

@Preview
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    context: Context = LocalContext.current
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
        modifier = Modifier.padding(50.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "로고 이미지",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
        )
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
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButtonComponent(info = "회원가입") {
                // 회원가입 화면으로 이동
            }
            TextButtonComponent(info = "비밀번호 찾기") {
                // 비밀번호 찾기 화면으로 이동
            }
        }
    }
}