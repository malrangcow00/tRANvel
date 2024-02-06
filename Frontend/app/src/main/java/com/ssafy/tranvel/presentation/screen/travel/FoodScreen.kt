package com.ssafy.tranvel.presentation.screen.travel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ssafy.tranvel.presentation.screen.travel.component.BottomNav
import com.ssafy.tranvel.presentation.screen.travel.component.GameBody
import com.ssafy.tranvel.presentation.screen.travel.component.GameHeader
import com.ssafy.tranvel.presentation.ui.theme.PrimaryColor

@Composable
fun FoodScreen(
    navController: NavController
) {
    Scaffold(
        topBar = { GameHeader(drawerState = DrawerState(DrawerValue.Closed)) },
        content = { paddingValues ->
            Column {
                Text(text = "음식 작성 화면 입니다.", modifier = Modifier.padding(paddingValues))
            }
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .height(65.dp)
                    .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)),
                cutoutShape = CircleShape,
                backgroundColor = PrimaryColor,
                elevation = 22.dp
            ) {
                BottomNav(navController = navController)
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = {
                    //뽑기
                },
                backgroundColor = PrimaryColor
            ) {
                Icon(imageVector = Icons.Filled.Casino, contentDescription = "Add icon")
            }
        }
    )
}