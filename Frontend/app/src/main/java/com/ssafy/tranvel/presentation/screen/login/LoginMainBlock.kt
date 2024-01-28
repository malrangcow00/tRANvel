package com.ssafy.tranvel.presentation.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.tranvel.R
import com.ssafy.tranvel.presentation.screen.login.component.LoginTextFieldComponent
import com.ssafy.tranvel.presentation.screen.login.component.TextButtonComponent
import com.ssafy.tranvel.presentation.ui.theme.PrimaryColor
import com.ssafy.tranvel.presentation.ui.theme.TextColor

@Preview
@Composable
fun LoginMainBlock() {

    Column{
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "로고 이미지",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
        )
        Spacer(modifier = Modifier.height(30.dp))

        LoginTextFieldComponent(info = "아이디")
        LoginTextFieldComponent(info = "비밀번호")

        Button(
            onClick = {},
            modifier = Modifier
                .wrapContentHeight()
                .padding(top = 10.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor)
        ) {
            Text(text = "Sign in", color = TextColor, fontSize = 18.sp)
        }
        
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ){
            TextButtonComponent(info = "회원가입")
            TextButtonComponent(info = "비밀번호 찾기")
        }
    }
}