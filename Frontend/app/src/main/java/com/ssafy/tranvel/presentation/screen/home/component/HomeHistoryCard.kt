package com.ssafy.tranvel.presentation.screen.home.component

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.tranvel.data.model.dto.HistoryDto
import com.ssafy.tranvel.presentation.ui.theme.bmjua

private const val TAG = "HomeHistoryCard_싸피"

@Composable
fun HomeHistoryCard(
    dto: HistoryDto?,
    historyClicked: (HistoryDto?) -> (Unit)
) {
    val profit = if (dto?.balanceResult == null) 0 else moneyChanger(dto.balanceResult)
    Log.d(TAG, "싸피 지금 값은 profit : ${profit}")
//    val profit = if (dto?.balanceResult == null) 0 else dto?.balanceResult
    Log.d(TAG, "싸피 지금 값은 dto : ${dto}")
    val endDateString =
        if (dto?.endDate == null) "" else (" ~ " + (dto?.endDate.orEmpty()).substring(5, 10))
    Log.d(TAG, "싸피 지금 값음 endDate : ${endDateString}")

    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable(
                onClick = { historyClicked(dto) }
            ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(10.dp),
                text = (dto?.startDate.orEmpty()).substring(5, 10) + endDateString,
                style = TextStyle(
                    fontFamily = bmjua,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    letterSpacing = 0.5.sp,
                    color = Color.Black
                ),
            )
            Column(
                modifier = Modifier
                    .weight(0.5f)
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxSize(0.5f)
                        .aspectRatio(1f)
                        .background(color = Color.Yellow, shape = CircleShape)
                        .border(BorderStroke(1.dp, color = Color.Black), CircleShape)
                )
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
                Spacer(modifier = Modifier.height(30.dp))
            }
            LazyRow(
                verticalAlignment = Alignment.CenterVertically,
                contentPadding = PaddingValues(0.dp),
                horizontalArrangement = Arrangement.spacedBy(-3.dp)
            ) {
                imageCard()
            }
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp),
                text = dto?.roomName.orEmpty(),
                style = TextStyle(
                    fontFamily = bmjua,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    letterSpacing = 0.5.sp,
                    color = Color.Black
                ),
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp),
                text = profit.toString(),
                style = TextStyle(
                    fontFamily = bmjua,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    letterSpacing = 0.5.sp,
                    color = Color.Black
                ),
            )
        }
    }
}

fun imageCard(

) {

}

fun moneyChanger(money: Int): String {
    var output = ""

    var current = money

    if(current == 0){
        return "0원"
    }

    var divider = 1000000000000000
    while (current > 1000) {
        output += ((current / divider).toString() + ",")
        divider /= 1000
    }
    output += current.toString() + "원"

    return output
}

@Preview
@Composable
fun previewAnnouncementCard(
    dto: HistoryDto = HistoryDto(
        roomid = -1,
        roomName = "",
        startDate = "",
        endDate = null,
        balanceResult = 0,
        images = null,
    ),
) {

}
