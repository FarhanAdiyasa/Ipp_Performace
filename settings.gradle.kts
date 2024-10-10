pluginManagement {
    repositories {
        maven {url = uri("https://jitpack.io")}
        google()
        //mavenCentral()\
        jcenter();
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven {url = uri("https://jitpack.io")}
        google()
       // mavenCentral()
        jcenter()
    }
}

rootProject.name = "IPP Performance"
include(":app")
 