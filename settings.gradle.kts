pluginManagement {
    repositories {
        google()
        mavenCentral() // <-- Add a newline before this
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "MeowPrintStore"
include(":app")


