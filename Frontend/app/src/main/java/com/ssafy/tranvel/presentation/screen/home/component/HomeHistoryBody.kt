package com.ssafy.tranvel.presentation.screen.home.component

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ssafy.tranvel.R
import com.ssafy.tranvel.data.model.dto.HistoryDto
import com.ssafy.tranvel.presentation.screen.components.EmptyIndicator
import com.ssafy.tranvel.presentation.screen.components.LoadingIndicator
import com.ssafy.tranvel.presentation.screen.history.DetailHistoryRecordViewModel
import com.ssafy.tranvel.presentation.screen.history.DetailHistoryViewModel
import com.ssafy.tranvel.presentation.screen.history.HistoryViewModel
import com.ssafy.tranvel.presentation.ui.theme.bmjua
import kotlinx.coroutines.flow.Flow

private const val TAG = "HomdHistoryBody_싸피"

@Composable
fun HomeHistoryBody(
    historyViewModel: HistoryViewModel,
    navController: NavController
) {
    val viewState = historyViewModel.uiState.collectAsState().value

    Column(
        modifier = Modifier.padding(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "히스토리",
                textAlign = TextAlign.Start,
                fontSize = 40.sp,
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Image(
                modifier = Modifier.size(60.dp),
                painter = painterResource(id = R.drawable.historyicon),
                contentDescription = "히스토리 아이콘",
                contentScale = ContentScale.Fit
            )
        }
        Divider(
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .width(1.dp)
        )
    }
    Content(
        historyViewModel,
        viewState.isLoading,
        viewState.pagedData,
        navController
    )
}

@Composable
private fun Content(
    historyViewModel: HistoryViewModel,
    isLoading: Boolean = false,
    pagedData: Flow<PagingData<HistoryDto>>? = null,
    navController: NavController
) {
    var pagingItems: LazyPagingItems<HistoryDto>? = null
    pagedData?.let {
        pagingItems = rememberFlowWithLifecycle(it).collectAsLazyPagingItems()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (isLoading || (pagingItems != null && pagingItems!!.itemCount == 0)) {
                if (pagingItems != null && historyViewModel.cnt == 0) {
                    historyViewModel.cnt = pagingItems!!.itemCount
                }
                items(1) {
                    LoadingIndicator()
                }
            } else if (pagedData != null && pagingItems != null) {
                if (historyViewModel.cnt == 0 && pagingItems!!.itemCount != 0) {
                    historyViewModel.cnt = pagingItems!!.itemCount
                }
                Log.d(TAG, "Cnt: ${historyViewModel.cnt}")
                items(count = historyViewModel.cnt) { index ->
                    HomeHistoryCard(
                        index,
                        historyViewModel,
                        pagingItems!![index],
                        navController
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Canvas(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxSize()
                    ) {
                        val canvasWidth = size.width
                        drawLine(
                            color = Color.LightGray,
                            start = Offset(0.dp.toPx(), 0.dp.toPx()),
                            end = Offset(canvasWidth, 0.dp.toPx()),
                            strokeWidth = 2.dp.toPx(),
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 20f), 10f)
                        )
                    }
                }
            } else {
                items(1) {
                    Text(
                        modifier = Modifier.align(Alignment.TopCenter),
                        text = "현재 기록된 \n 여행이 없어요ㅠㅠ",
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
