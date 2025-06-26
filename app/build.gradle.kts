import com.android.build.gradle.internal.api.ApkVariantOutputImpl

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    // 启用 ksp 插件
    alias(libs.plugins.kotlin.ksp)
    // TheRouter Plugin
    // alias(libs.plugins.therouter.plugin)
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
        // javaCompileOptions {
        //     annotationProcessorOptions {
        //         arguments.plusAssign(mapOf("AROUTER_MODULE_NAME" to project.name))
        //     }
        // }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

// ARouter
//kapt {
//    arguments {
//        arg("AROUTER_MODULE_NAME", project.name)
//    }
//}

// TheRouter
ksp {
    // 指定根包名（必须与主应用包名相同）
    arg("THEROUTER_APP_PACKAGE", AppConfig.applicationId)
    arg("KSP_MODULE_NAME", project.name)
    // 指定当前模块名称
    arg("ROUTER_MODULE_NAME", project.name)
    // 模块类型
    arg("MODULE_TYPE", "app")

    // 关键：聚合所有模块路由
    arg("ROUTER_MODULES", "module_app_manager:" +
            "module_constellation:" +
            "module_developer:" +
            "module_joke:" +
            "module_map:" +
            "module_setting:" +
            "module_voice_setting:" +
            "module_weather"
    )
    // 强制生成合并路由表
    arg("GENERATE_ROUTE_MAP_MERGE", "true")

    // 日志开关（开发阶段打开）
    arg("LOG_PRINT", ModuleConfig.IsTheRouterLogPrint)
    // 路由冲突检测（必开）
    arg("CHECK_ROUTE_MAP", "true")
    // 文档生成类型（可选）
    arg("ROUTER_DOC_PAGE", "NONE")

    // 开启路由压缩
    arg("COMPRESS_ROUTE", "true")
    // 开启增量编译 KSP 1.9.0+ 默认启用
    // arg("KSP_INCREMENTAL", "true")
    // 路由缓存优化 1.2.4 此参数已弃用
    // arg("ROUTE_CACHE_SIZE", "1000")
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
    implementation(libs.androidx.constraintlayout)
    implementation(libs.appcompat)
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
    ksp(DependenciesConfig.AROUTER_COMPILER)
}