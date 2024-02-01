package com.ssafy.tranvel.presentation.screen.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mail
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    Column(
        modifier = Modifier.padding(20.dp)
    ) {
        Text(
            text = "계정 등록",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .onFocusChanged {
                    isError = if (it.isFocused) {
                        false
                    } else {
                        !viewModel.checkId()
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
            trailingIcon = {
                Button(
                    onClick = {
                        viewModel.sendEmailAuth()
                    },
                    enabled = !authButtonState,
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(end = 5.dp)
                        .wrapContentWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryColor,
                        disabledContainerColor = PrimaryColor
                    ),
                    shape = RoundedCornerShape(5.dp),
                ) {
                    Text(
                        text = "전송",
                        color = TextColor,
                        fontSize = 14.sp
                    )
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
        Row {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 5.dp),
                value = viewModel.verificationCode.value,
                onValueChange = {
                    if (it.length < 5) {
                        viewModel.verificationCode.value = it
                    }
                },
                shape = RoundedCornerShape(5.dp),
                trailingIcon = {
                    Button(
                        onClick = {
                            viewModel.sendEmailAuthNum()
                        },
                        enabled = !resetButtonState,
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(end = 5.dp)
                            .wrapContentWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryColor,
                            disabledContainerColor = PrimaryColor
                        ),
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Text(
                            text = "확인",
                            color = TextColor,
                            fontSize = 14.sp
                        )
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
        Button(
            onClick = {
                onNextButtonClicked()
            },
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(top = 10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryColor,
                disabledContainerColor = PrimaryColor
            ),
            enabled = !resetButtonState
        ) {
            Text(
                text = "다음",
                color = TextColor,
                fontSize = 18.sp
            )
        }
    }
}