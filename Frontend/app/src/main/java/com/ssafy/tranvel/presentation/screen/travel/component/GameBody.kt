package com.ssafy.tranvel.presentation.screen.travel.component

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import androidx.hilt.navigation.compose.hiltViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.ssafy.tranvel.presentation.screen.history.component.HistoryBody
import com.naver.maps.map.compose.rememberCameraPositionState
import com.ssafy.tranvel.BuildConfig
import com.ssafy.tranvel.R
import com.ssafy.tranvel.presentation.screen.travel.GameViewModel
import com.ssafy.tranvel.presentation.screen.travel.LoadingIndicator
import com.ssafy.tranvel.presentation.screen.travel.RoomInfo


@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun GameBody(
    innerPadding: PaddingValues,
    gameViewModel: GameViewModel,
    context: Context = LocalContext.current
) {
    val attractionDestination by gameViewModel.latLng.collectAsState()
    val attractionInfo by gameViewModel.attractionInfo.collectAsState()
    val attractionState by gameViewModel.attractionState.collectAsState()
    val enterPerson by gameViewModel.enterPerson.collectAsState()
    val enterPersonName by gameViewModel.enterPersonName.collectAsState()
    val dialogState by gameViewModel.drawDialogState.collectAsState()

    if (dialogState) {
        LoadingIndicator()
    }

    if (enterPerson) {
        Toast.makeText(context, enterPersonName, Toast.LENGTH_SHORT).show()
        gameViewModel.setEnterPerson()
    }

    if (gameViewModel.drawResultDialogState.value) {
        DrawAttractionResultDialog(
            attractionInfo.name,
            attractionInfo.description,
            attractionInfo.image
        ) {
            gameViewModel.drawResultDialogState.value = false
        }
    }

    val cameraPositionState = CameraPositionState(
        // 카메라 초기 위치를 설정합니다.
        position = CameraPosition(
            LatLng(
                attractionInfo.latitude.toDouble(),
                attractionInfo.longitude.toDouble()
            ), 11.0
        )
    )

    if (attractionState) {
        gameViewModel.setAttractionState()
        cameraPositionState.move(CameraUpdate.zoomIn())
    }
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        NaverMap(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f),
            onMapLongClick = { pointF, latLng ->
                gameViewModel.setAttractionLangLng(latLng)
            },
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(
                    position = LatLng(
                        attractionInfo.latitude.toDouble(),
                        attractionInfo.longitude.toDouble()
                    )
                ),
                captionText = attractionInfo.name
            )
            Marker(
                state = MarkerState(position = attractionDestination)
            )
        }
        Log.d("TAG", "GameBody: ${RoomInfo.roomCode},  ${RoomInfo.roomPassword}")

        HistoryBody(
            paddingValues = innerPadding,
            roomId = RoomInfo.roomID,
            detailHistoryViewModel = hiltViewModel(),
            detailHistoryRecordViewModel = hiltViewModel()
        )

    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DrawAttractionResultDialog(
    attractionName: String,
    attractionContent: String,
    attractionImage: String?,
    onStateChange: () -> (Unit)
) {
    Dialog(
        onDismissRequest = { onStateChange() }
    ) {
        Column(
            modifier = Modifier
                .background(color = Color.White)
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = attractionName,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp)
                    .align(Alignment.CenterHorizontally)
            )
            GlideImage(
                model = "${BuildConfig.S3_BASE_URL}${attractionImage}",
                contentDescription = "여행지 사진",
                failure = placeholder(R.drawable.emptyimage),
                modifier = Modifier.size(200.dp)
            )
            Text(
                text = attractionContent,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}