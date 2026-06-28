plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.google.services)
}

import java.util.Properties
import java.io.FileInputStream

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(FileInputStream(localPropertiesFile))
}

val admobAppId = localProperties.getProperty("ADMOB_APP_ID") ?: ""
val admobBannerId = localProperties.getProperty("ADMOB_BANNER_ID") ?: ""
val admobInterstitialId = localProperties.getProperty("ADMOB_INTERSTITIAL_ID") ?: ""
val admobRewardedId = localProperties.getProperty("ADMOB_REWARDED_ID") ?: ""
val keystorePasswordStr = localProperties.getProperty("KEYSTORE_PASSWORD") ?: ""
val keyPasswordStr = localProperties.getProperty("KEY_PASSWORD") ?: ""

android {
    namespace = "com.example.astroyorum"
    compileSdk = 36
    defaultConfig {
        applicationId = "com.gokcank.astroyorum"
        minSdk = 26
        targetSdk = 36
        versionCode = 2
        versionName = "1.0.1"

        manifestPlaceholders["admobAppId"] = admobAppId
        buildConfigField("String", "ADMOB_BANNER_ID", "\"${admobBannerId}\"")
        buildConfigField("String", "ADMOB_INTERSTITIAL_ID", "\"${admobInterstitialId}\"")
        buildConfigField("String", "ADMOB_REWARDED_ID", "\"${admobRewardedId}\"")
    }

    signingConfigs {
        create("release") {
            storeFile = file("../astroyorum.jks")
            storePassword = keystorePasswordStr
            keyAlias = "astroyorum"
            keyPassword = keyPasswordStr
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
      compose = true
      aidl = false
      buildConfig = true
      shaders = false
    }

    packaging {
      resources {
        excludes += "/META-INF/{AL2.0,LGPL2.1}"
      }
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
  val composeBom = platform(libs.androidx.compose.bom)
  implementation(composeBom)
  androidTestImplementation(composeBom)

  // Core Android dependencies
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)

  // Arch Components
  implementation(libs.androidx.lifecycle.runtime.compose)
  implementation(libs.androidx.lifecycle.viewmodel.compose)

  // Compose
  implementation(libs.androidx.compose.ui)
  implementation(libs.androidx.compose.ui.tooling.preview)
  implementation(libs.androidx.compose.material3)
  // Tooling
  debugImplementation(libs.androidx.compose.ui.tooling)
  // Instrumented tests
  androidTestImplementation(libs.androidx.compose.ui.test.junit4)
  debugImplementation(libs.androidx.compose.ui.test.manifest)

  // Local tests: jUnit, coroutines, Android runner
  testImplementation(libs.junit)
  testImplementation(libs.kotlinx.coroutines.test)

  // Instrumented tests: jUnit rules and runners
  androidTestImplementation(libs.androidx.test.core)
  androidTestImplementation(libs.androidx.test.ext.junit)
  androidTestImplementation(libs.androidx.test.runner)
  androidTestImplementation(libs.androidx.test.espresso.core)

  // Navigation
  implementation(libs.androidx.navigation3.ui)
  implementation(libs.androidx.navigation3.runtime)
  implementation(libs.androidx.lifecycle.viewmodel.navigation3)

  // AdMob
  implementation(libs.google.play.services.ads)

  // DataStore (local prefs)
  implementation(libs.androidx.datastore.preferences)

  // Coil (image loading)
  implementation(libs.coil.compose)
  implementation(libs.coil.network.okhttp)

  // Firebase
  implementation(platform(libs.firebase.bom))
  implementation(libs.firebase.analytics)
  implementation(libs.firebase.firestore)
}

