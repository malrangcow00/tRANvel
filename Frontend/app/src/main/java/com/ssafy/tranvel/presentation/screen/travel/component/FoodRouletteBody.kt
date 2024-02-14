package com.ssafy.tranvel.presentation.screen.travel.component

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.jhdroid.view.RotateListener
import com.ssafy.tranvel.R
import com.ssafy.tranvel.databinding.FoodRouletteLayoutBinding
import com.ssafy.tranvel.presentation.screen.travel.GameViewModel
import com.ssafy.tranvel.presentation.screen.travel.RoomInfo

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun FoodRouletteBody(
    gameViewModel: GameViewModel,
    onNextPressed: () -> (Unit)
) {
    val rouletteData by gameViewModel.foodGameData.collectAsState()
    val rouletteRandom by gameViewModel.random.collectAsState()
    val uiState by gameViewModel.foodScreen.collectAsState()
    var rouletteResultName by remember {
        mutableStateOf("")
    }
    var rouletteResultFood by remember {
        mutableStateOf("")
    }
    val rouletteList = mutableListOf<String>()

    rouletteData.selectedUserInfos.forEach {
        rouletteList.add(it.nickname)
    }

    var rouletteState by remember {
        mutableStateOf(false)
    }

    val rouletteListener = object : RotateListener {
        override fun onRotateStart() {
            // rotate animation start
        }

        override fun onRotateEnd(result: String) {
            // rotate animation end, get result here
            val a = rouletteData.selectedUserInfos.filter {
                it.nickname == result
            }
            rouletteResultName = result
            rouletteResultFood = a[0].submittedFood
            if (RoomInfo.authority) {
                gameViewModel.sendFoodGameMessage("CLOSE", result)
            }
        }
    }

    if (uiState) {
        EnterFoodGameDialog(rouletteResultName, rouletteResultFood){
            gameViewModel.setFoodScreenState()
            onNextPressed()
        }
    }

    var listener: ButtonClick? = null

    if (rouletteRandom.randFloat != 0f && !rouletteState) {
        rouletteState = true
        Log.d("TAG", "FoodRouletteBody: ${rouletteRandom.randFloat}")
        listener?.onClick()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (rouletteList.size >= 2) {
            AndroidViewBinding(
                FoodRouletteLayoutBinding::inflate,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.TopCenter),
                onReset = {
                    // Null out the OnClickListener to avoid leaking the `action` lambda.
                },
                update = {
                    roulette.apply {
                        rouletteSize = rouletteList.size
                        setRouletteDataList(rouletteList)
                    }
                    if (rouletteRandom.randFloat != 0f) {
                        roulette.rotateRoulette(
                            rouletteRandom.randFloat,
                            rouletteRandom.randLong,
                            rouletteListener
                        )
                    }
                }
            )
        }
        Image(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(50.dp),
            painter = painterResource(id = R.drawable.down),
            contentDescription = "pointRoulette",
        )
        val selectedImageList = rouletteData.selectedUserInfos
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            Column {
                Text(text = "입력한 멤버")
                LazyRow(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                ) {
                    items(selectedImageList.size) { item ->
                        Card(
                            modifier = Modifier
                                .padding(end = 3.dp),
                            onClick = {},
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(10)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Box {
                                    Image(
                                        imageVector = Icons.Default.Person2,
                                        contentDescription = "프로필이미지",
                                        modifier = Modifier
                                            .size(50.dp)
                                            .align(Alignment.Center)
                                    )
                                    androidx.compose.material.Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "checking",
                                        modifier = Modifier
                                            .size(10.dp)
                                            .align(Alignment.TopEnd),
                                        tint = Color.Green
                                    )
                                }
                                Text(
                                    text = "$item",
                                    modifier = Modifier
                                        .padding(top = 5.dp)
                                )
                            }
                        }
                    }
                }
                val unSelectedImageList = rouletteData.unSelectedUserInfos
                Text(text = "입력하지 않은 멤버", modifier = Modifier.padding(top = 10.dp))
                LazyRow(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                ) {
                    items(unSelectedImageList.size) { item ->
                        Card(
                            modifier = Modifier
                                .padding(end = 3.dp),
                            onClick = {},
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(10)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Box {
                                    Image(
                                        imageVector = Icons.Default.Person2,
                                        contentDescription = "프로필이미지",
                                        modifier = Modifier
                                            .size(50.dp)
                                            .align(Alignment.Center)
                                    )
                                }
                            }
                        }
                    }
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    onClick = {
                        gameViewModel.sendFoodGameStartMessage("ENTER", "")
                    },
                ) {
                    Text(text = "뽑기")
                }
            }
        }
    }
}

interface ButtonClick {
    fun onClick()
}


@Composable
fun EnterFoodGameDialog(nickName: String, food: String, onChangeState: () -> (Unit)) {

    AlertDialog(
        onDismissRequest = { },
        title = {
            Text(
                text = "${nickName}님이 선택한 음식",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        text = {
            Text(
                text = "${food}가 당첨되었습니다.",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onChangeState()
                }) {
                Text(text = "확인", color = Color.Black)
            }
        }
    )
}
