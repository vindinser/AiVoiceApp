pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // maven { setUrl("https://jitpack.io") } 函数式调用所以使用setUrl
        maven { setUrl("https://jitpack.io") }
    }
}

rootProject.name = "AiVoiceApp"
rootProject.buildFileName = "build.gradele.kts"
include(":app")
include(":lib_base")
include(":lib_network")
include(":lib_voice")
include(":module_app_manager")
include(":module_constellation")
include(":module_developer")
include(":module_joke")
include(":module_map")
include(":module_setting")
include(":module_voice_setting")
include(":module_weather")
