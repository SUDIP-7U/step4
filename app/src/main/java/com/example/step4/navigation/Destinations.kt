package com.tridosha.balance.navigation

/** The four bottom-bar tabs. */
internal enum class MainTab(val label: String, val emoji: String) {
    Home("Home", "🏡"),
    Routine("Routine", "🌅"),
    Food("Food", "🥗"),
    More("More", "✨"),
}

/**
 * Pages opened on top of a tab. Each route has a stable string [key] so the current page
 * can survive configuration changes and process death via `rememberSaveable`.
 */
internal sealed interface DetailRoute {
    val key: String

    data class FoodCategoryDetail(val categoryId: String) : DetailRoute {
        override val key: String get() = "$FOOD_PREFIX$categoryId"
    }

    data object MyDosha : DetailRoute {
        override val key: String get() = "my_dosha"
    }

    data object About : DetailRoute {
        override val key: String get() = "about"
    }

    data object Settings : DetailRoute {
        override val key: String get() = "settings"
    }

    companion object {
        const val FOOD_PREFIX = "food:"

        fun fromKey(key: String): DetailRoute? = when {
            key.startsWith(FOOD_PREFIX) -> FoodCategoryDetail(key.removePrefix(FOOD_PREFIX))
            key == "my_dosha" -> MyDosha
            key == "about" -> About
            key == "settings" -> Settings
            else -> null
        }
    }
}
