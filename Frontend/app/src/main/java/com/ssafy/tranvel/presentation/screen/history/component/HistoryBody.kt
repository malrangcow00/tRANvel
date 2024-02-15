package com.ssafy.tranvel.presentation.screen.history.component

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ssafy.tranvel.data.model.dto.AdjustmentHistoryDto
import com.ssafy.tranvel.data.model.dto.AttractionHistoryDto
import com.ssafy.tranvel.data.model.dto.DetailHistoryRecordDto
import com.ssafy.tranvel.data.model.dto.FoodHistoryDto
import com.ssafy.tranvel.domain.viewstate.history.AdjustmentHistoryViewState
import com.ssafy.tranvel.presentation.screen.components.EmptyIndicator
import com.ssafy.tranvel.presentation.screen.components.ResultLoadingIndicator
import com.ssafy.tranvel.presentation.screen.history.DetailHistoryRecordViewModel
import com.ssafy.tranvel.presentation.screen.history.DetailHistoryViewModel
import com.ssafy.tranvel.presentation.ui.theme.bmjua
import kotlinx.coroutines.flow.Flow

private const val TAG = "HistoryBody_싸피"

@Composable
fun HistoryBody(
    paddingValues: PaddingValues,
    roomId: Long,
    detailHistoryViewModel: DetailHistoryViewModel,
    detailHistoryRecordViewModel: DetailHistoryRecordViewModel
) {
    LaunchedEffect(roomId) {
        detailHistoryViewModel.adjustmentCnt = 0
        detailHistoryViewModel.getAdjustmentHistory(roomId)
        detailHistoryViewModel.attractionCnt = 0
        detailHistoryViewModel.getAttractionHistory(roomId)
        detailHistoryViewModel.foodCnt = 0
        detailHistoryViewModel.getFoodHistory(roomId)

        detailHistoryRecordViewModel.cnt = 0
        detailHistoryRecordViewModel.getDetailHistoryRecord(roomId)
        Log.d(
            TAG,
            "HistoryBody: 여기서 detailHistoryRecordViewModel.cnt : ${detailHistoryRecordViewModel.cnt}"
        )
    }

    val recordViewState = detailHistoryRecordViewModel.uiState.collectAsState().value

    Log.d(TAG, "HistoryBody: recordViewState.pagedData size : ${recordViewState.pagedData}")

    Content(
        paddingValues,
        roomId,
        recordViewState.isLoading,
        recordViewState.pagedData,
        detailHistoryRecordViewModel,
        detailHistoryViewModel
    )
}

@Composable
private fun Content(
    paddingValues: PaddingValues,
    roomId: Long,
    isLoading: Boolean = false,
    pagedData: Flow<PagingData<DetailHistoryRecordDto>>? = null,
    detailHistoryRecordViewModel: DetailHistoryRecordViewModel,
    detailHistoryViewModel: DetailHistoryViewModel
) {
    var pagingItems: LazyPagingItems<DetailHistoryRecordDto>? = null
    pagedData?.let {
        pagingItems = rememberFlowWithLifecycle(it).collectAsLazyPagingItems()
    }

    Box(
        modifier = Modifier.padding(paddingValues)
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        LazyColumn(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isLoading) {
                if (pagingItems != null && detailHistoryRecordViewModel.cnt == 0) {
                    detailHistoryRecordViewModel.cnt = pagingItems!!.itemCount
                }
                items(1) {
                    ResultLoadingIndicator()
                }
            } else if (pagedData != null && pagingItems != null) {
                if (detailHistoryRecordViewModel.cnt == 0 && pagingItems!!.itemCount != 0) {
                    detailHistoryRecordViewModel.cnt = pagingItems!!.itemCount
                }
                Log.d(TAG, "detailHistoryRecordViewModel.cnt: ${detailHistoryRecordViewModel.cnt}")
                items(count = detailHistoryRecordViewModel.cnt) { index ->
                    HistoryDetailCard(
                        roomId = roomId,
                        index = index,
                        dto = pagingItems!![index],
                        detailHistoryViewModel,
                        detailHistoryRecordViewModel
                    )
                }
            } else {
                items(1) {
                    Spacer(modifier = Modifier.height(40.dp))
                    Text(
                        modifier = Modifier.align(Alignment.TopCenter),
                        text = "현재 기록된 \n\n 상세 기록이 없어요ㅠㅠ",
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