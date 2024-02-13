package com.ssafy.tranvel.presentation.screen.travel.component

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.ssafy.tranvel.presentation.screen.travel.GameViewModel
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

    if (enterPerson){
        Toast.makeText(context, enterPersonName,Toast.LENGTH_SHORT).show()
        gameViewModel.setEnterPerson()
    }

    val cameraPositionState  = CameraPositionState(
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


    }
}
