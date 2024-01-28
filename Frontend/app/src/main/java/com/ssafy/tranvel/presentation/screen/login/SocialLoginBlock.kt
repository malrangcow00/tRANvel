package com.ssafy.tranvel.presentation.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.tranvel.R

@Preview
@Composable
fun SocialLoginBlock() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){

        Image(
            painter = painterResource(id = R.drawable.kakao_login_large),
            contentDescription = "카카오 로그인",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
                .padding(end = 5.dp)
                .clickable { }
        )

        Image(
            painter = painterResource(id = R.drawable.android_light_sq_ctn),
            contentDescription = "카카오 로그인",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
                .padding(start = 5.dp)
                .clickable { }
        )
    }
}