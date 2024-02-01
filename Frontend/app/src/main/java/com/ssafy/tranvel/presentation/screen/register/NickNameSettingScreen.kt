package com.ssafy.tranvel.presentation.screen.register

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ssafy.tranvel.presentation.screen.login.component.ButtonComponent
import com.ssafy.tranvel.presentation.screen.login.component.LoginTextFieldComponent

@SuppressLint("UnrememberedMutableState")
@Composable
fun NickNameSettingScreen(
    viewModel: RegisterUserViewModel,
    onNextButtonClicked: () -> (Unit)
) {
    Log.d("MYTAG", "PasswordSettingScreen: ${viewModel.id}")
    val currentState: Boolean by viewModel.currentState.collectAsState()
    Column {
        Box(
            modifier = Modifier
                .weight(5f)
                .padding(end = 5.dp)
        ) {
            LoginTextFieldComponent(info = "닉네임", value = viewModel.nickname)
        }
        Box(
            modifier = Modifier
                .weight(3f)
                .padding(start = 5.dp)
        ) {
            ButtonComponent(info = "중복 확인") {
                viewModel.duplicateNickName()
            }
        }

        ButtonComponent(info = "다음") {
            onNextButtonClicked()
        }
    }
}