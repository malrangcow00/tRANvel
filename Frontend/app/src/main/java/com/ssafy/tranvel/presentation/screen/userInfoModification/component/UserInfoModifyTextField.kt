package com.ssafy.tranvel.presentation.screen.userInfoModification.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.ssafy.tranvel.presentation.ui.theme.PrimaryColor
import com.ssafy.tranvel.presentation.ui.theme.TextColor
import com.ssafy.tranvel.presentation.ui.theme.bmjua

@Composable
fun UserInfoModifyTextField(
    info: String,
    value: MutableState<String>,
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .padding(bottom = 5.dp),
        value = value.value,
        onValueChange = {
                        value.value=it
        },
        enabled = true,
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
