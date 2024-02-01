package com.ssafy.tranvel.presentation.screen.register

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.ssafy.tranvel.presentation.screen.login.component.ButtonComponent
import com.ssafy.tranvel.presentation.ui.theme.PrimaryColor
import com.ssafy.tranvel.presentation.ui.theme.TextColor

@SuppressLint("UnrememberedMutableState")
@Composable
fun PasswordSettingScreen(
    viewModel: RegisterUserViewModel,
    onNextButtonClicked: () -> (Unit)
) {
    var isErrorPassword by remember { mutableStateOf(false) }
    var isErrorVerification by remember { mutableStateOf(false) }
    val currentState: Boolean by viewModel.currentState.collectAsState()

    Column {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp)
                .onFocusChanged {
                    isErrorPassword = if (it.isFocused) {
                        false
                    } else {
                        !viewModel.checkPassword()
                    }
                },
            value = viewModel.password.value,
            onValueChange = {
                viewModel.password.value = it
            },
            textStyle = TextStyle(color = Color.Black),
            singleLine = true,
            isError = isErrorPassword,
            visualTransformation = PasswordVisualTransformation(),
            label = { Text(text = "비밀번호 입력") },
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
                .padding(bottom = 5.dp)
                .onFocusChanged {
                    isErrorVerification = if (it.isFocused) {
                        false
                    } else {
                        !viewModel.matchPassword()
                    }
                },
            value = viewModel.verification.value,
            onValueChange = {
                if (it.length < 16) {
                    viewModel.verification.value = it
                }
            },
            singleLine = true,
            isError = isErrorVerification,
            label = { Text(text = "비밀번호 확인") },
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
        ButtonComponent(info = "다음") {
            onNextButtonClicked()
        }
    }
}