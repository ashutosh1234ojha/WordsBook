plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
    id("com.google.devtools.ksp")
//    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.plugin.compose")
//    alias(libs.plugins.kotlin.compose)


}

android {
    namespace = "com.mywordsbook"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.mywordsbook"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        val AZURE_KEY = "903d165f-43bf-467c-9aa3-a92682ca6cbc"
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

    kotlin {
        jvmToolchain(17)
    }
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    kapt {
        generateStubs = true
    }
}

dependencies {
    dependencies {

        implementation("androidx.core:core-ktx:1.10.1")
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
        implementation("androidx.activity:activity-compose:1.7.2")
        implementation(platform("androidx.compose:compose-bom:2023.03.00"))
        implementation("androidx.compose.ui:ui")
        implementation("androidx.compose.ui:ui-graphics")
        implementation("androidx.compose.ui:ui-tooling-preview")
        implementation("androidx.compose.material3:material3")
        implementation("androidx.appcompat:appcompat:1.7.1")
        implementation("androidx.lifecycle:lifecycle-runtime-compose-android:2.8.3")
        implementation("com.google.firebase:firebase-crashlytics-buildtools:3.0.2")
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
        androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
        androidTestImplementation("androidx.compose.ui:ui-test-junit4")
        debugImplementation("androidx.compose.ui:ui-tooling")
        debugImplementation("androidx.compose.ui:ui-test-manifest")

        val room_version = "2.6.1"
//    implementation("androidx.room:room-runtime:$room_version")
        implementation("androidx.room:room-ktx:$room_version")

//    annotationProcessor("androidx.room:room-compiler:$room_version")
//    kapt("androidx.room:room-compiler:$room_version")
        ksp("androidx.room:room-compiler:$room_version")


        val lifecycle_version = "2.6.2"
        implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
        implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")
        implementation("androidx.lifecycle:lifecycle-viewmodel:$lifecycle_version")

        val compose_version = "1.0.0-beta01"
        val nav_compose_version = "2.7.7"
        implementation("androidx.navigation:navigation-compose:$nav_compose_version")

//    implementation("com.google.dagger:hilt-android:2.48")
//    kapt("com.google.dagger:hilt-android-compiler:2.48")
        implementation("com.google.dagger:hilt-android:2.51.1")
        kapt("com.google.dagger:hilt-android-compiler:2.51.1")



        implementation("androidx.compose.runtime:runtime-livedata:$compose_version")
        implementation("androidx.navigation:navigation-compose:2.7.4")
//    implementation(platform("com.google.firebase:firebase-bom:33.1.1"))

        // Import the BoM for the Firebase platform
        implementation(platform("com.google.firebase:firebase-bom:33.1.1"))

        // Declare the dependency for the Cloud Firestore library
        // When using the BoM, you don't specify versions in Firebase library dependencies
        implementation("com.google.firebase:firebase-firestore")

        implementation("com.firebaseui:firebase-ui-auth:7.2.0")
//        implementation("androidx.compose.material:material:1.6.8")
        implementation("androidx.compose.material3:material3:1.4.0")
        implementation("androidx.compose.material:material-icons-core:1.7.8")


        implementation("com.google.android.gms:play-services-auth:21.2.0")

        // Import the BoM for the Firebase platform
        implementation(platform("com.google.firebase:firebase-bom:33.1.2"))

        // Add the dependency for the Firebase Authentication library
        // When using the BoM, you don't specify versions in Firebase library dependencies
        implementation("com.google.firebase:firebase-auth")

//    implementation("androidx.credentials:credentials:1.2.2")

        // optional - needed for credentials support from play services, for devices running
        // Android 13 and below.
//    implementation("androidx.credentials:credentials-play-services-auth:1.2.2")

//    implementation ("com.google.android.libraries.identity.googleid:googleid:1.2.2")
        implementation("androidx.credentials:credentials:1.2.2")
        implementation("androidx.credentials:credentials-play-services-auth:1.2.2")
//    implementation ("com.google.android.libraries.identity.googleid:googleid:1.5.0-alpha02")

//    implementation("androidx.credentials:credentials:1.5.0-alpha02")
//    implementation("androidx.credentials:credentials-play-services-auth:1.5.0-alpha02")

//    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")

        implementation("io.coil-kt:coil-compose:2.2.2")


        val work_version = "2.9.0"
        implementation("androidx.work:work-runtime-ktx:$work_version")

          implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.10.0")
        implementation("androidx.hilt:hilt-navigation-compose:1.2.0")




    }
}