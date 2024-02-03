package com.ssafy.tranvel.presentation.screen.register

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.tranvel.presentation.screen.utils.ConverterURIToBitmap
import com.ssafy.tranvel.presentation.ui.theme.PrimaryColor
import com.ssafy.tranvel.presentation.ui.theme.TextColor

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
            .padding(20.dp),
    ) {
        Text(
            text = "프로필 사진 등록",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        if (bitmap == null) {
            Image(
                imageVector = Icons.Default.AddAPhoto,
                contentDescription = "사진 추가",
                modifier = Modifier
                    .size(200.dp)
                    .clickable {
                        launcher.launch("image/*")
                    }
                    .align(Alignment.CenterHorizontally)
            )
        } else {
            Image(
                bitmap = bitmap!!.asImageBitmap(),
                contentScale = ContentScale.FillBounds,
                contentDescription = "some useful description",
                modifier = Modifier
                    .size(150.dp)
                    .clickable {
                        launcher.launch("image/*")
                    }
                    .align(Alignment.CenterHorizontally)
            )
        }
        Button(
            onClick = {
                onNextButtonClicked()
                viewModel.registerUser()
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
                text = "회원가입",
                color = TextColor,
                fontSize = 18.sp
            )
        }
    }
}
