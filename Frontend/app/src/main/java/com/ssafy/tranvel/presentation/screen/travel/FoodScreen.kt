package com.ssafy.tranvel.presentation.screen.travel

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ssafy.tranvel.presentation.screen.travel.component.BottomNav
import com.ssafy.tranvel.presentation.screen.travel.component.FoodBody
import com.ssafy.tranvel.presentation.screen.travel.component.FoodRouletteBody
import com.ssafy.tranvel.presentation.screen.travel.component.GameBody
import com.ssafy.tranvel.presentation.screen.travel.component.GameHeader
import com.ssafy.tranvel.presentation.ui.theme.PrimaryColor

@Composable
fun FoodScreen(
    gameViewModel: GameViewModel,
    onBackPressed: () -> (Unit),
) {
    var visibility: Boolean by remember { mutableStateOf(true) }
    if (RoomInfo.authority) {
        gameViewModel.sendFoodGameMessage("ENTER","")
    } else {
        gameViewModel.setNavigateFoodState()
    }
    Scaffold(
        topBar = { GameHeader("오늘의 메뉴는?", false) },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(start = 20.dp, end = 20.dp)
                    .fillMaxSize()
            ) {
                if (visibility) {
                    FoodBody(
                        gameViewModel,
                        onBackPressed
                    ) {
                        visibility = !visibility
                    }
                } else {
                    FoodRouletteBody(gameViewModel){
                        onBackPressed()
                    }
                }
            }
        }
    )
}