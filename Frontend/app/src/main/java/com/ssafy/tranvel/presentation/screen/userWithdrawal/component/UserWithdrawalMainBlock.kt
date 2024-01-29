package com.ssafy.tranvel.presentation.screen.userWithdrawal.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.tranvel.presentation.ui.theme.PrimaryColor
import com.ssafy.tranvel.presentation.ui.theme.TextColor
import com.ssafy.tranvel.presentation.ui.theme.bmjua

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun UserWithdrawalMainBlock() {
    val reasons = listOf("선택", "앱을 사용하기 불편해요.", "더 이상 사용하지 않아요.", "기타")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(reasons[0]) }

    Column(
        modifier = Modifier.fillMaxWidth().padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = "탈퇴 사유를 알려주세요.",
            color = TextColor,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = bmjua,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(60.dp))
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            },
        ) {
            TextField(
                modifier = Modifier.menuAnchor(),
                readOnly = true,
                value = selectedOptionText,
                onValueChange = {},
                label = { Text("탈퇴 사유",fontFamily = bmjua) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(
                    focusedIndicatorColor = PrimaryColor,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedLabelColor = TextColor,
                    unfocusedLabelColor = TextColor
                )
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                },
                modifier = Modifier.background(color = PrimaryColor),
            ) {
                reasons.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(text = selectionOption, fontFamily = bmjua) },
                        onClick = {
                            selectedOptionText = selectionOption
                            expanded = false
                        },
                    )
                    Divider(
                        thickness = 1.dp,
                        color = Color(0x99e3e3e3)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(100.dp))
        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth(0.8f),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor)
        ){
            Text(text = "회원 탈퇴", color = TextColor, fontSize = 24.sp, fontFamily = bmjua)
        }
    }

}
