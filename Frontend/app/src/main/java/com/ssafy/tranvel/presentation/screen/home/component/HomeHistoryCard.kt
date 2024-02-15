package com.ssafy.tranvel.presentation.screen.home.component

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import com.ssafy.tranvel.data.model.dto.HistoryDto
import com.ssafy.tranvel.presentation.screen.history.HistoryViewModel
import com.ssafy.tranvel.presentation.screen.history.component.HistoryImageContent
import com.ssafy.tranvel.presentation.screen.history.navigation.navigateHistory
import com.ssafy.tranvel.presentation.ui.theme.bmjua
import kotlinx.coroutines.flow.Flow
import java.text.DecimalFormat

private const val TAG = "HomeHistoryCard_싸피"

@Composable
fun HomeHistoryCard(
    index : Int,
    viewModel: HistoryViewModel,
    historyDto: HistoryDto?,
    navController: NavController
) {
    var colors = listOf<Long>(
        0xFFFF0000,
        0xFFFF7F00,
        0xFFFFFF00,
        0xFF00FF00,
        0xFF0000FF,
        0xFF4B0082,
        0xFF9400D3
    )

    val profit = if (historyDto?.balanceResult == null) 0 else moneyFormatter(historyDto.balanceResult)
    val endDateString = if (historyDto?.endDate == null) "" else " ~ " + (historyDto?.endDate.orEmpty()).substring(5, 10)

    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable {
                Log.d(TAG, "HomeHistoryCard: historyDto => ${historyDto}")
                Log.d(TAG, "HomeHistoryCard: 대체 어디가 문제야")
                navController.navigateHistory(historyDto)
            },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                modifier = Modifier
                    .weight(0.4f)
                    .padding(10.dp),
                text = (historyDto?.startDate.orEmpty()).substring(5, 10) + endDateString,
                style = TextStyle(
                    fontFamily = bmjua,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    letterSpacing = 0.5.sp,
                    color = Color.Black
                ),
            )
            Column(
                modifier = Modifier
                    .weight(0.4f)
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxSize(0.5f)
                        .aspectRatio(1f)
                        .background(color = Color(colors[index % 7]), shape = CircleShape)
                        .border(BorderStroke(1.dp, color = Color.Black), CircleShape)
                )
                Canvas(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxSize()
                ) {
                    val canvasWidth = size.width
                    drawLine(
                        color = Color.Black,
                        start = Offset(canvasWidth / 2, -20.dp.toPx()),
                        end = Offset(canvasWidth / 2, 200.dp.toPx()),
                        strokeWidth = 2.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 40f), 10f)
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
            }
            Column(
                modifier = Modifier.weight(1.2f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1.2f)
                ) {
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .padding(10.dp),
                        text = if (historyDto?.roomName == null) "방 이름 없음" else historyDto.roomName,
                        style = TextStyle(
                            fontFamily = bmjua,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            letterSpacing = 0.5.sp,
                            color = Color.Black
                        ),
                    )
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .padding(10.dp),
                        text = profit.toString(),
                        style = TextStyle(
                            textAlign = TextAlign.End,
                            fontFamily = bmjua,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            letterSpacing = 0.5.sp,
                            color = Color.Black
                        ),
                    )
                }

                LazyRow(
                    modifier = Modifier.weight(0.8f),
                    horizontalArrangement = Arrangement.spacedBy((-10).dp),
                    verticalAlignment = Alignment.CenterVertically,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    val itemCount = if (historyDto?.images == null) 0 else historyDto.images.size
                    items(itemCount) { item ->
                        HistoryImageContent(
                            item, historyDto,
                            modifier = Modifier
                                .width(30.dp)
                                .height(40.dp)
                        )
                    }
                }
            }
        }
    }
}

fun moneyFormatter(money: Int): String {
    var dec = DecimalFormat("#,###")
    var output = dec.format(money)
    return output.toString() + "원"
}

@Composable
private fun <T> rememberFlowWithLifecycle(
    flow: Flow<T>,
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): Flow<T> = remember(flow, lifecycle) {
    flow.flowWithLifecycle(
        lifecycle = lifecycle,
        minActiveState = minActiveState
    )
}
