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

object DependenciesConfig {
    // 依赖常量
}

// Module配置
object ModuleConfig {
    // 是否APP
    var isApp = false

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