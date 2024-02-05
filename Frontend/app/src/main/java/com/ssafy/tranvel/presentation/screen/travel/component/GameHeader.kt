package com.ssafy.tranvel.presentation.screen.travel.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun GameHeader(
    drawerState: DrawerState
) {
    val scope = rememberCoroutineScope()
    var visibility: Boolean by remember { mutableStateOf(true) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.08f)
            .background(color = Color(0xFFDEF5E5))
            .padding(start = 20.dp),
    ) {
        IconButton(
            modifier = Modifier.align(Alignment.CenterVertically),
            onClick = {
                scope.launch {
                    drawerState.apply {
                        if (isClosed) open() else close()
                    }
                }
            },
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "사이드 메뉴 아이콘",
                modifier = Modifier.fillMaxSize()
            )
        }

        if (visibility) {
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = "ASEW21",
                fontSize = 25.sp,
                color = Color.Black,
            )
        } else {
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = "asdf1234",
                fontSize = 25.sp,
                color = Color.Black,
            )
        }
        IconButton(onClick = { visibility = !visibility }) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "비밀번호"
            )
        }
    }
}
