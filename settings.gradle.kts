pluginManagement {
    repositories {
        // গুগলের নিজস্ব তৈরি অ্যান্ড্রয়েড, গুগল সার্ভিস এবং অ্যান্ড্রয়েড এক্স (AndroidX) এর
// অফিশিয়াল প্লাগইনগুলো ডাউনলোড করার জন্য শুধু গুগল রিপোজিটরি ব্যবহার করা হবে
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
// jcenter() বন্ধ হয়ে যাওয়ার পর এটিই এখন প্রধান এবং নির্ভরযোগ্য রিপ্লেসমেন্ট
        mavenCentral()

        gradlePluginPortal()
        // ১. স্প্রিং বুট (Spring Boot) প্রজেক্ট রান করার প্লাগইন ডাউনলোড করতে এটি দরকার
        // ২. সিকিউরিটি চেকার বা কোড স্ক্যানার টুল ডাউনলোড করতে এটি দরকার
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "step4"
include(":app")
