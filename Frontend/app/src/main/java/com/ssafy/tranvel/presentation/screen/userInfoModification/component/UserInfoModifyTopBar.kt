package com.ssafy.tranvel.presentation.screen.userInfoModification.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UserInfoModifyTopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.08f)
            .background(color = Color(0xFFDEF5E5))
            .padding(start = 20.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        UserInfoModifyText(
            text = "회원 정보 수정",
            textAlign = TextAlign.Left,
            fontSize = 25.sp,
            color = Color.Black,
        )
    }
}
