package com.ssafy.tranvel.presentation.screen.travel.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.TextField
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.tranvel.presentation.screen.travel.GameViewModel
import com.ssafy.tranvel.presentation.ui.theme.PrimaryColor
import com.ssafy.tranvel.presentation.ui.theme.TextColor

@Composable
fun FoodBody(
    gameViewModel: GameViewModel,
    onBackPressed: () -> (Unit),
    onNextPressed: () -> (Unit)
) {
    var inputFood: String by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp)
    ) {
        Text(
            text = "메뉴 입력",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 24.sp
        )
        TextField(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
            value = inputFood,
            onValueChange = {
                inputFood = it
            },
            placeholder = { Text(text = "메뉴를 입력해주세요.", color = TextColor) },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = PrimaryColor,
                focusedContainerColor = Color.White,
                focusedLabelColor = TextColor,
                unfocusedContainerColor = Color.White,
                unfocusedLabelColor = TextColor,
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 5.dp),
                onClick = { onBackPressed() },
                border = BorderStroke(color = PrimaryColor, width = 1.dp)
            ) {
                Text(text = "취소", color = TextColor)
            }
            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 5.dp),
                onClick = {
                    gameViewModel.sendFoodGameReadyMessage("ENTER", inputFood)
                    onNextPressed()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryColor,
                ),
                enabled = inputFood != ""
            ) {
                Text(text = "저장", color = TextColor)
            }
        }
    }
}