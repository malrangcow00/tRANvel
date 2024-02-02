package com.ssafy.tranvel.presentation.screen.userInfoModification

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.ssafy.tranvel.presentation.screen.userInfoModification.component.UserInfoModifyMainBlock
import com.ssafy.tranvel.presentation.screen.userInfoModification.component.UserInfoModifyProfileImage
import com.ssafy.tranvel.presentation.screen.userInfoModification.component.UserInfoModifyTopBar


@Composable
fun UserInfoModifyScreen(
    viewModel : UserInfoModifyViewModel,
) {
    Column {
        UserInfoModifyTopBar()
        UserInfoModifyProfileImage(LocalContext.current,viewModel)
        UserInfoModifyMainBlock()
    }
}
