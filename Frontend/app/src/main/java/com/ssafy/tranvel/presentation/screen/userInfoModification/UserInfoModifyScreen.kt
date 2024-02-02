package com.ssafy.tranvel.presentation.screen.userInfoModification

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
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
