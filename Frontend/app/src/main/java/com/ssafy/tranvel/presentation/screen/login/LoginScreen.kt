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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ssafy.tranvel.R
import com.ssafy.tranvel.presentation.screen.login.component.ButtonComponent
import com.ssafy.tranvel.presentation.screen.login.component.LoginTextFieldComponent
import com.ssafy.tranvel.presentation.screen.login.component.TextButtonComponent
import com.ssafy.tranvel.presentation.screen.register.navigation.registerGraph


@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    context: Context = LocalContext.current,
    onNavigateToRegister: ()-> (Unit),
    onNavigateToFound: () -> (Unit),
    onNavigateToHome:() -> (Unit),
    onLoginButtonClicked: () -> (Unit)
) {
    val uiState: String by loginViewModel.uiState.collectAsState(initial = "")
    var isError by remember { mutableStateOf(false) }
    var isErrorPassword by remember { mutableStateOf(false) }
    val visibilityPassword: Boolean by loginViewModel.visibilityPassword.collectAsState()

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
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .onFocusChanged {
                    isError = if (it.isFocused) {
                        false
                    } else {
                        !loginViewModel.checkId()
                    }
                },
            shape = RoundedCornerShape(5.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Mail,
                    contentDescription = "mail",
                    tint = TextColor
                )
            },
            value = loginViewModel.id.value,
            onValueChange = {
                loginViewModel.id.value = it
            },
            isError = isError,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            label = { Text(text = "아이디") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = PrimaryColor,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedLabelColor = TextColor,
                unfocusedLabelColor = TextColor,
                errorContainerColor = Color.White,
                errorLabelColor = Color.Red
            )
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .onFocusChanged {
                    isErrorPassword = if (it.isFocused) {
                        false
                    } else {
                        !loginViewModel.checkPassword()
                    }
                },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "비밀번호"
                )
            },
            trailingIcon = {
                if (!visibilityPassword) {
                    Icon(
                        imageVector = Icons.Default.VisibilityOff,
                        contentDescription = "비밀번호",
                        modifier = Modifier.clickable {
                            loginViewModel.changePasswordVisibility()
                        }
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Visibility,
                        contentDescription = "비밀번호",
                        modifier = Modifier.clickable {
                            loginViewModel.changePasswordVisibility()
                        }
                    )
                }
            },
            value = loginViewModel.password.value,
            onValueChange = {
                loginViewModel.password.value = it
            },
            textStyle = TextStyle(color = TextColor),
            singleLine = true,
            isError = isErrorPassword,
            visualTransformation = if (visibilityPassword) VisualTransformation.None else PasswordVisualTransformation(),
            label = { Text(text = "비밀번호 입력") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = PrimaryColor,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedLabelColor = TextColor,
                unfocusedLabelColor = TextColor,
                errorContainerColor = Color.White,
                errorLabelColor = Color.Red,
                errorTrailingIconColor = TextColor,
                errorLeadingIconColor = TextColor
            )
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
                onNavigateToRegister()
            }
            TextButtonComponent(info = "비밀번호 찾기") {
                // 비밀번호 찾기 화면으로 이동
                onNavigateToFound()
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
