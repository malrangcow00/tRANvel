package com.ssafy.tranvel.presentation.screen.history.component

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.ssafy.tranvel.BuildConfig
import com.ssafy.tranvel.R
import com.ssafy.tranvel.data.model.dto.HistoryDto

private const val TAG = "HistoryHeaderImages_싸피"

@Composable
fun HistoryHeaderImages(
    dto: HistoryDto?
) {
    LazyRow(
        modifier = Modifier.padding(top = 20.dp, start = 30.dp, end = 30.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy((-30).dp),
    ) {
        val itemCount = if (dto?.images == null) 0 else dto.images.size
        items(itemCount) { item ->
            Box{
                HistoryImageContent(
                    item, dto,
                    modifier = Modifier
                        .width(90.dp)
                        .height(120.dp)
                )
            }
        }
    }
}

@Composable
fun HistoryImageContent(
    itemIndex: Int,
    dto: HistoryDto?,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(25.dp)
    ) {
        TravelImage(
            imgUrl = (BuildConfig.S3_ADDRESS) + dto?.images?.get(itemIndex)!!
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun TravelImage(
    imgUrl: String,
    modifier: Modifier = Modifier
) {
    val bitmap: MutableState<Bitmap??> = mutableStateOf(null)

    val imageModifier = modifier
        .fillMaxSize()

    Glide.with(LocalContext.current)
        .asBitmap()
        .load(imgUrl)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(
                resource: Bitmap,
                transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
            ) {
                bitmap.value = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {

            }
        })
    bitmap.value?.asImageBitmap()?.let {
        Image(
            bitmap = it,
            contentScale = ContentScale.FillBounds,
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
