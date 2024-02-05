package com.ssafy.tranvel.presentation.screen.home.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.tranvel.data.model.dto.AnnouncementDto
import com.ssafy.tranvel.data.model.dto.HistoryDto
import com.ssafy.tranvel.presentation.ui.theme.PrimaryColor

@Composable
fun HomeHistoryCard(
    dto: HistoryDto?,
    historyClicked: () -> (Unit)
) {
    Card(
        modifier = Modifier
            .background(PrimaryColor)
            .fillMaxWidth()
            .height(100.dp)
            .clickable(
                onClick = { historyClicked() }
            ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = dto?.period.orEmpty(),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 24.sp,
                    letterSpacing = 0.5.sp
                ),
                color = Color(0xFF018786)
            )
            Column {
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxSize()
                        .aspectRatio(1f)
                        .background(color = Color.Yellow, shape = CircleShape)
                )
                Canvas(
                    modifier = Modifier.size(100.dp),
                    onDraw = {
                        drawLine(
                            color = Color.Black,
                            start = Offset(0.dp.toPx(), 0.dp.toPx()),
                            end = Offset(100.dp.toPx(), 100.dp.toPx()),
                            strokeWidth = 10.dp.toPx(),
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 40f), 10f)
                        )
                    }
                )
            }
            Row{
                LazyColumn(
                    contentPadding = PaddingValues(0.dp)
                ){

                }
            }
        }
    }
}

@Preview
@Composable
fun previewAnnouncementCard(
    dto: HistoryDto = HistoryDto(
        1,
        "1월 27일 ~ 1월 30일",
        null,
        "test입니다",
        100000
    )
) {

}