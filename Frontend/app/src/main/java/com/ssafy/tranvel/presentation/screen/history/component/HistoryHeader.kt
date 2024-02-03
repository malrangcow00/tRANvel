package com.ssafy.tranvel.presentation.screen.history.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.tranvel.R
import kotlinx.coroutines.launch

@Composable
fun HistoryHeader(
    drawerState : DrawerState
){
    val scope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.08f)
            .background(color = Color(0xFFDEF5E5))
            .padding(start = 20.dp),
    ){
        IconButton(
            modifier = Modifier.align(Alignment.CenterVertically),
            onClick = {
                scope.launch {
                    drawerState.apply {
                        if(isClosed) open() else close()
                    }
                }
            },
        ){
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "사이드 메뉴 아이콘",
                modifier = Modifier.fillMaxSize()
            )
        }

        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = "홈 화면",
            fontSize = 25.sp,
            color = Color.Black,
        )
        Image(
            painter = painterResource(id = R.drawable.historyicon),
            contentDescription = "히스토리 아이콘",
            contentScale = ContentScale.Fit
        )
    }
}