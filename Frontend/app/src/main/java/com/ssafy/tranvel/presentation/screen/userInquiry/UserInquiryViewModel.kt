package com.ssafy.tranvel.presentation.screen.userInquiry

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserInquiryViewModel @Inject constructor(
    private val getUserInquiryUseCase : GetUserInquiryUseCase
) : ViewModel() {

}
