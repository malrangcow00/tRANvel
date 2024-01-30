package com.ssafy.tranvel.presentation.screen.found

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.tranvel.presentation.screen.login.component.ButtonComponent
import com.ssafy.tranvel.presentation.screen.login.component.LoginTextFieldComponent

@Preview
@Composable
fun FoundPasswordScreen(
    viewModel: FoundPasswordViewModel = hiltViewModel()
) {
    val authButtonState: Boolean by viewModel.authButtonState.collectAsState()
    val resetButtonState: Boolean by viewModel.resetButtonState.collectAsState()
    val currentState: Boolean by viewModel.currentState.collectAsState()

    Column(
        modifier = Modifier
            .padding(50.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextComponent(info = "비밀번호를 잊으셨나요?", fontSize = 30.sp, textAlign = TextAlign.Center)
        TextComponent(info = "비밀번호를 재설정하려는 계정(이메일)을 입력해주세요.", fontSize = 14.sp)

        Row {
            Box(
                modifier = Modifier
                    .weight(5f)
                    .padding(end = 5.dp)
            ) {
                LoginTextFieldComponent(
                    info = "아이디",
                    value = viewModel.id,
                    flag = !authButtonState
                )
            }
            Box(
                modifier = Modifier
                    .weight(3f)
                    .padding(start = 5.dp)
            ) {
                ButtonComponent(info = "보내기") {
                    viewModel.sendEmailAuth()
                }
            }
        }
        Row {
            Box(
                modifier = Modifier
                    .weight(5f)
                    .padding(end = 5.dp)
            ) {
                LoginTextFieldComponent(info = "인증번호", value = viewModel.verificationCode)
            }
            Box(
                modifier = Modifier
                    .weight(3f)
                    .padding(start = 5.dp)
            ) {
                ButtonComponent(info = "확인", flag = authButtonState) {
                    viewModel.sendEmailAuthNum()
                }
            }
        }
        ButtonComponent(
            info = "비밀번호 초기화",
            flag = resetButtonState
        ) {
            viewModel.resetPassword()
        }
    }
}