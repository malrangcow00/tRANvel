package com.ssafy.tranvel.presentation.screen.register

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
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
import com.ssafy.tranvel.presentation.screen.utils.ConverterURIToBitmap
import com.ssafy.tranvel.presentation.ui.theme.PrimaryColor
import com.ssafy.tranvel.presentation.ui.theme.TextColor

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun RegisterUserScreen(
    context: Context = LocalContext.current,
    viewModel: RegisterUserViewModel = hiltViewModel()
) {
    val bitmap: Bitmap? by viewModel.bitmap.collectAsState(initial = null)
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                viewModel.setBitmap(
                    ConverterURIToBitmap().setImgUri(
                        imgUri = uri,
                        context = context
                    )
                )
            }
        }
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(50.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = { launcher.launch("image/*") },
            modifier = Modifier.size(100.dp)
        ) {
            if (bitmap == null) {
                Icon(
                    imageVector = Icons.Default.AddAPhoto,
                    contentDescription = "이미지 추가하기",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                )
            } else {
                Image(
                    bitmap = bitmap!!.asImageBitmap(),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = "some useful description",
                    modifier = Modifier.size(100.dp)
                )
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
                    value = viewModel.id.value,
                    onValueChange = {
                        viewModel.id.value = it
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    label = { Text(text = "아이디") },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = PrimaryColor,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedLabelColor = TextColor,
                        unfocusedLabelColor = TextColor
                    )
                )
            }
            Box(
                modifier = Modifier
                    .weight(3f)
                    .padding(start = 5.dp)
            ) {
                ButtonComponent(info = "보내기") {
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
                ButtonComponent(info = "확인") {
                }
            }
        }

        Row {
            Box(
                modifier = Modifier
                    .weight(5f)
                    .padding(end = 5.dp)
            ) {
                LoginTextFieldComponent(info = "닉네임", value = mutableStateOf(""))
            }
            Box(
                modifier = Modifier
                    .weight(3f)
                    .padding(start = 5.dp)
            ) {
                ButtonComponent(info = "중복 확인") {
                }
            }
        }
        LoginTextFieldComponent(info = "비밀번호 입력", value = mutableStateOf(""))
        LoginTextFieldComponent(info = "비밀번호 확인", value = mutableStateOf(""))
        ButtonComponent(info = "회원 가입") {

        }
    }
}