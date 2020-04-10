plugins {
    id(BuildPlugins.androidLibrary)
    kotlin(BuildPlugins.kotlinAndroid)
    kotlin(BuildPlugins.kotlinAndroidExtensions)
    id(BuildPlugins.serialization) version kotlinVersion
}

android {
    compileSdkVersion(Android.compileSdkVersion)
    defaultConfig {
        minSdkVersion(Android.minSdk)
        targetSdkVersion(Android.targetSdk)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = TestDependencies.testInstrumentationRunner

        buildTypes {
            getByName("release") {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
                manifestPlaceholders = mapOf("enableCrashReporting" to "false")
                buildConfigField("String", "PS_BASE_URL", "\"https://dev.pactsafe.io\"")

            }
            getByName("debug") {
                isMinifyEnabled = false
                isDebuggable = true
                manifestPlaceholders = mapOf("enableCrashReporting" to "true")
                buildConfigField("String", "PS_BASE_URL", "\"https://dev.pactsafe.io\"")
            }
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dependencies.kotlinStdLib)
    implementation(Dependencies.appCompat)
    implementation(Dependencies.ktxCore)
    implementation(Dependencies.constraintLayout)
    testImplementation(TestDependencies.junit)
    androidTestImplementation(TestDependencies.androidxTestRunner)
    androidTestImplementation(TestDependencies.espressoCore)

    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofitCallAdapter)
    implementation(Dependencies.retrofitConverter)
    implementation(Dependencies.androidCoroutines)
    implementation(Dependencies.ktxCore)
    implementation(Dependencies.koin)
    implementation(Dependencies.rxJava)
    implementation(Dependencies.rxKotlin)
    implementation(Dependencies.rxAndroid)
    implementation(Dependencies.coroutines)
    implementation(Dependencies.androidCoroutines)
    implementation(Dependencies.constraintLayout)
    implementation(Dependencies.gson)
    implementation(Dependencies.okHttp)
    implementation(Dependencies.okHttpLogging)
    implementation(Dependencies.serializationRuntime)
    implementation(Dependencies.timber)
    implementation(Dependencies.material)
}
