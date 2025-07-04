/**
 * Kotlin 常量
 */
object KotlinConstants {
    const val gradle_version = "3.6.2"
    const val kotlin_version = "1.3.71"
}

object AppConfig {
    const val applicationId = "com.zs.aivoiceapp"
    const val compileSdk = 35
    const val minSdk = 21
    const val targetSdk = 35
    const val versionCode = 1
    const val versionName = "1.0"

}

// 依赖常量
object DependenciesConfig {
    // Event Bus
    const val EVENT_BUS = "org.greenrobot:eventbus:3.3.1"

    // ARouter
    // const val AROUTER = "com.alibaba:arouter-api:1.5.2"
    // const val AROUTER_COMPILER = "com.alibaba:arouter-compiler:1.5.2"

    // TheRouter
    const val AROUTER = "cn.therouter:router:1.3.0-rc4"
    const val AROUTER_COMPILER = "cn.therouter:apt:1.3.0-rc4"
    const val THEROUTER_PLUGIN = "cn.therouter:plugin:1.3.0-rc4"

    // RecyclerView
    const val RECYCLERVIEW = "androidx.recyclerview:recyclerview:1.2.1"

    // Retrofit
    const val RETROFIT = "com.squareup.retrofit2:retrofit:3.0.0"
    const val RETROFIT_GSON = "com.squareup.retrofit2:converter-gson:3.0.0"

    // MagicViewPager
    // const val VIEWPAGER = "com.zhy:magic-viewpager:1.0.1"
    const val VIEWPAGER = "androidx.viewpager2:viewpager2:1.1.0"
    const val TABLAYOUT = "com.google.android.material:material:1.11.0"

    // 权限请求
    // const val AND_PERMISSIONS = "com.yanzhenjie:permission:2.0.2"

    // lottie动画
    const val LOTTIE = "com.airbnb.android:lottie:3.4.0"
}

// Module配置
object ModuleConfig {
    // 是否APP
    var isApp = false

    // TheRouter日志打印
    var IsTheRouterLogPrint = "true"

    // 包名
    const val MODULE_APP_MANGER = "com.zs.module_app_manager"
    const val MODULE_CONSTELLATION = "com.zs.module_constellation"
    const val MODULE_DEVELOPER = "com.zs.module_developer"
    const val MODULE_JOKE = "com.zs.module_joke"
    const val MODULE_MAP = "com.zs.module_map"
    const val MODULE_SETTING = "com.zs.module_setting"
    const val MODULE_VOICE_SETTING = "com.zs.module_voice_setting"
    const val MODULE_WEATHER = "com.zs.module_weather"

}