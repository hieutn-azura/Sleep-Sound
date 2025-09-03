import java.text.SimpleDateFormat
import java.util.Date

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.parcelize)
    alias(libs.plugins.google.devtools.ksp)
    id("androidx.navigation.safeargs.kotlin")
    alias(libs.plugins.gmsGoogleServices)
    alias(libs.plugins.firebaseCrashlytics)
}

android {
    namespace = "com.hdt.sleepsound"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.hdt.sleepsound"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        val formattedDate = SimpleDateFormat("MMM.dd.yyyy").format(Date())
        base.archivesName = "sleep_sound-v$versionName($versionCode)_${formattedDate}"
    }

    signingConfigs {
        create("release") {
            storeFile = rootProject.file("keystores/sleep_sound.jks")
            storePassword = "123456"
            keyAlias = "sleep_sound"
            keyPassword = "123456"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isDebuggable = false
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    flavorDimensions.add("version")
    productFlavors {
        create("dev") {
            applicationId = "com.hdt.sleepsound"
            buildConfigField("boolean", "build_debug", "true")
        }
        create("product") {
            applicationId = "com.sleepy.baby.relaxmusic.sleepcycle.relaxation.sleepsounds.whitenoise.rainsounds.sleeptrack.sleepo"
            buildConfigField("boolean", "build_debug", "false")
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.koin.android)
    implementation(libs.koin.android.compat)
    implementation(libs.koin.core)
    implementation(libs.glide)
    implementation(libs.review.ktx)
    implementation(libs.lottie)

    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}