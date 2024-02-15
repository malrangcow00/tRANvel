package com.ssafy.tranvel.presentation.screen.history.component

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ssafy.tranvel.BuildConfig
import com.ssafy.tranvel.data.model.dto.AdjustmentHistoryDto
import com.ssafy.tranvel.data.model.dto.DetailHistoryRecordDto
import com.ssafy.tranvel.presentation.screen.components.EmptyIndicator
import com.ssafy.tranvel.presentation.screen.components.ResultLoadingIndicator
import com.ssafy.tranvel.presentation.screen.history.DetailHistoryRecordViewModel
import com.ssafy.tranvel.presentation.screen.history.DetailHistoryViewModel
import com.ssafy.tranvel.presentation.screen.home.component.moneyFormatter
import com.ssafy.tranvel.presentation.ui.theme.bmjua
import kotlinx.coroutines.flow.Flow
import my.nanihadesuka.compose.LazyColumnScrollbar

private const val TAG = "HistoryDetailCard_싸피"

//무지개색 색상 hex code list
var rainbows = listOf<Long>(
    0xFFFF0000,
    0xFFFF7F00,
    0xFFFFFF00,
    0xFF00FF00,
    0xFF0000FF,
    0xFF233067,
    0xFF9400D3
)

//무지개색 파스텔톤 색상 hex code list
var pastelRainbows = listOf<Long>(
    0xFFFFADAD,
    0xFFFFD6A5,
    0xFFFDFFB6,
    0xFFCAFFBF,
    0xFF9BF6FF,
    0xFFA0C4FF,
    0xFFBDB2FF,
    0xFFFFC6FF,
)

@Composable
fun HistoryDetailCard(
    roomId: Long,
    index: Int,
    dto: DetailHistoryRecordDto?,
    detailHistoryViewModel: DetailHistoryViewModel,
    detailHistoryRecordViewModel: DetailHistoryRecordViewModel,
) {
//    LaunchedEffect(roomId){
//        detailHistoryViewModel.getAttractionHistory(roomId)
//        detailHistoryViewModel.getAdjustmentHistory(roomId)
//        detailHistoryViewModel.getFoodHistory(roomId)
//        Log.d(TAG, "HistoryDetailCard: ${dto?.contentId}")
//        Log.d(TAG, "HistoryDetailCard: ${detailHistoryViewModel.attractionList}")
//        Log.d(TAG, "HistoryDetailCard: ${detailHistoryViewModel.adjustmentList}")
//        Log.d(TAG, "HistoryDetailCard: ${detailHistoryViewModel.foodList}")
//    }
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth(0.15f)
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(30.dp)
                        .aspectRatio(1f)
                        .background(
                            color = Color(rainbows[(detailHistoryRecordViewModel.cnt - 1 - index) % 7]),
                            shape = CircleShape
                        )
                        .border(BorderStroke(1.dp, color = Color.LightGray), CircleShape),
                ) {
                    Text(
                        text = (detailHistoryRecordViewModel.cnt - index).toString(),
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    modifier = Modifier.height(50.dp),
                    text = if (dto?.dateTime == null) "날짜 없음" else {
                        dto.dateTime.substring(5, 7).toInt()
                            .toString() + "월 " + dto.dateTime.substring(8, 10).toInt()
                            .toString() + "일" + "\n" + dto.dateTime.substring(
                            11,
                            13
                        ) + "시 " + dto.dateTime.substring(14, 16) + "분"
                    },
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
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
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(5.dp))
                        .padding(10.dp)
                        .clickable {
//                            expanded = !expanded
                        }
                ) {
                    LazyRow(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .fillMaxWidth(0.3f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy((-10).dp)
                    ) {
                        val itemCount = if (dto?.images == null) 0 else dto.images.size
                        items(itemCount) { item ->
                            DetailHistoryImageContent(
                                item, dto,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                    Text(
                        text = matchCategory(dto?.historyCategory!!),
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth(0.3f),
                        textAlign = TextAlign.Center,
                    )
                    if (matchCategory(dto?.historyCategory).equals("정산")) {
                        detailHistoryViewModel.adjustmentList.forEach {
                            Log.d(TAG, "HistoryDetailCard: ${it.price}")
                            if (dto.contentId == it.id) {
                                Log.d(TAG, "HistoryDetailCard: 비용 ${it.price}")
                                (if (dto == null) "정산 정보\n없음" else it.price?.let { it1 ->
                                    moneyFormatter(
                                        it1.toInt()
                                    )
                                })?.let { it2 ->
                                    Text(
                                        modifier = Modifier
                                            .align(Alignment.CenterEnd)
                                            .fillMaxWidth(0.3f),
                                        textAlign = TextAlign.Center,
                                        text = it2
                                    )
                                }
                            }
                        }
                    } else if (matchCategory(dto?.historyCategory).equals("여행지")) {
                        Log.d(TAG, "HistoryDetailCard: 값 뭐니 : ${detailHistoryViewModel.attractionList}")
                        Log.d(TAG, "HistoryDetailCard: 사이즈 몇이니 : ${detailHistoryViewModel.attractionList.size}")
                        detailHistoryViewModel.attractionList.forEach {
                            Log.d(TAG, "HistoryDetailCard: attr ${it.attractionList.attrName}")
                            Log.d(TAG, "HistoryDetailCard: attr ${it.attractionList}")
                            if (dto.contentId == it.id) {
                                Log.d(TAG, "HistoryDetailCard: 여행지 ${it.attractionList.attrName}")
                                Text(
                                    modifier = Modifier
                                        .align(Alignment.CenterEnd)
                                        .fillMaxWidth(0.3f),
                                    textAlign = TextAlign.Center,
                                    text = if (it.attractionList.attrName == null) "여행지\n이름 없음" else it.attractionList.attrName
                                )
                            }
                        }
                    } else if (matchCategory(dto?.historyCategory).equals("음식")) {
                        detailHistoryViewModel.foodList.forEach {
                            Log.d(TAG, "HistoryDetailCard: ${it.foodName}")
                            if (dto.contentId == it.id) {
                                Log.d(TAG, "HistoryDetailCard: 음식 ${it.foodName}")
                                Text(
                                    modifier = Modifier
                                        .align(Alignment.CenterEnd)
                                        .fillMaxWidth(0.3f),
                                    textAlign = TextAlign.Center,
                                    text = if (it.foodName == null) "음식 이름 없음" else it.foodName
                                )

                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DetailHistoryImageContent(
    itemIndex: Int,
    dto: DetailHistoryRecordDto?,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp)
    ) {
        TravelImage(
            imgUrl = (BuildConfig.S3_ADDRESS) + dto?.images?.get(itemIndex)!!
        )
    }
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

fun matchCategory(gameType: String): String {
    if (gameType.equals("adjustment")) {
        return "정산"
    } else if (gameType.equals("food")) {
        return "음식"
    } else return "여행지"
}
