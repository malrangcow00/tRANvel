package com.ssafy.tranvel.presentation.screen.login.component

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.ssafy.tranvel.presentation.ui.theme.TextColor

@Composable
fun TextButtonComponent(
    info: String
) {
    TextButton(
        onClick = {
            Log.d("MYTAG", "눌렸음1")
        },

    ) {
        Text(
            text = info,
            color = TextColor
        )
    }
}