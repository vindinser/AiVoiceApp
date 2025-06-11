apply {
    plugin("kotlin")
}

buildscript {
    // 仓库
    repositories {
        gradlePluginPortal()
    }
    // 依赖
    dependencies {
        classpath(kotlin("gradle-plugin", "1.3.71"))
    }
}

dependencies {
    implementation(gradleKotlinDsl())
    implementation(kotlin("stdlib", "1.3.71"))
}

repositories {
    gradlePluginPortal()
}