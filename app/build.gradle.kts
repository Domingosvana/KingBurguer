plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.googleksp)
    alias(libs.plugins.googleHilt)
}

android {
    namespace = "com.ango.kingburguer"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.ango.kingburguer"
        minSdk = 23
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        //buildConfigField("String", "X_SECRET_KEY", getSecretKey() + "")
            buildConfigField("String", "X_SECRET_KEY","\"${getSecretKey()}\"")

      //  buildConfigField(
          //  "String",
      ////      "X_SECRET_KEY",
      //      "\"${getSecretKey()}\""
        //)


        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    implementation(libs.androidx.compose.material)


    implementation(libs.androidx.navigation)
    //icon importado
    implementation(libs.androidx.material.icons)



    //redes
    implementation(libs.retrofit2)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp3.logging.interceptor)

    //LocalStore
    implementation(libs.datastore.preferences)

      //coreSplashScreen
    implementation(libs.core.splashscreen)

    // coil-compose coil-network
    //imagem assicrona ou rede http
    implementation(libs.coil.compose)
    implementation(libs.coil.network)



   // hillt/ksp
    implementation(libs.google.hilt)
    implementation(libs.google.hilt.navigation)
    ksp(libs.google.hilt.compiler)




    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)



}

//fun getSecretKey(): String {
  //  return project.findProperty("x_secret_key") as String
//}


fun getSecretKey():String?{
    return project.findProperty("x_secret_key") as? String
}

//fun getSecretkey(): String?{
  //  return project.findProperty("x_secret_key") as? String
//}