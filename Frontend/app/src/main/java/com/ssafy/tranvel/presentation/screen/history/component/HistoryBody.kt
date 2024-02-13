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
import com.ssafy.tranvel.data.model.dto.DetailHistoryDto
import com.ssafy.tranvel.data.model.dto.DetailHistoryRecordDto
import com.ssafy.tranvel.data.model.dto.HistoryDto
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
    dto: HistoryDto?,
    detailHistoryViewModel: DetailHistoryViewModel,
    detailHistoryRecordViewModel: DetailHistoryRecordViewModel
) {
    LaunchedEffect(dto) {
        detailHistoryViewModel.cnt = 0
        if (dto != null) detailHistoryViewModel.getDetailHistory(dto.roomid!!)

        detailHistoryRecordViewModel.cnt = 0
        if (dto != null) detailHistoryRecordViewModel.getDetailHistoryRecord(dto.roomid!!)
        Log.d(TAG, "HistoryBody: 여기서 detailHistoryRecordViewModel.cnt : ${detailHistoryRecordViewModel.cnt}")
    }

    val viewState = detailHistoryViewModel.uiState.collectAsState().value
    val recordViewState = detailHistoryRecordViewModel.uiState.collectAsState().value

    Log.d(TAG, "HistoryBody: recordViewState.pagedData size : ${recordViewState.pagedData}")

    Content(
        dto,
        paddingValues,
        viewState.isLoading,
        viewState.pagedData,
        detailHistoryViewModel,
        detailHistoryRecordViewModel,
        recordViewState.isLoading,
        recordViewState.pagedData
    )
}

@Composable
private fun Content(
    dto: HistoryDto?,
    paddingValues: PaddingValues,
    isLoading: Boolean = false,
    pagedData: Flow<PagingData<DetailHistoryDto>>? = null,
    detailHistoryViewModel: DetailHistoryViewModel,
    detailHistoryRecordViewModel: DetailHistoryRecordViewModel,
    recordIsLoading: Boolean = false,
    recordPagedData: Flow<PagingData<DetailHistoryRecordDto>>? = null,
) {
    var pagingItems: LazyPagingItems<DetailHistoryDto>? = null
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
                if (pagingItems != null && detailHistoryViewModel.cnt == 0) {
                    detailHistoryViewModel.cnt = pagingItems!!.itemCount
                }
                items(1) {
                    ResultLoadingIndicator()
                }
            } else if (pagedData != null && pagingItems != null) {
                if (detailHistoryViewModel.cnt == 0 && pagingItems!!.itemCount != 0) {
                    detailHistoryViewModel.cnt = pagingItems!!.itemCount
                }
                Log.d(TAG, "Cnt: ${detailHistoryViewModel.cnt}")
                items(count = detailHistoryViewModel.cnt) { index ->
                    HistoryDetailCard(
                        roomId = dto?.roomid!!,
                        index = index,
                        dto = pagingItems!![index],
                        detailHistoryViewModel = detailHistoryViewModel,
                        detailHistoryRecordViewModel = detailHistoryRecordViewModel,
                        isLoading = recordIsLoading,
                        pagedData = recordPagedData,
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
