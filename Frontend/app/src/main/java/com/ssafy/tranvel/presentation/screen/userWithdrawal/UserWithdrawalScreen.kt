package com.ssafy.tranvel.presentation.screen.userWithdrawal

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ssafy.tranvel.presentation.screen.userWithdrawal.component.UserWithdrawalMainBlock
import com.ssafy.tranvel.presentation.screen.userWithdrawal.component.UserWithdrawalTopBar

@Preview
@Composable
fun UserWithdrawalScreen(){
    Column {
        UserWithdrawalTopBar()
        UserWithdrawalMainBlock()
    }
}
