package com.ssafy.tranvel.presentation.screen.login.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ssafy.tranvel.presentation.ui.theme.RippleCustomTheme
import com.ssafy.tranvel.presentation.ui.theme.TextColor

@Composable
fun TextButtonComponent(
    info: String,
    onClick: () -> Unit
) {
    CompositionLocalProvider(LocalRippleTheme provides RippleCustomTheme) {
        TextButton(
            onClick = {
                onClick()
            },
            modifier = Modifier.background(color = Color.White)
        ) {
            Text(
                text = info,
                color = TextColor
            )
        }
    }
}