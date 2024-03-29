package com.ssafy.tranvel.presentation.screen.userInfoModification.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ssafy.tranvel.presentation.screen.userInfoModification.UserInfoModifyViewModel
import com.ssafy.tranvel.presentation.ui.theme.PrimaryColor
import com.ssafy.tranvel.presentation.ui.theme.TextColor
import com.ssafy.tranvel.presentation.ui.theme.bmjua

@Composable
fun UserInfoModifyMainBlock(
    viewModel: UserInfoModifyViewModel,
    onCancelButtonClicked: () -> Unit,
    onSaveButtonClicked: () -> Unit
) {
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.Start
    )
    {
        Text(
            text = "닉네임", color = TextColor, fontSize = 18.sp, fontWeight = FontWeight.Bold
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            UserInfoModifyTextField(info = "닉네임", value = viewModel.nickname)
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = {
                    viewModel.duplicateNickName()
                },
                modifier = Modifier
                    .wrapContentWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor)
            ) {
                Text(
                    text = "중복 확인",
                    color = TextColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = bmjua
                )
            }
        }
        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "비밀번호", color = TextColor, fontSize = 18.sp, fontWeight = FontWeight.Bold
        )
        UserInfoModifyTextField(info = "비밀번호", value = viewModel.password)
        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "비밀번호 확인", color = TextColor, fontSize = 18.sp, fontWeight = FontWeight.Bold
        )
        UserInfoModifyTextField(info = "비밀번호 확인", value = viewModel.passwordCheck)
        Spacer(modifier = Modifier.height(30.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                onClick = {
                    onCancelButtonClicked()
                },
                modifier = Modifier
                    .weight(1f),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, PrimaryColor),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text(
                    text = "취소",
                    color = TextColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = bmjua
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            Button(
                onClick = {
                    onSaveButtonClicked()
                },
                modifier = Modifier
                    .weight(1f),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor)
            ) {
                Text(
                    text = "저장",
                    color = TextColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = bmjua
                )
            }
        }
    }

}
