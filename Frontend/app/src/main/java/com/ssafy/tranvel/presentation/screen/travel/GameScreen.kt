package com.ssafy.tranvel.presentation.screen.travel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material.Scaffold
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ssafy.tranvel.R
import com.ssafy.tranvel.presentation.screen.home.navigation.homeRoute
import com.ssafy.tranvel.presentation.screen.travel.component.BottomNav
import com.ssafy.tranvel.presentation.screen.travel.component.GameBody
import com.ssafy.tranvel.presentation.screen.travel.component.GameHeader
import com.ssafy.tranvel.presentation.screen.travel.component.Screen
import com.ssafy.tranvel.presentation.ui.theme.PrimaryColor
import com.ssafy.tranvel.presentation.ui.theme.PrimaryColor2
import com.ssafy.tranvel.presentation.ui.theme.transparent

@Composable
fun GameScreen(
    navController: NavController,
    gameViewModel: GameViewModel
) {
    // 방장이 아니라면 음식 화면으로 이동
    val navigateFoodScreen by gameViewModel.navigateFoodScreen.collectAsState()
    val drawPerson by gameViewModel.drawPerson.collectAsState()
    val drawState by gameViewModel.drawState.collectAsState()
    val uiState by gameViewModel.gameState.collectAsState()

    if (uiState) {
        gameViewModel.setGameState()
        navController.navigate(homeRoute){
            this.popUpTo("game")
        }
    }

    if (navigateFoodScreen) {
        EnterFoodGameDialog {
            gameViewModel.setNavigateFoodState()
            Screen.Restaurant.route?.let { navController.navigate(it) }
        }
    }
    Scaffold(
        topBar = { GameHeader("즐거운 여행 중", true, gameViewModel) },
        content = { paddingValues ->
            Column {
                GameBody(paddingValues, gameViewModel)
            }
        },
        bottomBar = {
            if (RoomInfo.authority) {
                BottomAppBar(
                    modifier = Modifier
                        .height(50.dp)
                        .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)),
                    cutoutShape = CircleShape,
                    backgroundColor = PrimaryColor,
                    elevation = 22.dp
                ) {
                    BottomNav(navController = navController)
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = {
                    if (RoomInfo.authority && drawState) {
                        gameViewModel.sendAttractionPersonMessage("ENTER", "")
                    }
                    if (drawPerson && !drawState) {
                        gameViewModel.drawAttraction()
                    }
                },
                backgroundColor = PrimaryColor2
            ) {
                Icon(imageVector = Icons.Filled.Casino, contentDescription = "Add icon")
            }
        }
    )
}

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.attraction_loading_dialog))
    val progress by animateLottieCompositionAsState(
        composition, true, iterations = LottieConstants.IterateForever, restartOnPlay = false
    )
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier.fillMaxSize().background(color = transparent)
    )
}

@Composable
fun EnterFoodGameDialog(onChangeState: () -> (Unit)) {

    AlertDialog(
        onDismissRequest = { },
        title = {
            Text(
                text = "메뉴 선정",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        text = {
            Text(
                text = "음식 메뉴를 뽑는 게임이 시작하여 화면이 이동합니다..",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onChangeState()
                }
            ) {
                Text(text = "확인", color = Color.Black)
            }
        }
    )
}