package com.ssafy.tranvel.presentation.screen.history.component

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.tranvel.data.model.dto.HistoryDto

private const val TAG = "HistoryTitle_싸피"
@Composable
fun HistoryTitle(
    dto: HistoryDto?
) {
    Log.d(TAG, "HistoryTitle: ${dto?.roomName.orEmpty()}")
    Text(
        text = dto?.roomName.orEmpty(),
        textAlign = TextAlign.Start,
        fontSize = 20.sp,
    )
    Divider(
        color = Color.Black,
        modifier = Modifier
            .fillMaxWidth()
            .width(1.dp)
    )
}
