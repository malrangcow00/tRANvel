package com.ssafy.tranvel.presentation.screen.announcement

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ssafy.tranvel.R
import com.ssafy.tranvel.data.model.dto.AnnouncementDto
import com.ssafy.tranvel.presentation.ui.theme.bmjua

@Composable
fun AnnouncementDetailScreen(
    dto: AnnouncementDto,
    onDismiss: () -> Unit,
    showDialog: Boolean
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = onDismiss,
        ) {
            DialogContent(dto, showDialog)
        }
    }
}

@Composable
fun DialogContent(
    dto: AnnouncementDto,
    showDialog: Boolean
) {
    Column(
        modifier = Modifier.background(color = Color.White)
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(10.dp)
                .size(40.dp)
                .clickable { !showDialog },
            painter = painterResource(id = R.drawable.exiticon),
            contentDescription = "notificationImage",
            contentScale = ContentScale.Inside,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                modifier = Modifier
                    .size(35.dp)
                    .weight(1f),
                painter = painterResource(id = R.drawable.notificationicon),
                contentDescription = "notificationImage",
                contentScale = ContentScale.Fit,
            )
            Text(
                text = dto?.title.orEmpty(),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth()
                    .wrapContentSize()
                    .padding(vertical = 8.dp)
                    .weight(1f),
                fontSize = 18.sp,
                fontFamily = bmjua,
                fontWeight = FontWeight.Bold,
                lineHeight = 17.sp
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(
            modifier = Modifier
                .height(12.dp)
                .fillMaxWidth()
        )
        Text(
            text = dto?.content.orEmpty(),
            fontFamily = bmjua,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.9f)
                .wrapContentSize()
                .padding(vertical = 20.dp)
        )
        Spacer(
            modifier = Modifier
                .height(30.dp)
                .fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun showDialog() {
    AnnouncementDetailScreen(
        dto = AnnouncementDto(
            1,
            "Hello",
            "asdflkqjwplvz;lxcmvl;zxkcjv;zlkxjcv;lkzas;dfjal;asdfalsdkf;asldkf;asldkfaskdfjalksdhjfaoisvlkzxncvaiosjdf;lkc;vaksdjfl;mzxviaowpierqlwkjfvla;kscmvlkajsdoifa;lcvzml;aksdjfioapqwek;lasjdfl;kj;lkksdjf;laksdjf;lkxcjv;lzkxjc;lvkzjx;lckvj;laksjedropiqwju;eorjlazxcv,n,m;laiksjdfoiasj;dlfkajmsdfxjc;lvkj;lakjsd;lfkjas;kldf",
            "20240131"
        ),
        onDismiss = { },
        showDialog = true
    )
}
