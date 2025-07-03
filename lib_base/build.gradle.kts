plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.zs.lib_base"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.work.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Event Bus
    api(DependenciesConfig.EVENT_BUS)

    // ARouter
    api(DependenciesConfig.AROUTER)
    // api(DependenciesConfig.THEROUTER_PLUGIN)
    // 运行时注解
    annotationProcessor(DependenciesConfig.AROUTER_COMPILER)

    // RecyclerView
    api(DependenciesConfig.RECYCLERVIEW)

    // MagicViewPager
    api(DependenciesConfig.VIEWPAGER)

    // AndPermissions
    // api(DependenciesConfig.AND_PERMISSIONS)

    api(project(":lib_voice"))
    api(project(":lib_network"))
}