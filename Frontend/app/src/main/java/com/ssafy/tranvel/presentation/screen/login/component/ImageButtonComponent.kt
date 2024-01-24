package com.ssafy.tranvel.presentation.screen.login.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

@Composable
fun ImageButtonComponent(
    id: Int,
    info: String
) {
    Image(
        painter = painterResource(id = id),
        contentDescription = info,
        contentScale = ContentScale.FillHeight,
        modifier = Modifier.fillMaxWidth(0.5f).clickable { }
    )
}