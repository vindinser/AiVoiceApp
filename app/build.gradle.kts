import com.android.build.gradle.internal.api.ApkVariantOutputImpl

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.zs.aivoiceapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.zs.aivoiceapp"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // 签名配置
    signingConfigs {
        register("release") {
            // 别名
            keyAlias = "zsmooc"
            // 别名密码
            keyPassword = "123456"
            // 路径
            storeFile = file("/src/main/jks/aivoice.jks")
            // 签名密码
            storePassword = "123456"
        }
    }

    // 编译类型
    buildTypes {
        getByName("debug") {

        }
        release {
            isMinifyEnabled = false

            // 自动签名打包
            signingConfig = signingConfigs.getByName("release")

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // 输出类型
    android.applicationVariants.all {
        // 编译类型
        val buildType = this.buildType.name
        // 打包输出类型
        outputs.all {
            // 输出APK
            if(this is ApkVariantOutputImpl) {
                if(buildType == "release") {
                    this.outputFileName = "ZS_AI_V${ defaultConfig.versionName }_$buildType.apk"
                } else {
                    this.outputFileName = "ZS_AI_V${ defaultConfig.versionName }_$buildType.apk"
                }
            }
        }
    }

    // 依赖操作
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

// 依赖
dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}