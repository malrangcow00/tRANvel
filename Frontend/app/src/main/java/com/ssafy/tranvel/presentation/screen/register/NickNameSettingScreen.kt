package com.ssafy.tranvel.presentation.screen.register

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.tranvel.presentation.ui.theme.PrimaryColor
import com.ssafy.tranvel.presentation.ui.theme.TextColor

@SuppressLint("UnrememberedMutableState")
@Composable
fun NickNameSettingScreen(
    viewModel: RegisterUserViewModel,
    onNextButtonClicked: () -> (Unit)
) {
    Log.d("MYTAG", "PasswordSettingScreen: ${viewModel.id}")
    val currentState: Boolean by viewModel.currentState.collectAsState()
    Column(
        modifier = Modifier.padding(20.dp)
    ) {
        Text(
            text = "닉네임 등록",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            value = viewModel.nickname.value,
            onValueChange = {
                if (it.length < 9) {
                    viewModel.nickname.value = it
                }
            },
            shape = RoundedCornerShape(5.dp),
            trailingIcon = {
                Button(
                    onClick = {
                        viewModel.duplicateNickName()
                    },
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
                        text = "중복 확인",
                        color = TextColor,
                        fontSize = 14.sp
                    )
                }
            },
            singleLine = true,
            label = { Text(text = "닉네임") },
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
                onNextButtonClicked()
            },
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryColor,
                disabledContainerColor = PrimaryColor
            ),
        ) {
            Text(
                text = "다음",
                color = TextColor,
                fontSize = 18.sp
            )
        }
    }
}