package com.ssafy.tranvel.presentation.screen.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore

class ConverterURIToBitmap {
    fun setImgUri(imgUri: Uri, context: Context): Bitmap {
        imgUri.let {
            val bitmap: Bitmap
            if (Build.VERSION.SDK_INT < 28) {
                bitmap = MediaStore.Images.Media.getBitmap(
                    context.contentResolver,
                    imgUri
                )
            } else {
                val source =
                    ImageDecoder.createSource(context.contentResolver, imgUri)
                bitmap = ImageDecoder.decodeBitmap(source)
            }
            return bitmap
        }
    }
}