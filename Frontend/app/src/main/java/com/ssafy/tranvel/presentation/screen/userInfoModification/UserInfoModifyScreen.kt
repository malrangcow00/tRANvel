package com.ssafy.tranvel.presentation.screen.userInfoModification

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.tranvel.presentation.screen.mainMenuDrawer.MainMenuDrawerScreen
import com.ssafy.tranvel.presentation.screen.userInfoModification.component.UserInfoModifyMainBlock
import com.ssafy.tranvel.presentation.screen.userInfoModification.component.UserInfoModifyProfileImage
import com.ssafy.tranvel.presentation.screen.userInfoModification.component.UserInfoModifyTopBar


@Preview
@Composable
fun UserInfoModifyScreen() {
    Column {
        UserInfoModifyTopBar()
        UserInfoModifyProfileImage()
        UserInfoModifyMainBlock()
    }
}
