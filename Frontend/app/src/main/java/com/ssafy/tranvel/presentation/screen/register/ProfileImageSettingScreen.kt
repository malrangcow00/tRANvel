package com.ssafy.tranvel.presentation.screen.register

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.tranvel.presentation.screen.login.component.ButtonComponent
import com.ssafy.tranvel.presentation.screen.utils.ConverterURIToBitmap

@Composable
fun ProfileImageSettingScreen(
    context: Context = LocalContext.current,
    viewModel: RegisterUserViewModel,
    onNextButtonClicked: () -> (Unit)
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
        ButtonComponent(info = "회원 가입") {
            viewModel.registerUser()
        }
    }
}