package com.ssafy.tranvel.presentation.screen.login.component

import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
        ) {
            Text(
                text = info,
                color = TextColor
            )
        }
    }
}