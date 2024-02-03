package com.ssafy.tranvel.presentation.screen.announcement.component

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ssafy.tranvel.R
import com.ssafy.tranvel.data.model.dto.AnnouncementDto
import com.ssafy.tranvel.presentation.screen.announcement.AnnouncementViewModel
import com.ssafy.tranvel.presentation.ui.theme.bmjua
import kotlinx.coroutines.flow.Flow

private const val TAG = "AnnouncementBody"

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AnnouncementBody(
    paddingValues: PaddingValues,
    viewModel: AnnouncementViewModel,
    navigateToAnnouncementDetail: (AnnouncementDto?) -> Unit
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
                clickDetail = {
                    navigateToAnnouncementDetail.invoke(it)
                }
            )
        },
    )
}

@Composable
private fun Content(
    isLoading: Boolean = false,
    pagedData: Flow<PagingData<AnnouncementDto>>? = null,
    clickDetail: (AnnouncementDto?) -> Unit
) {
    var pagingItems: LazyPagingItems<AnnouncementDto>? = null
    pagedData?.let {
        pagingItems = rememberFlowWithLifecycle(it).collectAsLazyPagingItems()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp),
    ) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
//            if (isLoading) {
//                items(10) {
//                    AnnouncementShimmer()
//                }
//            } else if (pagedData != null && pagingItems != null) {
            if (!isLoading && pagedData != null && pagingItems != null) {
                Log.d(
                    TAG,
                    "pagedData : ${pagedData}  / pagingItems : ${pagingItems} / pagingItems.itemCount : ${pagingItems!!.itemCount}"
                )
                items(count = pagingItems!!.itemCount, key = null) { index ->
                    AnnouncementCard(
                        showDetailAnnouncementClick = {
                            clickDetail.invoke(pagingItems!!.get(index))
                        },
                        dto = pagingItems!!.get(index),
                    )
                }
            }
        }
        if (isLoading) {
            AnnouncementLoadingIndicator()
        }
        if (!isLoading && (pagedData != null || pagingItems != null)) {
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

@Composable
fun AnnouncementLoadingIndicator(
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loadinganimation))
    val progress by animateLottieCompositionAsState(
        composition, true, iterations = LottieConstants.IterateForever, restartOnPlay = false
    )
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier.fillMaxSize()
    )
}

@Composable
fun EmptyIndicator(
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.emptyanimation))
    val progress by animateLottieCompositionAsState(
        composition, true, iterations = LottieConstants.IterateForever, restartOnPlay = false
    )
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier.fillMaxSize()
    )
}

@Composable
fun <T> rememberFlowWithLifecycle(
    flow: Flow<T>,
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): Flow<T> = remember(flow, lifecycle) {
    flow.flowWithLifecycle(
        lifecycle = lifecycle,
        minActiveState = minActiveState
    )
}
