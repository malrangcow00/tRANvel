package com.ssafy.tranvel.presentation.screen.travel.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ssafy.tranvel.presentation.screen.travel.GameViewModel
import com.ssafy.tranvel.presentation.screen.travel.RoomInfo
import com.ssafy.tranvel.presentation.ui.theme.TextColor
import kotlinx.coroutines.launch

@Composable
fun GameHeader(
    title: String,
    visibility: Boolean,
    gameViewModel: GameViewModel
) {
    var dialogState by remember {
        mutableStateOf(false)
    }

    if (dialogState) {
        EnterSettingDialog(
            onChangeState = {
                dialogState = false
            },
            onGameEnd = {
                dialogState = false
                gameViewModel.sendRoomMessage("CLOSE", it)
            }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.08f)
            .background(color = Color(0xFFDEF5E5))
            .padding(start = 20.dp, end = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = title,
            fontSize = 25.sp,
            color = Color.Black,
        )
        if (visibility && RoomInfo.authority) {
            IconButton(
                onClick = {
                    dialogState = true
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "환경설정"
                )
            }
        }
    }
}

@Composable
fun EnterSettingDialog(onChangeState: () -> (Unit), onGameEnd: (String) -> (Unit)) {
    var gameEnd by remember {
        mutableStateOf(false)
    }
    var inputText by remember {
        mutableStateOf("")
    }
    Dialog(onDismissRequest = { onChangeState() }) {
        if (gameEnd) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White, shape = RoundedCornerShape(10.dp)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "이번 여행의 제목을 써주세요.",
                    style = MaterialTheme.typography.headlineMedium
                )
                TextField(
                    value = inputText,
                    onValueChange = {
                        inputText = it
                    }
                )
                Button(onClick = { onGameEnd(inputText) }) {
                    Text(text = "저장 하기", color = TextColor)
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White, shape = RoundedCornerShape(10.dp)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    Text(text = "방 코드: ", style = MaterialTheme.typography.headlineMedium)
                    Text(text = RoomInfo.roomCode, style = MaterialTheme.typography.headlineMedium)
                }
                Row {
                    Text(text = "비밀번호: ", style = MaterialTheme.typography.headlineMedium)
                    Text(
                        text = RoomInfo.roomPassword,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
                Button(onClick = { gameEnd = true }) {
                    Text(text = "여행 종료", color = TextColor)
                }
            }
        }
    }
}
