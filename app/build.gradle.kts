plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "io.dronuts.medihelp"
    compileSdk = 34

    defaultConfig {
        applicationId = "io.dronuts.medihelp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "0.1.0"

        buildConfigField("String", "BASE_API_URL", "\"https://api.medihelp.dronuts.io/v1/\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            // enable debug logging, etc.
        }
    }

    flavorDimensions += listOf("environment")
    productFlavors {
        create("demo") {
            dimension = "environment"
            buildConfigField("String", "BASE_API_URL", "\"https://demo.api.medihelp.local/v1/\"")
        }
        create("prod") {
            dimension = "environment"
            buildConfigField("String", "BASE_API_URL", "\"https://api.medihelp.dronuts.io/v1/\"")
        }
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.5"
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    packagingOptions {
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")

    // Compose
    implementation("androidx.compose.ui:ui:1.4.3")
    implementation("androidx.compose.material3:material3:1.1.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.navigation:navigation-compose:2.6.0")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.47")
    kapt("com.google.dagger:hilt-compiler:2.47")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Retrofit + OkHttp
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")

    // Room
    implementation("androidx.room:room-runtime:2.5.2")
    implementation("androidx.room:room-ktx:2.5.2")
    kapt("androidx.room:room-compiler:2.5.2")

    // MQTT (placeholder)
    implementation("org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.2.5")

    // Play Services Location & Maps (developers must add API key)
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("com.google.maps:google-maps-services:2.1.0")

    // Firebase BOM (FCM)
    implementation(platform("com.google.firebase:firebase-bom:32.0.0"))
    implementation("com.google.firebase:firebase-messaging")

    // testing / debug
    testImplementation("junit:junit:4.13.2")
}
