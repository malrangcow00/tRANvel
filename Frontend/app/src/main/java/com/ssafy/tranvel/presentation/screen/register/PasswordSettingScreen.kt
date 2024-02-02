package com.ssafy.tranvel.presentation.screen.register

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val visibilityPassword: Boolean by viewModel.visibilityPassword.collectAsState()
    val visibilityVerification: Boolean by viewModel.visibilityConfirmPassword.collectAsState()

    Column(
        modifier = Modifier.padding(20.dp)
    ) {
        Text(
            text = "비밀번호 등록",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .onFocusChanged {
                    isErrorPassword = if (it.isFocused) {
                        false
                    } else {
                        !viewModel.checkPassword()
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
                            viewModel.changePasswordVisibility()
                        }
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Visibility,
                        contentDescription = "비밀번호",
                        modifier = Modifier.clickable {
                            viewModel.changePasswordVisibility()
                        }
                    )
                }
            },
            value = viewModel.password.value,
            onValueChange = {
                viewModel.password.value = it
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
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
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
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "비밀번호"
                )
            },
            trailingIcon = {
                if (visibilityVerification) {
                    Icon(
                        imageVector = Icons.Default.Visibility,
                        contentDescription = "비밀번호",
                        modifier = Modifier.clickable {
                            viewModel.changeConfirmPasswordVisibility()
                        }
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.VisibilityOff,
                        contentDescription = "비밀번호",
                        modifier = Modifier.clickable {
                            viewModel.changeConfirmPasswordVisibility()
                        }
                    )
                }
            },
            textStyle = TextStyle(color = TextColor),
            visualTransformation = if (visibilityVerification) VisualTransformation.None else PasswordVisualTransformation(),
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
                errorLabelColor = Color.Red,
                errorTrailingIconColor = TextColor,
                errorLeadingIconColor = TextColor
            )
        )
        Button(
            onClick = {
                onNextButtonClicked()
            },
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryColor,
                disabledContainerColor = PrimaryColor
            ),
            enabled = !isErrorPassword && !isErrorVerification
        ) {
            Text(
                text = "다음",
                color = TextColor,
                fontSize = 18.sp
            )
        }
    }
}