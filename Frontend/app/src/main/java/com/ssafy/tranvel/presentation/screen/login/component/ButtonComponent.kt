package com.ssafy.tranvel.presentation.screen.login.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.tranvel.presentation.ui.theme.PrimaryColor
import com.ssafy.tranvel.presentation.ui.theme.TextColor

@Composable
fun ButtonComponent(
    onClick: () -> Unit
) {
    Button(
    onClick = {
              onClick()
    },
    modifier = Modifier
    .wrapContentHeight()
    .padding(top = 10.dp)
    .fillMaxWidth(),
    colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor)
    ) {
        Text(text = "Sign in", color = TextColor, fontSize = 18.sp)
    }
}