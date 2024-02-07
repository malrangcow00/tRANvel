package com.ssafy.tranvel.presentation.screen.history.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.tranvel.data.model.dto.HistoryDto
import com.ssafy.tranvel.presentation.screen.home.component.moneyFormatter

private const val TAG = "HistoryTitle_싸피"

@Composable
fun HistoryTitle(
    dto: HistoryDto?
) {
    val profit = if (dto?.balanceResult == null) 0 else moneyFormatter(dto.balanceResult)

    Column(
        modifier = Modifier.padding(40.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(

        ){
            Text(
                text = dto?.roomName.orEmpty(),
                fontSize = 20.sp,
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.4f))
            Text(
                text = profit.toString(),
                fontSize = 20.sp,
            )
        }
        Divider(
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth(1.3f)
                .width(1.dp)
        )


    }
}
