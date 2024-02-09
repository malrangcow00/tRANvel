package com.ssafy.tranvel.presentation.screen.travel.component

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.jhdroid.view.RotateListener
import com.ssafy.tranvel.R
import com.ssafy.tranvel.databinding.FoodRouletteLayoutBinding

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun FoodRouletteBody(
) {
    val rouletteData =
        listOf("JhDroid", "Android", "Blog", "IT", "Developer", "Kotlin", "Java", "Happy")
    val rouletteListener = object : RotateListener {
        override fun onRotateStart() {
            // rotate animation start
        }

        override fun onRotateEnd(result: String) {
            // rotate animation end, get result here
            Log.d("TAG", "onRotateEnd: $result")
        }
    }
    var listener: ButtonClick? = null
    Box(modifier = Modifier.fillMaxSize()){
        AndroidViewBinding(
            FoodRouletteLayoutBinding::inflate,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter),
            onReset = {
                // Null out the OnClickListener to avoid leaking the `action` lambda.
            },
            update = {
                roulette.apply {
                    rouletteSize = 8
                    setRouletteDataList(rouletteData)
                }
                listener = object : ButtonClick {
                    override fun onClick() {
                        roulette.rotateRoulette(10000f, 4000L, rouletteListener)
                    }
                }
            }
        )
        Image(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(50.dp),
            painter = painterResource(id = R.drawable.down),
            contentDescription = "pointRoulette",
        )
        val imageList = List(20) {

        }
        Box(modifier = Modifier.align(Alignment.BottomCenter)){
            Column {
                Text(text = "입력한 멤버")
                LazyRow(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                ) {
                    items(imageList.size) { item ->
                        Card(
                            modifier = Modifier
                                .padding(end = 3.dp),
                            onClick = {},
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(10)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Box {
                                    Image(
                                        imageVector = Icons.Default.Person2,
                                        contentDescription = "프로필이미지",
                                        modifier = Modifier
                                            .size(50.dp)
                                            .align(Alignment.Center)
                                    )
                                    androidx.compose.material.Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "checking",
                                        modifier = Modifier
                                            .size(10.dp)
                                            .align(Alignment.TopEnd),
                                        tint = Color.Green
                                    )
                                }
                                Text(
                                    text = "$item",
                                    modifier = Modifier
                                        .padding(top = 5.dp)
                                )
                            }
                        }
                    }
                }
                Text(text = "입력하지 않은 멤버",modifier=Modifier.padding(top = 10.dp))
                LazyRow(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                ) {
                    items(imageList.size) { item ->
                        Card(
                            modifier = Modifier
                                .padding(end = 3.dp),
                            onClick = {},
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(10)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Box {
                                    Image(
                                        imageVector = Icons.Default.Person2,
                                        contentDescription = "프로필이미지",
                                        modifier = Modifier
                                            .size(50.dp)
                                            .align(Alignment.Center)
                                    )
                                    androidx.compose.material.Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "checking",
                                        modifier = Modifier
                                            .size(10.dp)
                                            .align(Alignment.TopEnd),
                                        tint = Color.Green
                                    )
                                }
                                Text(
                                    text = "$item",
                                    modifier = Modifier
                                        .padding(top = 5.dp)
                                )
                            }
                        }
                    }
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    onClick = {
                        listener?.onClick()
                    },
                ) {
                    Text(text = "뽑기")
                }
            }
        }
    }
}

interface ButtonClick {
    fun onClick()
}