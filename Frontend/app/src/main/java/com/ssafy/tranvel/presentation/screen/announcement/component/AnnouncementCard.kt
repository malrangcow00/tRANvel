package com.ssafy.tranvel.presentation.screen.announcement.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.tranvel.data.model.dto.AnnouncementDto
import com.ssafy.tranvel.presentation.screen.announcement.AnnouncementDetailScreen

@Composable
fun AnnouncementCard(
    dto: AnnouncementDto?,
    showDetailAnnouncementClick: () -> (Unit)
) {
    val showDialog = remember { mutableStateOf(false) }
    if(showDialog.value){
        AnnouncementDetailScreen(dto = dto!!, onDismiss = {showDialog.value = false}, showDialog = showDialog.value)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable(
                onClick = { showDialog.value = true }
            ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = dto?.title.orEmpty(),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 24.sp,
                    letterSpacing = 0.5.sp
                ),
                color = Color(0xFF018786)
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .align(Alignment.End),
                text = dto?.time.orEmpty(),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    letterSpacing = 0.25.sp
                ),
                color = Color(0xFF018786),
            )
        }
    }
}

@Composable
fun showAnnouncement(
    dto: AnnouncementDto,
    showDialog: Boolean,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AnnouncementDetailScreen(dto, onDismiss, showDialog)
    }
}

@Preview
@Composable
fun previewAnnouncementCard(
    dto: AnnouncementDto = AnnouncementDto(
        "Hello",
        "asdflkqjwplvz;lxcmvl;zxkcjv;zlkxjcv;lkzas;dfjal;asdfalsdkf;asldkf;asldkfaskdfjalksdhjfaoisvlkzxncvaiosjdf;lkc;vaksdjfl;mzxviaowpierqlwkjfvla;kscmvlkajsdoifa;lcvzml;aksdjfioapqwek;lasjdfl;kj;lkksdjf;laksdjf;lkxcjv;lzkxjc;lvkzjx;lckvj;laksjedropiqwju;eorjlazxcv,n,m;laiksjdfoiasj;dlfkajmsdfxjc;lvkj;lakjsd;lfkjas;kldf",
        "20240131"
    )
){

}
