import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
}

android {
    namespace = "com.ssafy.tranvel"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ssafy.tranvel"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField("String", "API_KEY",getApiKey("API_KEY"))
        buildConfigField("String", "WEBSOCKET_URL",getApiKey("WEBSOCKET_URL"))
        resValue("string","NAVER_API_KEY",getApiKey("NAVER_API_KEY"))
        buildConfigField("String", "S3_ADDRESS",getApiKey("S3_ADDRESS"))
        buildConfigField("String", "S3_BASE_URL", getApiKey("S3_BASE_URL"))
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    viewBinding {
        enable = true
    }
}

fun getApiKey(propertyKey: String): String = gradleLocalProperties(rootDir).getProperty(propertyKey)


dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")

    //retrofit2
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //okhttp3
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

    //STOMP
    implementation("com.github.NaikSoftware:StompProtocolAndroid:1.6.6")

    //rx
    implementation("io.reactivex.rxjava2:rxjava:2.2.5")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.0")

    //compose
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material")
    implementation("androidx.navigation:navigation-compose:2.7.6")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    implementation("androidx.paging:paging-runtime:3.2.1")
    implementation("androidx.paging:paging-compose:3.3.0-alpha02")

    //glide
    ksp("com.github.bumptech.glide:glide:4.14.2")
    implementation("com.github.bumptech.glide:compose:1.0.0-beta01")

    //hilt
    implementation("com.google.dagger:hilt-android:2.48")
    ksp("com.google.dagger:dagger-compiler:2.48") // Dagger compiler
    ksp("com.google.dagger:hilt-compiler:2.48")

    // Icon
    implementation("androidx.compose.material:material-icons-extended")

    //lottie
    implementation("com.airbnb.android:lottie-compose:6.3.0")

    //navermap
    implementation("io.github.fornewid:naver-map-compose:1.4.1")

    //viewmodel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")

    //roulette
    implementation("com.github.JhDroid:android-roulette-wheel-view:1.0.3")

    //viewBinding
    implementation("androidx.compose.ui:ui-viewbinding")

    //https://github.com/nanihadesuka/LazyColumnScrollbar
    implementation("com.github.nanihadesuka:LazyColumnScrollbar:1.9.0")

    //splashScreen
    implementation("androidx.core:core-splashscreen:1.0.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
