package com.ssafy.tranvel.presentation.screen.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ssafy.tranvel.R
import com.ssafy.tranvel.presentation.ui.theme.PrimaryColor
import com.ssafy.tranvel.presentation.ui.theme.TextColor
import com.ssafy.tranvel.presentation.ui.theme.bmjua

@Composable
fun HomeRoomBody(
//    roomViewModel: RoomViewModel,
    onEnterButtonClicked: () -> Unit,
    onCreateButtonClicked: () -> Unit,
) {
//    val uiState: String by roomViewModel.uiState.collectAsState()
    var isError by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(10.dp)
    ) {
        Spacer(modifier = Modifier.width(20.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.4f),
            shape = RoundedCornerShape(5.dp),
            value = "",
//            value = homeViewModel.enterCode.value,
            onValueChange = {
//                homeViewModel.enterCode.value = it
            },
            isError = isError,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
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
                errorContainerColor = Color.White,
                errorLabelColor = Color.Red
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
                onEnterButtonClicked()
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
                onCreateButtonClicked()
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
