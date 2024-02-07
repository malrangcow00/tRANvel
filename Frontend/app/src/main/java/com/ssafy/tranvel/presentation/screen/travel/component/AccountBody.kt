package com.ssafy.tranvel.presentation.screen.travel.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.AirplaneTicket
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material.icons.filled.Train
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.tranvel.presentation.screen.travel.TravelViewModel
import com.ssafy.tranvel.presentation.ui.theme.PrimaryColor
import com.ssafy.tranvel.presentation.ui.theme.PrimaryColor2
import com.ssafy.tranvel.presentation.ui.theme.TextColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountBody(
    paddingValues: PaddingValues,
    onBackPressed: () -> (Unit)
) {
    var selectedCategory: Int by remember { mutableIntStateOf(6) }
    val selectedCategoryColor: Color by remember {
        mutableStateOf(PrimaryColor)
    }
    var inputText: String by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(start = 20.dp, end = 20.dp, top = 10.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.padding(end = 15.dp),
                text = "멤버 선택",
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                color = Color.LightGray
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .clickable(
                        indication = rememberRipple(color = PrimaryColor2),
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = { }
                    )
                    .requiredHeight(TextFieldDefaults.MinHeight)
                    .padding(4.dp)
            ) {
                Checkbox(
                    checked = false,
                    onCheckedChange = null
                )

                Spacer(Modifier.size(6.dp))

                Text(
                    text = "모두 선택",
                    fontSize = 14.sp,
                    color = Color.LightGray
                )
            }
        }
        val imageList = List(20) {

        }
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
                            Icon(
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

        Text(
            modifier = Modifier.padding(end = 5.dp, top = 15.dp),
            text = "비용",
            fontSize = 14.sp,
            color = Color.LightGray
        )
        TextField(
            modifier = Modifier
                .padding(top = 10.dp)
                .align(Alignment.End),
            value = inputText,
            onValueChange = {
                inputText = it
            },
            placeholder = { Text(text = "사용한 금액을 작성하세요.", color = TextColor) },
            singleLine = true,
            textStyle = if (inputText!="") TextStyle(textAlign = TextAlign.End) else TextStyle(textAlign = TextAlign.Start),
            trailingIcon = { if (inputText != "") Text(text = "원", color = TextColor) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = PrimaryColor,
                focusedContainerColor = Color.White,
                focusedLabelColor = TextColor,
                unfocusedContainerColor = Color.White,
                unfocusedLabelColor = TextColor,
            )
        )
        Text(
            modifier = Modifier.padding(end = 5.dp, top = 15.dp),
            text = "카테고리",
            fontSize = 14.sp,
            color = Color.LightGray
        )

        val categoryList = listOf(
            Category(Icons.Default.Train, "교통"),
            Category(Icons.Default.RestaurantMenu, "음식"),
            Category(Icons.Default.Home, "숙소"),
            Category(Icons.Default.ShoppingBasket, "쇼핑"),
            Category(Icons.Default.AirplaneTicket, "티켓")
        )

        LazyRow(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            itemsIndexed(categoryList) { index, item ->
                Card(
                    modifier = Modifier
                        .padding(end = 3.dp),
                    onClick = { selectedCategory = index },
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(10)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Icon(
                            imageVector = item.img,
                            contentDescription = "프로필이미지",
                            modifier = Modifier
                                .size(50.dp),
                            tint = if (index == selectedCategory) selectedCategoryColor else Color.DarkGray
                        )
                        Text(
                            text = item.title,
                            modifier = Modifier
                                .padding(top = 5.dp),
                            color = if (index == selectedCategory) selectedCategoryColor else Color.DarkGray
                        )
                    }
                }
            }
        }
        Text(
            modifier = Modifier.padding(end = 5.dp, top = 15.dp),
            text = "상세 내역",
            fontSize = 14.sp,
            color = Color.LightGray
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            value = "",
            onValueChange = {},
            placeholder = { Text(text = "자세한 사항을 입력하세요.") },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "작성하기",
                    tint = TextColor
                )
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = PrimaryColor,
                focusedContainerColor = Color.White,
                focusedLabelColor = TextColor,
                unfocusedContainerColor = Color.White,
                unfocusedLabelColor = TextColor,
            )
        )
        Text(
            modifier = Modifier.padding(end = 5.dp, top = 15.dp),
            text = "사진 추가",
            fontSize = 14.sp,
            color = Color.LightGray
        )
        Card(
            modifier = Modifier
                .padding(end = 3.dp, top = 10.dp),
            onClick = {},
            shape = RoundedCornerShape(15)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    imageVector = Icons.Default.AddAPhoto,
                    contentDescription = "사진 추가",
                    modifier = Modifier
                        .size(100.dp)
                        .background(color = Color.LightGray)
                        .padding(5.dp),
                )
            }
        }
        Row{
            Button(onClick = { onBackPressed() }) {
                Text(text = "작성")
            }
            Button(onClick = { onBackPressed() }) {
                Text(text = "취소")
            }
        }
    }
}

data class Category(
    val img: ImageVector,
    val title: String
)