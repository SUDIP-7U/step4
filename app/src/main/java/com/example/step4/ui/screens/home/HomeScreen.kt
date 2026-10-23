package com.example.step4.ui.screens.home

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.step4.data.content.PauseData
import com.example.step4.data.content.RoutineData
import com.example.step4.data.model.Dosha
import com.example.step4.ui.components.BulletList
import com.example.step4.ui.components.EmojiBadge
import com.example.step4.ui.components.ExpandChevron
import com.example.step4.ui.components.ExpandSection
import com.example.step4.ui.components.LocalAnimationsEnabled
import com.example.step4.ui.components.ScreenList
import com.example.step4.ui.components.SectionLabel
import com.example.step4.ui.components.TridoshaButton
import com.example.step4.ui.components.TridoshaCard
import com.example.step4.ui.components.accentColor
import com.example.step4.ui.components.softColor
import com.example.step4.ui.theme.AppTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlinx.coroutines.delay

private const val REST_SCALE = 0.6f
private const val STATIC_SCALE = 0.85f

@Composable
internal fun HomeScreen(
    padding: PaddingValues,
    remindersEnabled: Boolean,
    onOpenRoutine: () -> Unit,
    onOpenFood: () -> Unit,
    onOpenMyDosha: () -> Unit,
) {
    ScreenList(padding) {
        item(key = "greeting") { GreetingBanner() }
        if (remindersEnabled) {
            item(key = "reminder") { ReminderCard(onClick = onOpenRoutine) }
        }
        item(key = "pause") { DailyPauseCard() }
        item(key = "doshas_label") { SectionLabel("The three doshas") }
        items(Dosha.entries, key = { it.name }) { dosha -> DoshaOverviewCard(dosha) }
        item(key = "actions_label") { SectionLabel("Quick actions") }
        item(key = "actions") {
            QuickActions(
                onOpenRoutine = onOpenRoutine,
                onOpenFood = onOpenFood,
                onOpenMyDosha = onOpenMyDosha,
            )
        }
    }
}

private fun greetingFor(hour: Int): String = when (hour) {
    in 5..11 -> "Good morning"
    in 12..16 -> "Good afternoon"
    in 17..20 -> "Good evening"
    else -> "Peaceful night"
}

@Composable
private fun GreetingBanner() {
    val colors = AppTheme.colors
    val now = remember { LocalDateTime.now() }
    val date = remember(now) {
        now.format(DateTimeFormatter.ofPattern("EEEE, d MMMM", Locale.getDefault()))
    }
    TridoshaCard(
        containerColor = colors.deepSage,
        shape = RoundedCornerShape(26.dp),
        border = null,
        contentPadding = PaddingValues(24.dp),
    ) {
        Text(
            text = date.uppercase(),
            style = MaterialTheme.typography.labelMedium,
            color = colors.onDeepSage.copy(alpha = 0.8f),
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = greetingFor(now.hour),
            style = MaterialTheme.typography.headlineLarge,
            color = colors.onDeepSage,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Take a moment to arrive, breathe and notice how you feel today.",
            style = MaterialTheme.typography.bodyLarge,
            color = colors.onDeepSage.copy(alpha = 0.9f),
        )
    }
}

@Composable
private fun ReminderCard(onClick: () -> Unit) {
    val colors = AppTheme.colors
    val period = remember { RoutineData.periodForHour(LocalDateTime.now().hour) }
    TridoshaCard(
        onClick = onClick,
        containerColor = colors.sageSoft,
        border = null,
        contentPadding = PaddingValues(16.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            EmojiBadge(emoji = period.emoji, background = colors.cardBackground)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "GENTLE REMINDER",
                    style = MaterialTheme.typography.labelMedium,
                    color = colors.muted,
                )
                Text(
                    text = "${period.title}: ${period.summary}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.ink,
                )
            }
        }
    }
}

private enum class BreathPhase(val label: String) {
    Ready("Breathe"),
    Inhale("Breathe in"),
    Hold("Hold gently"),
    Exhale("Breathe out"),
}

