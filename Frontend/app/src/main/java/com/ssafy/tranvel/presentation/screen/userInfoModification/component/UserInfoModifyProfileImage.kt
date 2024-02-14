package com.ssafy.tranvel.presentation.screen.userInfoModification.component

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.tranvel.R
import com.ssafy.tranvel.presentation.screen.userInfoModification.UserInfoModifyViewModel
import com.ssafy.tranvel.presentation.screen.utils.ConverterURIToBitmap

@Composable
fun UserInfoModifyProfileImage(
    context: Context = LocalContext.current,
    viewModel: UserInfoModifyViewModel
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
            .fillMaxWidth()
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = { launcher.launch("image/*") },
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(50))
                .align(Alignment.CenterHorizontally)
                .background(color = Color(0xFFDEF5E5))
        ) {
            if (bitmap == null) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "기본 이미지",
                    modifier = Modifier.fillMaxSize().align(Alignment.CenterHorizontally)
                )
            } else {
                Image(
                    bitmap = bitmap!!.asImageBitmap(),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = "유저 프로필 이미지",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

fun loadImage() {

}