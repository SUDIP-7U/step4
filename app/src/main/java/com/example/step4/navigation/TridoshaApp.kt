package com.example.step4.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.step4.data.SettingsStore
import com.example.step4.data.content.FoodData
import com.example.step4.ui.theme.AppTheme
import com.example.step4.ui.components.LocalAnimationsEnabled
import com.example.step4.ui.screens.food.FoodCategoryScreen
import com.example.step4.ui.screens.food.FoodScreen
import com.example.step4.ui.screens.home.HomeScreen
import com.example.step4.ui.screens.more.AboutScreen
import com.example.step4.ui.screens.more.MoreScreen
import com.example.step4.ui.screens.more.MyDoshaScreen
import com.example.step4.ui.screens.more.SettingsScreen
import com.example.step4.ui.screens.routine.RoutineScreen


/** What is currently on screen: a tab, optionally with a detail page opened on top of it. */
private data class Destination(val tab: MainTab, val detail: DetailRoute?)

@Composable
internal fun TridoshaApp() {
    val colors = AppTheme.colors
    val context = LocalContext.current
    val settings = remember { SettingsStore(context) }

    var remindersEnabled by remember { mutableStateOf(settings.remindersEnabled) }
    var animationsEnabled by remember { mutableStateOf(settings.animationsEnabled) }
    var lastReflection by remember { mutableStateOf(settings.lastReflection) }

    var tab by rememberSaveable { mutableStateOf(MainTab.Home) }
    var detailKey by rememberSaveable { mutableStateOf<String?>(null) }
    val detail = detailKey?.let(DetailRoute::fromKey)
    val destination = Destination(tab, detail)

    // Detail page → its tab; any other tab → Home.
    BackHandler(enabled = detail != null) { detailKey = null }
    BackHandler(enabled = detail == null && tab != MainTab.Home) { tab = MainTab.Home }

    fun openTab(target: MainTab) {
        detailKey = null
        tab = target
    }

    fun openDetail(route: DetailRoute) {
        detailKey = route.key
    }

    CompositionLocalProvider(LocalAnimationsEnabled provides animationsEnabled) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = colors.cream,
            contentColor = colors.ink,
            bottomBar = { TridoshaBottomBar(selected = tab, onSelect = ::openTab) },
        ) { padding ->
            AnimatedContent(
                targetState = destination,
                modifier = Modifier.fillMaxSize(),
                transitionSpec = destinationTransition(animationsEnabled),
                label = "destination",
            ) { target ->
                DestinationContent(
                    destination = target,
                    padding = padding,
                    remindersEnabled = remindersEnabled,
                    animationsEnabled = animationsEnabled,
                    lastReflection = lastReflection,
                    onOpenTab = ::openTab,
                    onOpenDetail = ::openDetail,
                    onCloseDetail = { detailKey = null },
                    onRemindersChange = {
                        remindersEnabled = it
                        settings.remindersEnabled = it
                    },
                    onAnimationsChange = {
                        animationsEnabled = it
                        settings.animationsEnabled = it
                    },
                    onReflectionSaved = {
                        lastReflection = it
                        settings.lastReflection = it
                    },
                    onClearReflection = {
                        lastReflection = null
                        settings.lastReflection = null
                    },
                )
            }
        }
    }
}

private fun destinationTransition(
    animationsEnabled: Boolean,
): AnimatedContentTransitionScope<Destination>.() -> ContentTransform = {
    val opening = targetState.detail != null && initialState.detail == null
    val closing = targetState.detail == null && initialState.detail != null
    when {
        !animationsEnabled -> EnterTransition.None togetherWith ExitTransition.None
        opening -> (slideInHorizontally(tween(300)) { it / 5 } + fadeIn(tween(300))) togetherWith
            (slideOutHorizontally(tween(300)) { -it / 8 } + fadeOut(tween(150)))
        closing -> (slideInHorizontally(tween(300)) { -it / 8 } + fadeIn(tween(300))) togetherWith
            (slideOutHorizontally(tween(300)) { it / 5 } + fadeOut(tween(150)))
        else -> fadeIn(tween(220, delayMillis = 90)) togetherWith fadeOut(tween(90))
    }
}

