package com.ssafy.tranvel.presentation.screen.register

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.tranvel.presentation.screen.login.component.ButtonComponent
import com.ssafy.tranvel.presentation.screen.login.component.LoginTextFieldComponent
import com.ssafy.tranvel.presentation.ui.theme.PrimaryColor
import com.ssafy.tranvel.presentation.ui.theme.TextColor

@Composable
fun EmailAuthScreen(
    viewModel: RegisterUserViewModel,
    onNextButtonClicked: () -> (Unit)
) {
    var isError by remember { mutableStateOf(false) }
    val authButtonState: Boolean by viewModel.authButtonState.collectAsState()
    val resetButtonState: Boolean by viewModel.resetButtonState.collectAsState()

    Column {
        Row {
            Box(
                modifier = Modifier
                    .weight(5f)
                    .padding(end = 5.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp)
                        .onFocusChanged {
                            isError = if (it.isFocused) {
                                false
                            } else {
                                !viewModel.checkId()
                            }
                        },
                    value = viewModel.id.value,
                    onValueChange = {
                        viewModel.id.value = it
                    },
                    enabled = !authButtonState,
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
            }
            Box(
                modifier = Modifier
                    .weight(3f)
                    .padding(start = 5.dp)
            ) {
                ButtonComponent(info = "보내기",flag = !authButtonState) {
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
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp),
                    value = viewModel.verificationCode.value,
                    onValueChange = {
                        if (it.length < 5) {
                            viewModel.verificationCode.value = it
                        }
                    },
                    singleLine = true,
                    enabled = !resetButtonState,
                    label = { Text(text = "인증 코드") },
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
            }
            Box(
                modifier = Modifier
                    .weight(3f)
                    .padding(start = 5.dp)
            ) {
                ButtonComponent(info = "확인") {
                    viewModel.sendEmailAuthNum()
                }
            }
        }
        if (resetButtonState){
            ButtonComponent(info = "다음") {
                onNextButtonClicked()
            }
        }
    }
}