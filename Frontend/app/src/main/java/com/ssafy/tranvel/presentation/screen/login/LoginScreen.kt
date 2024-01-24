package com.ssafy.tranvel.presentation.screen.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen() {
    Column(
        modifier = Modifier.padding(50.dp)
    ) {
        LoginMainBlock()
        SocialLoginBlock()
    }
}