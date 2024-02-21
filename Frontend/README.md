# tRanvel
계획없이 여행을 떠나고 싶을 때 사용해보세요.
## 실행 화면
메인화면

![image](/uploads/95461d4d25ca9c7b2df13e242ca4e139/image.png)

여행지 뽑기

![image__4_](/uploads/9828c9bbe327fe54382fbb4d92479c5b/image__4_.png)

음식 입력 화면

![image__3_](/uploads/30561633ecbe9ab8f8a5a258653f7d02/image__3_.png)

음식 뽑기 화면

![image__2_](/uploads/168ac2010b7a4255208defe909736344/image__2_.png)

비용 작성 화면

![image__1_](/uploads/25fc114c3748bce8f1213c7fb6dfa2cf/image__1_.png)
## 개발 환경
![Static Badge](https://img.shields.io/badge/test_device-Galaxy_s10-34A853)
![Static Badge](https://img.shields.io/badge/Android-v12-34A853?logo=Android)
![Static Badge](https://img.shields.io/badge/Android_Studio-Hedgehog-3DDC84?logo=AndroidStudio)
![Static Badge](https://img.shields.io/badge/kotlin-v1.9.0-7F52FF?logo=kotlin)
![Static Badge](https://img.shields.io/badge/ksp-v1.9.0~1.0.12-7F52FF)
![Static Badge](https://img.shields.io/badge/agp-v8.2.1-02303A?logo=Gradle)
![Static Badge](https://img.shields.io/badge/jetpack_compose-v2.5.3-4285F4?logo=jetpackcompose)
![Static Badge](https://img.shields.io/badge/minsdk-21-4285F4)
![Static Badge](https://img.shields.io/badge/targetsdk-34-4285F4)

## Architecture
이 앱은 코틀린 언어를 이용하여 MVVM 아키텍처 패턴을 준수하며 개발하였습니다.  
## 기술 스택
- compose  
    - [ui](https://developer.android.com/jetpack/androidx/releases/compose-ui?hl=ko)
    - [Material3](https://developer.android.com/jetpack/androidx/releases/compose-material3?hl=ko) - 앱의 디자인을 위해사용
    - [Material](https://developer.android.com/jetpack/androidx/releases/compose-material?hl=ko) - 앱의 디자인을 위해사용
    - [Paging](https://developer.android.com/jetpack/androidx/releases/paging?hl=ko) - 사용자가 로드된 데이터의 끝까지 스크롤할 때 새로운 데이터를 요청하여 효율적인 관리하기 위해 사용 - v3.2.1
    - [Lottie](https://airbnb.io/lottie/#/android-compose) 로딩화면을 재미있게 보여주기위해 사용 - v1.4.1
    - [Glide](https://bumptech.github.io/glide/int/compose.html) 여행지에 관한 사진등 다양한 이미지를 보여주기위해 사용 - v1.0.0-beta01
    - [Lifecycle-ViewModel](https://developer.android.com/jetpack/androidx/releases/lifecycle?hl=ko)
    - [NaverMap](https://github.com/fornewid/naver-map-compose) - 뽑은 여행지의 위치를 보여주고 사용자가 원하는 지역을 선택하기위해 사용 - v1.4.1
    - [Navigation-Compose](https://developer.android.com/jetpack/compose/navigation?hl=ko) - compose에서 화면을 이동하기 위해 사용 - v2.7.6
    - [viewbinding](https://developer.android.com/jetpack/compose/migrate/interoperability-apis/views-in-compose?hl=ko) - xml을 이용하여 만든 룰렛을 compose에서 화면을 보여주기 위해서 사용
- jetpack
    - [SplashScreen](https://developer.android.com/develop/ui/views/launch/splash-screen?hl=ko) - 스플래쉬 화면을 만들기 위해 사용 - v1.0.1
    - [Hilt](https://developer.android.com/training/dependency-injection/hilt-android?hl=ko) - 의존성 주입을 어노테이션으로 편리하게 도와주는 도구 - v2.48
- other
    - [Okhttp3](https://github.com/square/okhttp/tree/master) - HTTP 로그 확인 및 헤더에 jwt를 담기 위해 사용 - v4.10.0
    - [Retrofit2](https://square.github.io/retrofit/) - HTTP통신을 편리하게 개발하기위해 사용 - v2.9.0
    - [Flow](https://developer.android.com/kotlin/flow?hl=ko) - 데이터 흐름을 위한 api
    - Gson - retrofit을 사용하여 통신을 할 때 json을 이용하기 위해 사용 - v2.9.0
    - [STOMP](https://github.com/NaikSoftware/StompProtocolAndroid) - 실시간 통신을 위해 사용 - v1.6.6
    - [Android-Roulette-Wheel-View](https://github.com/JhDroid/android-roulette-wheel-view) - 음식 선정 게임을 위해 사용하는 xml 기반 라이브러리 - ㅍ1.0.3

## local.properties
API_KEY= 서버 기본 주소  
NAVER_API_KEY= 네이버 지도 SDK 키 값  
WEBSOCKET_URL=웹소켓 서버 주소  
S3_ADDRESS=S3 기본 주소  
S3_BASE_URL=

