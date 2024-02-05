package com.ssafy.tranvel.presentation.screen.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ssafy.tranvel.R
import com.ssafy.tranvel.data.model.dto.AllHistoryDto
import com.ssafy.tranvel.presentation.screen.announcement.component.rememberFlowWithLifecycle
import com.ssafy.tranvel.presentation.screen.history.HistoryViewModel
import kotlinx.coroutines.flow.Flow

@Composable
fun HomeHistoryBody(
    historyViewModel: HistoryViewModel,
    navigateToHistory: (AllHistoryDto?) -> Unit
) {
    val viewState = historyViewModel.uiState.collectAsState().value

    Column(
        modifier = Modifier.padding(20.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
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
        Spacer(modifier = Modifier.height(40.dp))
    }

    Content(
        viewState.isLoading,
        viewState.pagedData,
        { navigateToHistory.invoke(it) }
    )
}

@Composable
private fun Content(
    isLoading: Boolean = false,
    pagedData: Flow<PagingData<AllHistoryDto>>? = null,
    clickHistory: (AllHistoryDto?) -> Unit
) {
    var pagingItems : LazyPagingItems<AllHistoryDto>? = null
    pagedData?.let {
        pagingItems = rememberFlowWithLifecycle(it).collectAsLazyPagingItems()
    }


}
