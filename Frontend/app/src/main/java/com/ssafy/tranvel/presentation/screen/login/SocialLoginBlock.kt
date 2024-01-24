package com.ssafy.tranvel.presentation.screen.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.tranvel.R
import com.ssafy.tranvel.presentation.screen.login.component.ImageButtonComponent

@Preview
@Composable
fun SocialLoginBlock() {
    Column(
    ) {
        ImageButtonComponent(
            id = R.drawable.kakao_login_medium_wide,
            info = "카카오로그인"
        )
        ImageButtonComponent(
            id = R.drawable.kakao_login_medium_wide,
            info = "카카오로그인"
        )
    }
}