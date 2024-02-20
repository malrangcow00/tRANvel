# tRanvel
계획없이 여행을 떠나고 싶을 때 사용해보세요.
## 개발 환경
![Static Badge](https://img.shields.io/badge/test_device-Galaxy_s10-34A853)
![Static Badge](https://img.shields.io/badge/Android-v12-34A853?logo=Android)
![Static Badge](https://img.shields.io/badge/Android_Studio-Hedgehog-3DDC84?logo=AndroidStudio)
![Static Badge](https://img.shields.io/badge/kotlin-v1.9.0-7F52FF?logo=kotlin)
![Static Badge](https://img.shields.io/badge/ksp-v1.9.0~1.0.12-7F52FF)
![Static Badge](https://img.shields.io/badge/agp-v8.2.1-7F52FF?logo=Gradle)
![Static Badge](https://img.shields.io/badge/jetpack_compose-v2.5.3-4285F4?logo=jetpackcompose)
![Static Badge](https://img.shields.io/badge/minsdk-21-4285F4)
![Static Badge](https://img.shields.io/badge/targetsdk-34-4285F4)

## Architecture
이 앱은 코틀린 언어를 이용하여 MVVM 아키텍처 패턴을 준수하며 개발하였습니다.  
## 기술 스택
- compose  
    - ui
    - Material3
    - Material
    - Paging
    - Lottie-1.4.1
    - Glide 1.0.0-beta01
    - Lifecycle-ViewModel
    - NaverMap 1.4.1
    - Navigation-Compose 2.7.6
    - viewbinding - xml기반의 룰렛을 사용하기 위한 라이브러리
- jetpack
    - SplashScreen - 스플래쉬 화면을 만들기 위한 라이브러리 1.0.1
    - Hilt - 의존성 주입을 어노테이션으로 편리하게 도와주는 라이브러리 2.48
- other
    - Okhttp3 - HTTP 로그 확인 및 헤더에 jwt를 담기 위한 라이브러리 4.10.0
    - Retrofit2 - HTTP통신을 위한 라이브러리 2.9.0
    - Flow - 데이터 흐름을 위한 라이브러리
    - Gson - json을 위한 라이브러리 2.9.0
    - STOMP - 실시간 통신을 위한 라이브러리 1.6.6
    - RXJAVA2 - STOMP 라이브러리 사용을 위한 비동기 통신 라이브러리
    - Android-Roulette-Wheel-View - 음식 선정 게임을 위해 사용하는 xml 기반 라이브러리

## local.properties
API_KEY= 서버 기본 주소  
NAVER_API_KEY= 네이버 지도 SDK 키 값  
WEBSOCKET_URL=웹소켓 서버 주소  
S3_ADDRESS=S3 기본 주소  
S3_BASE_URL=