@Composable
private fun TridoshaBottomBar(selected: MainTab, onSelect: (MainTab) -> Unit) {
    val colors = AppTheme.colors
    NavigationBar(
        containerColor = colors.cardBackground,
        contentColor = colors.ink,
        tonalElevation = 0.dp,
    ) {
        MainTab.entries.forEach { item ->
            val isSelected = item == selected
            NavigationBarItem(
                selected = isSelected,
                onClick = { onSelect(item) },
                icon = {
                    Text(
                        text = item.emoji,
                        fontSize = 22.sp,
                        modifier = Modifier.alpha(if (isSelected) 1f else 0.6f),
                    )
                },
                label = { Text(item.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colors.ink,
                    selectedTextColor = colors.ink,
                    indicatorColor = colors.sageSoft,
                    unselectedIconColor = colors.muted,
                    unselectedTextColor = colors.muted,
                ),
            )
        }
    }
}

@Composable
private fun DestinationContent(
    destination: Destination,
    padding: PaddingValues,
    remindersEnabled: Boolean,
    animationsEnabled: Boolean,
    lastReflection: String?,
    onOpenTab: (MainTab) -> Unit,
    onOpenDetail: (DetailRoute) -> Unit,
    onCloseDetail: () -> Unit,
    onRemindersChange: (Boolean) -> Unit,
    onAnimationsChange: (Boolean) -> Unit,
    onReflectionSaved: (String) -> Unit,
    onClearReflection: () -> Unit,
) {
    when (val detail = destination.detail) {
        is DetailRoute.FoodCategoryDetail -> {
            val category = FoodData.categoryById(detail.categoryId)
            if (category != null) {
                FoodCategoryScreen(category = category, padding = padding, onBack = onCloseDetail)
            } else {
                FoodScreen(
                    padding = padding,
                    onOpenCategory = { onOpenDetail(DetailRoute.FoodCategoryDetail(it)) },
                )
            }
        }
        DetailRoute.MyDosha -> MyDoshaScreen(
            padding = padding,
            lastReflection = lastReflection,
            onSaveReflection = onReflectionSaved,
            onBack = onCloseDetail,
        )
        DetailRoute.About -> AboutScreen(padding = padding, onBack = onCloseDetail)
        DetailRoute.Settings -> SettingsScreen(
            padding = padding,
            remindersEnabled = remindersEnabled,
            animationsEnabled = animationsEnabled,
            hasReflection = lastReflection != null,
            onRemindersChange = onRemindersChange,
            onAnimationsChange = onAnimationsChange,
            onClearReflection = onClearReflection,
            onBack = onCloseDetail,
        )
        null -> when (destination.tab) {
            MainTab.Home -> HomeScreen(
                padding = padding,
                remindersEnabled = remindersEnabled,
                onOpenRoutine = { onOpenTab(MainTab.Routine) },
                onOpenFood = { onOpenTab(MainTab.Food) },
                onOpenMyDosha = { onOpenDetail(DetailRoute.MyDosha) },
            )
            MainTab.Routine -> RoutineScreen(padding = padding)
            MainTab.Food -> FoodScreen(
                padding = padding,
                onOpenCategory = { onOpenDetail(DetailRoute.FoodCategoryDetail(it)) },
            )
            MainTab.More -> MoreScreen(
                padding = padding,
                lastReflection = lastReflection,
                onOpenMyDosha = { onOpenDetail(DetailRoute.MyDosha) },
                onOpenAbout = { onOpenDetail(DetailRoute.About) },
                onOpenSettings = { onOpenDetail(DetailRoute.Settings) },
            )
        }
    }
}
