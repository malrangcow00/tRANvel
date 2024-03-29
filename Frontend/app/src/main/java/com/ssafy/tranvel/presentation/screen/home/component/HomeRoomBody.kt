package com.ssafy.tranvel.presentation.screen.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ssafy.tranvel.R
import com.ssafy.tranvel.data.model.dto.Room
import com.ssafy.tranvel.presentation.screen.login.LoadingDialog
import com.ssafy.tranvel.presentation.screen.travel.RoomInfo
import com.ssafy.tranvel.presentation.screen.travel.TravelViewModel
import com.ssafy.tranvel.presentation.ui.theme.PrimaryColor
import com.ssafy.tranvel.presentation.ui.theme.TextColor
import com.ssafy.tranvel.presentation.ui.theme.bmjua

@Composable
fun HomeRoomBody(
    travelViewModel: TravelViewModel,
    onEnterButtonClicked: () -> Unit,
) {
    val uiState: Boolean by travelViewModel.roomBodyState.collectAsState()
    val currentState: Boolean by travelViewModel.currentState.collectAsState()

    var inputCode: String by remember {
        mutableStateOf("")
    }

    var dialogState: Boolean by remember {
        mutableStateOf(false)
    }

    var enterDialogState: Boolean by remember {
        mutableStateOf(false)
    }

    if (enterDialogState) {
        EnterRoomDialog(
            onChangeState = { enterDialogState = false },
            onCreateRoom = { pass ->
                travelViewModel.enterRoom(inputCode, pass)
            }
        )
    }

    if (dialogState) {
        CreateRoomDialog(
            onChangeState = { dialogState = false },
            onCreateRoom = {
                travelViewModel.createRoom(it)
            }
        )
    }

    if (currentState) {
        LoadingDialog {
        }
    }

    if (uiState && RoomInfo.roomCode != "") {
        onEnterButtonClicked()
        travelViewModel.changeRoomBodyState()
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(10.dp)
    ) {
        Spacer(modifier = Modifier.width(20.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.4f),
            shape = RoundedCornerShape(5.dp),
            value = inputCode,
            onValueChange = {
                inputCode = it
            },
            singleLine = true,
            leadingIcon = {
                Image(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.dooricon),
                    contentDescription = "방 입장 아이콘",
                    contentScale = ContentScale.Fit,
                )
            },
            label = {
                Text(text = "입장 코드", textAlign = TextAlign.Center, fontFamily = bmjua)
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = PrimaryColor,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedLabelColor = TextColor,
                unfocusedLabelColor = TextColor,
            )
        )
        Spacer(modifier = Modifier.width(20.dp))
        Button(
            shape = RoundedCornerShape(30),
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(top = 10.dp)
                .width(90.dp)
                .height(50.dp),
            onClick = {
                enterDialogState = true
            }
        ) {
            Text(text = "입장", color = Color.Black, textAlign = TextAlign.Center, fontFamily = bmjua)
        }
        Spacer(modifier = Modifier.width(10.dp))
        Button(
            shape = RoundedCornerShape(30),
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(top = 10.dp)
                .width(90.dp)
                .height(50.dp),
            onClick = {
                dialogState = true
            }
        ) {
            Text(
                text = "만들기",
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontFamily = bmjua
            )
        }
    }
}

@Composable
fun CreateRoomDialog(onChangeState: () -> (Unit), onCreateRoom: (String) -> (Unit)) {
    var inputText by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onChangeState() },
        title = {
            Text(
                text = "방 생성",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        text = {
            OutlinedTextField(
                value = inputText,
                onValueChange = {
                    inputText = it
                },
                label = { Text(text = "비밀번호") },
                placeholder = { Text(text = "방 비밀번호를 입력하세요.") },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
        },

        dismissButton = {
            TextButton(onClick = { onChangeState() }) {
                Text(text = "취소", color = Color.Black)
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onChangeState()
                    onCreateRoom(inputText)
                }) {
                Text(text = "생성", color = Color.Black)
            }
        }
    )
}

@Composable
fun EnterRoomDialog(onChangeState: () -> (Unit), onCreateRoom: (String) -> (Unit)) {
    var inputPassword: String by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onChangeState() },
        title = {
            Text(
                text = "방 입장",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        text = {
            OutlinedTextField(
                value = inputPassword,
                onValueChange = {
                    inputPassword = it
                },
                label = { Text(text = "비밀번호") },
                placeholder = { Text(text = "방 비밀번호를 입력하세요.") },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
        },
        dismissButton = {
            TextButton(onClick = { onChangeState() }) {
                Text(text = "취소", color = Color.Black)
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onChangeState()
                    onCreateRoom(inputPassword)
                }) {
                Text(text = "입장", color = Color.Black)
            }
        }
    )
}