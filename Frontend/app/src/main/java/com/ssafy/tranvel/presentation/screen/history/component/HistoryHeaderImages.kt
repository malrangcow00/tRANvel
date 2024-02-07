package com.ssafy.tranvel.presentation.screen.history.component

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.Glide
import com.ssafy.tranvel.R
import com.ssafy.tranvel.data.model.dto.HistoryDto

@Composable
fun HistoryHeaderImages(
    dto: HistoryDto?
) {
    LazyRow(
        modifier = Modifier.padding(top = 20.dp, start = 30.dp, end = 30.dp),
        contentPadding = PaddingValues(horizontal = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val itemCount = if (dto?.images == null) 0 else dto.images.size
        items(itemCount) { item ->
            content(item)
        }
    }
}

@Composable
fun content(
    itemIndex: Int,

    ) {
    Card(
        shape = RoundedCornerShape(16.dp)
    ) {
//        TravelImage(
//            imgUrl = ,
//            modifier =
//        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun TravelImage(
    imgUrl: String,
    modifier: Modifier
) {
    val bitmap: MutableState<Bitmap??> = mutableStateOf(null)

    val imageModifier = modifier
        .size(50.dp)
        .clip(RoundedCornerShape(10.dp))

    Glide.with(LocalContext.current)
        .asBitmap()
        .load(imgUrl)

    bitmap.value?.asImageBitmap()?.let {
        Image(
            bitmap = it,
            contentScale = ContentScale.Fit,
            contentDescription = null,
            modifier = imageModifier
        )
    } ?: Image(
        painter = painterResource(id = R.drawable.emptyimage),
        contentScale = ContentScale.Fit,
        contentDescription = null,
        modifier = imageModifier
    )
}