package com.ssafy.tranvel.presentation.screen.login.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ssafy.tranvel.presentation.ui.theme.PrimaryColor
import com.ssafy.tranvel.presentation.ui.theme.TextColor

@Composable
fun LoginTextFieldComponent(
    info: String,
    value: String
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp),
        value = value,
        onValueChange = {

        },
        label = { Text(text = info)},
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = PrimaryColor,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedLabelColor = TextColor,
            unfocusedLabelColor = TextColor
        )
    )
}