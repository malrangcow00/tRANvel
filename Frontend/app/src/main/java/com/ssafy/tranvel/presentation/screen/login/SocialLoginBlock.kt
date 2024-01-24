package com.ssafy.tranvel.presentation.screen.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ssafy.tranvel.R
import com.ssafy.tranvel.presentation.screen.login.component.ImageButtonComponent

@Preview
@Composable
fun SocialLoginBlock() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        ImageButtonComponent(
            id = R.drawable.kakao_login_large,
            info = "카카오로그인"
        )

        ImageButtonComponent(
            id = R.drawable.android_light_sq_ctn,
            info = "구글 로그인"
        )
    }
}