plugins {
    if(ModuleConfig.isApp) {
        alias(libs.plugins.android.application)
    } else {
        alias(libs.plugins.android.library)
    }
    alias(libs.plugins.kotlin.android)
    // 启用 Ksp 插件
    alias(libs.plugins.kotlin.ksp)
}

android {
    namespace = "com.zs.module_app_manager"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        // 全局标志控制applicationId
        if (ModuleConfig.isApp) {
            // 安全转换：库模块会跳过这个设置
            (this as? com.android.build.api.dsl.ApplicationDefaultConfig)?.applicationId = ModuleConfig.MODULE_APP_MANGER
            (this as? com.android.build.api.dsl.ApplicationDefaultConfig)?.targetSdk = AppConfig.targetSdk
        }
        minSdk = AppConfig.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        // consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    // 动态替换资源
    sourceSets {
        getByName("main") {
            if(ModuleConfig.isApp) {
                manifest.srcFile("src/main/manifest/AndroidManifest.xml")
            } else {
                manifest.srcFile("src/main/AndroidManifest.xml")
            }
        }
    }
}

// TheRouter
ksp {
    // 指定当前模块名称
    arg("ROUTER_MODULE_NAME", project.name)
    // 模块类型
    arg("MODULE_TYPE", "feature")

    // 日志开关（开发阶段打开）
    arg("LOG_PRINT", ModuleConfig.IsTheRouterLogPrint)
    // 路由冲突检测（必开）
    arg("CHECK_ROUTE_MAP", "true")
    // 文档生成类型（可选）
    arg("ROUTER_DOC_PAGE", "NONE")

    // 开启路由压缩
    arg("COMPRESS_ROUTE", "true")
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(project(":lib_base"))

    // 运行时注解
    ksp(DependenciesConfig.AROUTER_COMPILER)
}