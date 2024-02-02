package com.ssafy.tranvel.presentation.screen.mainMenuDrawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.tranvel.R
import com.ssafy.tranvel.presentation.ui.theme.PrimaryColor

@Composable
fun MainMenuDrawerScreen(
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    onSettingClicked: () -> Unit,
    onAnnouncementClicked: () -> Unit,
    onInquiryClicked: () -> Unit,
    onWithdrawalClicked: () -> Unit,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        DrawerHeader(onSettingClicked)
        DrawerMenus(onAnnouncementClicked, onInquiryClicked, onWithdrawalClicked)
    }
}

@Composable
private fun DrawerHeader(
    onSettingClicked: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.35f)
            .background(PrimaryColor)
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .fillMaxSize(0.15f)
                .padding(5.dp)
                .clickable(onClick = onSettingClicked),
            painter = painterResource(id = R.drawable.settingicon),
            contentDescription = "setting",
            contentScale = ContentScale.FillBounds,
        )
        Image(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize(0.45f)
                .clip(CircleShape)
                .border(1.dp, Color(0xFFFFFFFF), CircleShape),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "default profile image",
            contentScale = ContentScale.FillBounds,
        )
        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(30.dp),
            text = "사용자 이름",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun DrawerMenus(
    onAnnouncementClicked: () -> Unit,
    onInquiryClicked: () -> Unit,
    onWithdrawalClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color.White)
            .padding(top = 20.dp)
    ) {
        Button(
            onClick = {
                onAnnouncementClicked()
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        ) {
            Text(
                modifier = Modifier.padding(20.dp),
                text = "공지사항",
                textAlign = TextAlign.Start,
                fontSize = 18.sp,
                color = Color.Black
            )
        }

        Divider(
            color = Color.LightGray,
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Button(
            onClick = {
                onInquiryClicked()
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text(
                modifier = Modifier.padding(20.dp),
                text = "1:1 문의",
                textAlign = TextAlign.Start,
                fontSize = 18.sp,
                color = Color.Black
            )
        }

        Divider(
            color = Color.LightGray,
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Button(
            onClick = {
                onWithdrawalClicked()
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text(
                modifier = Modifier.padding(20.dp),
                text = "회원탈퇴",
                textAlign = TextAlign.Start,
                fontSize = 18.sp,
                color = Color.Black
            )
        }
    }
}