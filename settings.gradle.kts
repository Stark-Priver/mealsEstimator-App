enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
        maven("https://androidx.dev/storage/compose-compiler/repository/") {
            content {
                includeGroup("androidx.compose.compiler")
            }
        }
    }
}
rootProject.name = "MealTime"
include(":app")
include(":feature:settings")
include(":feature:kitchen-timer")
include(":feature:home:data")
include(":feature:home:domain")
include(":feature:home:presentation")
include(":feature:home:di")
include(":feature:search:data")
include(":feature:search:domain")
include(":feature:search:presentation")
include(":feature:search:di")
include(":feature:favorites:domain")
include(":feature:favorites:data")
include(":feature:favorites:presentation")
include(":feature:favorites:di")
include(":feature:auth:data")
include(":feature:auth:di")
include(":feature:auth:domain")
include(":feature:auth:presentation")
include(":core:designsystem")
include(":core:common")
include(":core:database")
include(":core:network")
include(":core:analytics")
include(":core:preferences")
include(":feature:addmeal:di")
include(":feature:addmeal:data")
include(":feature:addmeal:domain")
include(":feature:addmeal:presentation")
