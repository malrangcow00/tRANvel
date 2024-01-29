package com.ssafy.tranvel.presentation.screen.found

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.ssafy.tranvel.presentation.ui.theme.TextColor

@Composable
fun TextComponent(
    info: String,
    fontSize: TextUnit = 18.sp,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = info,
        modifier = Modifier
            .fillMaxWidth(),
        fontSize = fontSize,
        textAlign = textAlign,
        color = TextColor
    )
}