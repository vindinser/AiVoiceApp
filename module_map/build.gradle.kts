plugins {
    if(ModuleConfig.isApp) {
        alias(libs.plugins.android.application)
    } else {
        alias(libs.plugins.android.library)
    }
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.zs.module_map"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        if (ModuleConfig.isApp) {
            (this as? com.android.build.api.dsl.ApplicationDefaultConfig)?.applicationId = ModuleConfig.MODULE_APP_MANGER
            (this as? com.android.build.api.dsl.ApplicationDefaultConfig)?.targetSdk = AppConfig.targetSdk
        }

        minSdk = AppConfig.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // ARouter
        javaCompileOptions {
            annotationProcessorOptions {
                arguments.plusAssign(mapOf("AROUTER_MODULE_NAME" to project.name))
            }
        }
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

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(project(":lib_base"))

    // 运行时注解
    annotationProcessor(DependenciesConfig.AROUTER_COMPILER)
}