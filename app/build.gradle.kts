import com.android.build.gradle.internal.api.ApkVariantOutputImpl

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    // 启用 Kapt 插件
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.zs.aivoiceapp"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // ARouter
//        javaCompileOptions {
//            annotationProcessorOptions {
//                arguments.plusAssign(mapOf("AROUTER_MODULE_NAME" to project.name))
//            }
//        }
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

// ARouter
kapt {
    arguments {
        arg("AROUTER_MODULE_NAME", project.name)
    }
}

// 依赖
dependencies {
    // Android基础库
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    // 测试库
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(project(":lib_base"))

    if(!ModuleConfig.isApp) {
        implementation(project(":module_app_manager"))
        implementation(project(":module_constellation"))
        implementation(project(":module_developer"))
        implementation(project(":module_joke"))
        implementation(project(":module_map"))
        implementation(project(":module_setting"))
        implementation(project(":module_voice_setting"))
        implementation(project(":module_weather"))
    }

    // 运行时注解
    kapt(DependenciesConfig.AROUTER_COMPILER)
}