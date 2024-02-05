package com.ssafy.tranvel.presentation.screen.travel

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ssafy.tranvel.presentation.screen.travel.component.GameBody
import com.ssafy.tranvel.presentation.screen.travel.component.GameHeader

@Composable
fun GameScreen() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight()
            ) {
            }
        },
        gesturesEnabled = true
    ) {
        Scaffold(
            topBar = { GameHeader(drawerState = drawerState) },
            content = { paddingValues ->
                GameBody(paddingValues)
            }
        )
    }
}