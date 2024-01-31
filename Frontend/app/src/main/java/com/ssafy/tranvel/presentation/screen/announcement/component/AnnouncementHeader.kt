package com.ssafy.tranvel.presentation.screen.announcement.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AnnouncementHeader(){
    Column(
        modifier = Modifier.padding(20.dp)
    ){
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "공지사항",
            textAlign = TextAlign.Start,
            fontSize = 40.sp,
        )
        Divider(
            color = Color.Black,
            modifier = Modifier.fillMaxWidth().width(1.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))
    }
}