@Composable
private fun DailyPauseCard() {
    val colors = AppTheme.colors
    val animate = LocalAnimationsEnabled.current
    var index by rememberSaveable { mutableStateOf(PauseData.indexForToday()) }
    val prompt = PauseData.prompts[index % PauseData.prompts.size]
    val scale = remember { Animatable(REST_SCALE) }
    var phase by remember { mutableStateOf(BreathPhase.Ready) }

    LaunchedEffect(prompt.id, animate) {
        if (!animate) {
            phase = BreathPhase.Ready
            scale.snapTo(STATIC_SCALE)
            return@LaunchedEffect
        }
        scale.snapTo(REST_SCALE)
        while (true) {
            phase = BreathPhase.Inhale
            scale.animateTo(1f, tween(prompt.inhaleSeconds * 1000, easing = FastOutSlowInEasing))
            if (prompt.holdSeconds > 0) {
                phase = BreathPhase.Hold
                delay(prompt.holdSeconds * 1000L)
            }
            phase = BreathPhase.Exhale
            scale.animateTo(REST_SCALE, tween(prompt.exhaleSeconds * 1000, easing = FastOutSlowInEasing))
        }
    }

    val pattern = buildString {
        append("Inhale ${prompt.inhaleSeconds}s")
        if (prompt.holdSeconds > 0) append(" · Hold ${prompt.holdSeconds}s")
        append(" · Exhale ${prompt.exhaleSeconds}s")
    }

    TridoshaCard(
        shape = RoundedCornerShape(26.dp),
        contentPadding = PaddingValues(22.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SectionLabel("Your Daily Pause")
            Spacer(Modifier.height(4.dp))
            Text(
                text = prompt.title,
                style = MaterialTheme.typography.titleLarge,
                color = colors.ink,
            )
            Spacer(Modifier.height(16.dp))
            Box(modifier = Modifier.size(190.dp), contentAlignment = Alignment.Center) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            scaleX = scale.value
                            scaleY = scale.value
                        }
                        .background(colors.sage.copy(alpha = 0.28f), CircleShape),
                )
                Box(
                    modifier = Modifier
                        .size(130.dp)
                        .graphicsLayer {
                            scaleX = scale.value
                            scaleY = scale.value
                        }
                        .background(colors.sage.copy(alpha = 0.55f), CircleShape),
                )
                Text(
                    text = phase.label,
                    style = MaterialTheme.typography.titleMedium,
                    color = colors.ink,
                    textAlign = TextAlign.Center,
                )
            }
            Spacer(Modifier.height(16.dp))
            Text(
                text = prompt.guidance,
                style = MaterialTheme.typography.bodyMedium,
                color = colors.ink,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = pattern,
                style = MaterialTheme.typography.labelMedium,
                color = colors.muted,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(16.dp))
            TridoshaButton(
                text = "Try another pause",
                onClick = { index = (index + 1) % PauseData.prompts.size },
                tonal = true,
            )
        }
    }
}

@Composable
private fun DoshaOverviewCard(dosha: Dosha) {
    val colors = AppTheme.colors
    var expanded by rememberSaveable(dosha.name) { mutableStateOf(false) }
    TridoshaCard(
        onClick = { expanded = !expanded },
        containerColor = dosha.softColor(),
        border = null,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            EmojiBadge(emoji = dosha.emoji, background = colors.cardBackground, size = 48.dp)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = dosha.displayName,
                    style = MaterialTheme.typography.titleLarge,
                    color = colors.ink,
                )
                Text(
                    text = dosha.element,
                    style = MaterialTheme.typography.labelMedium,
                    color = colors.muted,
                )
            }
            ExpandChevron(expanded = expanded)
        }
        Spacer(Modifier.height(10.dp))
        Text(
            text = dosha.tagline,
            style = MaterialTheme.typography.bodyMedium,
            color = colors.ink,
        )
        ExpandSection(visible = expanded) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "Qualities: " + dosha.qualities.joinToString(" · "),
                    style = MaterialTheme.typography.labelLarge,
                    color = colors.ink,
                )
                Spacer(Modifier.height(10.dp))
                SectionLabel("Ways to support balance")
                Spacer(Modifier.height(8.dp))
                BulletList(items = dosha.supportTips.take(3), accent = dosha.accentColor())
            }
        }
    }
}

@Composable
private fun QuickActions(
    onOpenRoutine: () -> Unit,
    onOpenFood: () -> Unit,
    onOpenMyDosha: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        QuickActionCard(
            emoji = "🌅",
            label = "Daily routine",
            onClick = onOpenRoutine,
            modifier = Modifier.weight(1f),
        )
        QuickActionCard(
            emoji = "🥗",
            label = "Food guide",
            onClick = onOpenFood,
            modifier = Modifier.weight(1f),
        )
        QuickActionCard(
            emoji = "🧭",
            label = "Find my dosha",
            onClick = onOpenMyDosha,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun QuickActionCard(
    emoji: String,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TridoshaCard(
        onClick = onClick,
        modifier = modifier.fillMaxHeight(),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            EmojiBadge(emoji = emoji)
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = AppTheme.colors.ink,
                textAlign = TextAlign.Center,
            )
        }
    }
}
