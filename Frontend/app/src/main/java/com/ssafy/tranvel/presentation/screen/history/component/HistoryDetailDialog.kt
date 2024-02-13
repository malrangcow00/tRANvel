package com.ssafy.tranvel.presentation.screen.history.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ssafy.tranvel.BuildConfig
import com.ssafy.tranvel.data.model.dto.DetailHistoryDto
import com.ssafy.tranvel.data.model.dto.DetailHistoryRecordDto
import com.ssafy.tranvel.presentation.screen.components.EmptyIndicator
import com.ssafy.tranvel.presentation.screen.components.HistoryIndicator
import com.ssafy.tranvel.presentation.screen.components.LoadingIndicator
import com.ssafy.tranvel.presentation.screen.components.ResultLoadingIndicator
import com.ssafy.tranvel.presentation.screen.history.DetailHistoryRecordViewModel
import com.ssafy.tranvel.presentation.screen.home.component.moneyFormatter
import com.ssafy.tranvel.presentation.ui.theme.bmjua
import kotlinx.coroutines.flow.Flow

private const val TAG = "HistoryDetailDialog_싸피"

@Composable
fun HistoryDetailDialog(
    roomId: Long,
    dto: DetailHistoryDto,
    onDismiss: () -> Unit,
    showDialog: Boolean,
    detailHistoryRecordViewModel: DetailHistoryRecordViewModel,
    isLoading: Boolean,
    pagedData: Flow<PagingData<DetailHistoryRecordDto>>?,
    pagingItems : LazyPagingItems<DetailHistoryRecordDto>?
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = onDismiss,
        ) {
            DetailHistoryContent(
                roomId,
                dto,
                showDialog,
                isLoading,
                pagedData,
                pagingItems,
                detailHistoryRecordViewModel
            )
        }
    }
}

@Composable
fun DetailHistoryContent(
    roomId: Long,
    dto: DetailHistoryDto,
    showDialog: Boolean,
    isLoading: Boolean,
    pagedData: Flow<PagingData<DetailHistoryRecordDto>>?,
    pagingItems : LazyPagingItems<DetailHistoryRecordDto>?,
    detailHistoryRecordViewModel: DetailHistoryRecordViewModel
) {
    Log.d(TAG, "DetailHistoryContent: detailHistoryRecordViewModel.cnt at Dialog에서: ${detailHistoryRecordViewModel.cnt}")
    Column(
        modifier = Modifier
            .fillMaxHeight(0.7f)
            .fillMaxWidth()
            .background(color = Color.White)
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Row {
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                textAlign = TextAlign.Start,
                text = if (dto?.dateTime == null) "날짜 없음" else {
                    dto.dateTime.substring(5, 7).toInt()
                        .toString() + "월 " + dto.dateTime.substring(8, 10).toInt()
                        .toString() + "일"
                },
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        LazyRow(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.9f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            val itemCount = if (dto?.images == null) 0 else dto.images.size
            items(itemCount) { item ->
                DetailHistoryImageDialogImages(
                    item, dto,
                    modifier = Modifier.size(60.dp)
                )
            }
        }
        Box(modifier = Modifier) {
            Spacer(modifier = Modifier.height(40.dp))
            LazyColumn(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isLoading) {
//                    if (pagingItems != null && detailHistoryRecordViewModel.cnt == 0) {
//                        Log.d(TAG, "DetailHistoryContent: detailHistoryRecordViewModel.cnt1 : ${detailHistoryRecordViewModel.cnt}")
//                        Log.d(TAG, "DetailHistoryContent: pagingItems1 : ${pagingItems?.itemCount}")
//                        detailHistoryRecordViewModel.cnt = pagingItems!!.itemCount
//                    }
                    items(1) {
                        ResultLoadingIndicator()
                    }
                } else if (pagedData != null && pagingItems != null) {
//                    Log.d(TAG, "DetailHistoryContent: pagingItems2 : ${pagingItems?.itemCount}")
//                    Log.d(TAG, "DetailHistoryContent: pagingData2 : ${pagedData}")
//                    if (detailHistoryRecordViewModel.cnt == 0 && pagingItems!!.itemCount != 0) {
//                        detailHistoryRecordViewModel.cnt = pagingItems!!.itemCount
//                    }
//                    Log.d(
//                        TAG,
//                        "DetailHistoryContent: detailHistoryRecordViewModel.cnt3 : ${detailHistoryRecordViewModel.cnt}"
//                    )
                    items(count = detailHistoryRecordViewModel.cnt) { index ->
                        Log.d(TAG, "DetailHistoryContent: index : ${index} dto.roomId : ${roomId}")
                        HistoryRecordCard(
                            index = index,
                            dateTime = dto?.dateTime,
                            dto = pagingItems!![index]
                        )
                    }
                } else {
                    items(1) {
                        Spacer(modifier = Modifier.height(40.dp))
                        Text(
                            modifier = Modifier.align(Alignment.TopCenter),
                            text = "현재 기록된 \n 상세 기록이 없어요ㅠㅠ",
                            fontFamily = bmjua,
                            fontSize = 30.sp,
                            textAlign = TextAlign.Center
                        )
                        EmptyIndicator()
                    }
                }
            }
        }
    }
}

@Composable
fun DetailHistoryImageDialogImages(
    itemIndex: Int,
    dto: DetailHistoryDto?,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
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

@Composable
fun HistoryRecordCard(
    index: Int,
    dateTime: String?,
    dto: DetailHistoryRecordDto?
) {
    var date1: String = if (dateTime == null) "" else {
        dateTime.substring(5, 7).toInt()
            .toString() + "월 " + dateTime.substring(8, 10).toInt()
            .toString() + "일"
    }

    var date2: String = if (dto?.dateTime == null) "" else {
        dto.dateTime.substring(5, 7).toInt()
            .toString() + "월 " + dto.dateTime.substring(8, 10).toInt()
            .toString() + "일"
    }

    if (dto != null && date1.equals(date2)) {
        Log.d(TAG, "HistoryRecordCard: date1 값은 : ${date1}")
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = matchCategory(dto.historyCategory)
                )
                Text(
                    text = dto.detail.orEmpty()
                )
                Text(
                    text = if (matchCategory(dto.historyCategory).equals("정산")) moneyFormatter(dto.moneyResult!!.toInt()) else ""
                )
            }
        }
    }
}

fun matchCategory(gameType: String): String {
    if (gameType.equals("adjustment")) {
        return "정산"
    } else if (gameType.equals("food")) {
        return "음식"
    } else return "장소"
}
