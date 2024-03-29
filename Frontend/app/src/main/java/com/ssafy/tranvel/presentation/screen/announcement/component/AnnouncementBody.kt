package com.ssafy.tranvel.presentation.screen.announcement.component

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
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
import com.ssafy.tranvel.data.model.dto.AnnouncementDto
import com.ssafy.tranvel.presentation.screen.announcement.AnnouncementViewModel
import com.ssafy.tranvel.presentation.screen.components.EmptyIndicator
import com.ssafy.tranvel.presentation.screen.components.LoadingIndicator
import com.ssafy.tranvel.presentation.ui.theme.bmjua
import kotlinx.coroutines.flow.Flow

private const val TAG = "AnnouncementBody_싸피"

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AnnouncementBody(
    paddingValues: PaddingValues,
    viewModel: AnnouncementViewModel,
) {
    val viewState = viewModel.uiState.collectAsState().value

    Scaffold(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        content = {
            Content(
                isLoading = viewState.isLoading,
                pagedData = viewState.pagedData,
            )
        },
    )
}

@Composable
private fun Content(
    isLoading: Boolean = false,
    pagedData: Flow<PagingData<AnnouncementDto>>? = null,
) {
    var pagingItems: LazyPagingItems<AnnouncementDto>? = null
    pagedData?.let {
        pagingItems = rememberFlowWithLifecycle(it).collectAsLazyPagingItems()
    }

    var cnt = 0

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp),
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            if (isLoading) {
                if (pagingItems != null && cnt == 0) {
                    cnt = pagingItems!!.itemCount
                }
                items(1) {
                    LoadingIndicator()

                }
            } else if (pagedData != null && pagingItems != null) {
                if (cnt == 0 && pagingItems!!.itemCount != 0) {
                    cnt = pagingItems!!.itemCount
                }
                items(count = cnt) { index ->
                    AnnouncementCard(
                        dto = pagingItems!![index]
                    )
                }

            } else {
                items(1) {
                    Text(
                        modifier = Modifier.align(Alignment.TopCenter),
                        text = "현재 등록된 \n 공지사항이 없어요ㅠㅠ",
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
