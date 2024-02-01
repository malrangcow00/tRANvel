package com.ssafy.tranvel.presentation.screen.found

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.tranvel.presentation.screen.login.component.ButtonComponent
import com.ssafy.tranvel.presentation.screen.login.component.LoginTextFieldComponent
import com.ssafy.tranvel.presentation.ui.theme.PrimaryColor
import com.ssafy.tranvel.presentation.ui.theme.TextColor

@Composable
fun FoundPasswordScreen(
    viewModel: FoundPasswordViewModel = hiltViewModel(),
    onNextButtonClicked: () -> (Unit)
) {
    val authButtonState: Boolean by viewModel.authButtonState.collectAsState()
    val resetButtonState: Boolean by viewModel.resetButtonState.collectAsState()
    val currentState: Boolean by viewModel.currentState.collectAsState()
    var isError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(20.dp)
    ) {
        Text(
            text = "비밀번호 재설정",
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
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
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
                    enabled = authButtonState,
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(end = 5.dp)
                        .wrapContentWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryColor,
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
            enabled = authButtonState,
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
        Button(
            onClick = {
                viewModel.resetPassword()
                onNextButtonClicked()
            },
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(top = 10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryColor,
            ),
            enabled = resetButtonState
        ) {
            Text(
                text = "다음",
                color = TextColor,
                fontSize = 18.sp
            )
        }
    }
}