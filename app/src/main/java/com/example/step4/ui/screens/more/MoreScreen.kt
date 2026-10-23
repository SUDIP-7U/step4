package com.example.step4.ui.screens.more

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.step4.data.content.ReflectionData
import com.example.step4.data.model.Dosha
import com.example.step4.data.model.ReflectionQuestion
import com.example.step4.ui.components.BulletList
import com.example.step4.ui.components.DetailHeader
import com.example.step4.ui.components.DisclaimerCard
import com.example.step4.ui.components.DoshaChip
import com.example.step4.ui.components.EmojiBadge
import com.example.step4.ui.components.ScreenList
import com.example.step4.ui.components.ScreenTitle
import com.example.step4.ui.components.SectionLabel
import com.example.step4.ui.components.TridoshaButton
import com.example.step4.ui.components.TridoshaCard
import com.example.step4.ui.components.accentColor
import com.example.step4.ui.components.softColor
import com.example.step4.ui.theme.AppTheme

// ───────────────────────────── More ─────────────────────────────

@Composable
internal fun MoreScreen(
    padding: PaddingValues,
    lastReflection: String?,
    onOpenMyDosha: () -> Unit,
    onOpenAbout: () -> Unit,
    onOpenSettings: () -> Unit,
) {
    val lastDoshas = remember(lastReflection) { ReflectionData.decode(lastReflection) }
    ScreenList(padding) {
        item(key = "title") {
            ScreenTitle(
                title = "More",
                subtitle = "Reflect, learn and make the app your own.",
            )
        }
        item(key = "my_dosha") {
            MoreLink(
                emoji = "🧘",
                title = "My Dosha",
                subtitle = if (lastDoshas.isEmpty()) {
                    "Take a short reflection quiz"
                } else {
                    "Last reflection: ${ReflectionData.describe(lastDoshas)}"
                },
                onClick = onOpenMyDosha,
            )
        }
        item(key = "about") {
            MoreLink(
                emoji = "📖",
                title = "About",
                subtitle = "Ayurveda, the doshas and this app",
                onClick = onOpenAbout,
            )
        }
        item(key = "settings") {
            MoreLink(
                emoji = "⚙️",
                title = "Settings",
                subtitle = "Reminders and animations",
                onClick = onOpenSettings,
            )
        }
        item(key = "disclaimer") { DisclaimerCard() }
    }
}

@Composable
private fun MoreLink(emoji: String, title: String, subtitle: String, onClick: () -> Unit) {
    val colors = AppTheme.colors
    TridoshaCard(onClick = onClick) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            EmojiBadge(emoji = emoji, size = 48.dp)
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, style = MaterialTheme.typography.titleMedium, color = colors.ink)
                Text(text = subtitle, style = MaterialTheme.typography.bodyMedium, color = colors.muted)
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = colors.muted,
            )
        }
    }
}

// ───────────────────────────── My Dosha ─────────────────────────────

@Composable
internal fun MyDoshaScreen(
    padding: PaddingValues,
    lastReflection: String?,
    onSaveReflection: (String) -> Unit,
    onBack: () -> Unit,
) {
    val questions = ReflectionData.questions
    var started by rememberSaveable { mutableStateOf(false) }
    var answers by rememberSaveable { mutableStateOf(listOf<Int>()) }
    val complete = answers.size >= questions.size
    val scores = remember(answers) { ReflectionData.scores(answers) }
    val dominant = remember(scores) { ReflectionData.dominant(scores) }
    val lastDoshas = remember(lastReflection) { ReflectionData.decode(lastReflection) }

    LaunchedEffect(complete) {
        if (complete && dominant.isNotEmpty()) onSaveReflection(ReflectionData.encode(dominant))
    }

    ScreenList(padding) {
        item(key = "header") {
            DetailHeader(
                title = "My Dosha",
                onBack = onBack,
                subtitle = "A short reflection, not a diagnosis",
            )
        }
        when {
            complete -> {
                item(key = "result") {
                    ResultCard(scores = scores, dominant = dominant, total = questions.size)
                }
                items(dominant, key = { "profile_${it.name}" }) { dosha -> DoshaProfileCard(dosha) }
                item(key = "result_actions") {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        TridoshaButton(
                            text = "Retake",
                            onClick = {
                                answers = emptyList()
                                started = true
                            },
                        )
                        TridoshaButton(text = "Done", onClick = onBack, tonal = true)
                    }
                }
            }
            started -> {
                item(key = "question") {
                    QuestionCard(
                        step = answers.size,
                        total = questions.size,
                        question = questions[answers.size],
                        onAnswer = { optionIndex -> answers = answers + optionIndex },
                        onPrevious = { answers = answers.dropLast(1) },
                    )
                }
            }
            else -> {
                item(key = "intro") {
                    IntroCard(
                        lastDoshas = lastDoshas,
                        questionCount = questions.size,
                        onStart = {
                            answers = emptyList()
                            started = true
                        },
                    )
                }
            }
        }
        item(key = "disclaimer") { DisclaimerCard() }
    }
}

@Composable
private fun IntroCard(lastDoshas: List<Dosha>, questionCount: Int, onStart: () -> Unit) {
    val colors = AppTheme.colors
    TridoshaCard(shape = RoundedCornerShape(26.dp), contentPadding = PaddingValues(22.dp)) {
        Text(
            text = "A moment of reflection",
            style = MaterialTheme.typography.titleLarge,
            color = colors.ink,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Answer $questionCount short questions about how you usually feel. There are no right or " +
                "wrong answers. Pick whichever sounds most like you most of the time.",
            style = MaterialTheme.typography.bodyMedium,
            color = colors.ink,
        )
        if (lastDoshas.isNotEmpty()) {
            Spacer(Modifier.height(16.dp))
            SectionLabel("Your last reflection")
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                lastDoshas.forEach { DoshaChip(dosha = it) }
            }
        }
        Spacer(Modifier.height(18.dp))
        TridoshaButton(
            text = if (lastDoshas.isEmpty()) "Begin reflection" else "Reflect again",
            onClick = onStart,
        )
    }
}

@Composable
private fun QuestionCard(
    step: Int,
    total: Int,
    question: ReflectionQuestion,
    onAnswer: (Int) -> Unit,
    onPrevious: () -> Unit,
) {
    val colors = AppTheme.colors
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        SectionLabel("Question ${step + 1} of $total")
        LinearProgressIndicator(
            progress = { (step + 1) / total.toFloat() },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(CircleShape),
            color = colors.deepSage,
            trackColor = colors.sageSoft,
        )
        Text(
            text = question.text,
            style = MaterialTheme.typography.titleLarge,
            color = colors.ink,
        )
        question.options.forEachIndexed { optionIndex, option ->
            TridoshaCard(onClick = { onAnswer(optionIndex) }) {
                Text(
                    text = option.text,
                    style = MaterialTheme.typography.bodyLarge,
                    color = colors.ink,
                )
            }
        }
        if (step > 0) {
            TextButton(
                onClick = onPrevious,
                colors = ButtonDefaults.textButtonColors(contentColor = colors.ink),
            ) {
                Text(text = "Previous question", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

@Composable
private fun ResultCard(scores: Map<Dosha, Int>, dominant: List<Dosha>, total: Int) {
    val colors = AppTheme.colors
    TridoshaCard(
        containerColor = colors.deepSage,
        shape = RoundedCornerShape(26.dp),
        border = null,
        contentPadding = PaddingValues(22.dp),
    ) {
        Text(
            text = "YOUR REFLECTION",
            style = MaterialTheme.typography.labelMedium,
            color = colors.onDeepSage.copy(alpha = 0.8f),
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = if (dominant.size == 1) {
                "Mostly ${dominant.first().displayName}"
            } else {
                "A blend of ${ReflectionData.describe(dominant)}"
            },
            style = MaterialTheme.typography.headlineMedium,
            color = colors.onDeepSage,
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = "This describes how you have been feeling lately. It can shift with seasons and life.",
            style = MaterialTheme.typography.bodyMedium,
            color = colors.onDeepSage.copy(alpha = 0.9f),
        )
        Spacer(Modifier.height(18.dp))
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Dosha.entries.forEach { dosha ->
                ScoreRow(dosha = dosha, count = scores[dosha] ?: 0, total = total)
            }
        }
    }
}

@Composable
private fun ScoreRow(dosha: Dosha, count: Int, total: Int) {
    val colors = AppTheme.colors
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = dosha.displayName,
            style = MaterialTheme.typography.labelLarge,
            color = colors.onDeepSage,
            modifier = Modifier.width(56.dp),
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .height(10.dp)
                .clip(CircleShape)
                .background(colors.onDeepSage.copy(alpha = 0.2f)),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(count / total.toFloat())
                    .fillMaxHeight()
                    .background(dosha.accentColor(), CircleShape),
            )
        }
        Text(
            text = "$count/$total",
            style = MaterialTheme.typography.labelMedium,
            color = colors.onDeepSage,
            modifier = Modifier.width(36.dp),
        )
    }
}

@Composable
private fun DoshaProfileCard(dosha: Dosha) {
    val colors = AppTheme.colors
    TridoshaCard(containerColor = dosha.softColor(), border = null) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            EmojiBadge(emoji = dosha.emoji, background = colors.cardBackground, size = 48.dp)
            Column {
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
        }
        Spacer(Modifier.height(10.dp))
        Text(
            text = dosha.tagline,
            style = MaterialTheme.typography.bodyMedium,
            color = colors.ink,
        )
        Spacer(Modifier.height(14.dp))
        ProfileSection("When in balance", dosha.inBalance, dosha)
        Spacer(Modifier.height(14.dp))
        ProfileSection("When out of balance", dosha.outOfBalance, dosha)
        Spacer(Modifier.height(14.dp))
        ProfileSection("Ways to support balance", dosha.supportTips, dosha)
    }
}

@Composable
private fun ProfileSection(label: String, items: List<String>, dosha: Dosha) {
    SectionLabel(label)
    Spacer(Modifier.height(8.dp))
    BulletList(items = items, accent = dosha.accentColor())
}

// ───────────────────────────── About ─────────────────────────────

@Suppress("DEPRECATION")
@Composable
internal fun AboutScreen(padding: PaddingValues, onBack: () -> Unit) {
    val colors = AppTheme.colors
    val context = LocalContext.current
    val version = remember {
        runCatching { context.packageManager.getPackageInfo(context.packageName, 0).versionName }
            .getOrNull() ?: "1.0.0"
    }
    ScreenList(padding) {
        item(key = "header") { DetailHeader(title = "About", onBack = onBack) }
        item(key = "hero") {
            TridoshaCard(
                containerColor = colors.deepSage,
                shape = RoundedCornerShape(26.dp),
                border = null,
                contentPadding = PaddingValues(24.dp),
            ) {
                Text(text = "🪷", style = MaterialTheme.typography.headlineLarge)
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Tridosha Balance",
                    style = MaterialTheme.typography.headlineMedium,
                    color = colors.onDeepSage,
                )
                Text(
                    text = "Version $version",
                    style = MaterialTheme.typography.labelMedium,
                    color = colors.onDeepSage.copy(alpha = 0.8f),
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    text = "A calm companion for exploring daily routines, mindful eating and the three doshas.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.onDeepSage.copy(alpha = 0.9f),
                )
            }
        }
        item(key = "ayurveda") {
            TridoshaCard {
                Text(
                    text = "What is Ayurveda?",
                    style = MaterialTheme.typography.titleLarge,
                    color = colors.ink,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Ayurveda is a traditional system of wellness from India. It describes health as " +
                        "a balance between body, mind and environment, and offers everyday habits around " +
                        "food, rest, movement and mindfulness. Its ideas are centuries old, and many of " +
                        "them are simple, gentle routines you can try for yourself.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.ink,
                )
            }
        }
        item(key = "doshas_label") { SectionLabel("The three doshas") }
        items(Dosha.entries, key = { "about_${it.name}" }) { dosha ->
            TridoshaCard(containerColor = dosha.softColor(), border = null) {
                DoshaChip(dosha = dosha, label = "${dosha.displayName} · ${dosha.element}")
                Spacer(Modifier.height(8.dp))
                Text(
                    text = dosha.tagline,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.ink,
                )
            }
        }
        item(key = "how_to") {
            TridoshaCard {
                Text(
                    text = "Using this app",
                    style = MaterialTheme.typography.titleLarge,
                    color = colors.ink,
                )
                Spacer(Modifier.height(10.dp))
                BulletList(
                    items = listOf(
                        "Pause with the breathing guide on Home.",
                        "Browse the Routine tab for a gentle rhythm through the day.",
                        "Explore foods by category in the Food Guide.",
                        "Reflect on your tendencies in My Dosha.",
                    ),
                    accent = colors.deepSage,
                )
            }
        }
        item(key = "disclaimer") { DisclaimerCard() }
    }
}

// ───────────────────────────── Settings ─────────────────────────────

@Composable
internal fun SettingsScreen(
    padding: PaddingValues,
    remindersEnabled: Boolean,
    animationsEnabled: Boolean,
    hasReflection: Boolean,
    onRemindersChange: (Boolean) -> Unit,
    onAnimationsChange: (Boolean) -> Unit,
    onClearReflection: () -> Unit,
    onBack: () -> Unit,
) {
    ScreenList(padding) {
        item(key = "header") {
            DetailHeader(
                title = "Settings",
                onBack = onBack,
                subtitle = "Saved automatically on this device",
            )
        }
        item(key = "reminders") {
            SettingSwitchRow(
                title = "Gentle reminders",
                description = "Show a time-of-day suggestion from your routine at the top of Home.",
                checked = remindersEnabled,
                onCheckedChange = onRemindersChange,
            )
        }
        item(key = "animations") {
            SettingSwitchRow(
                title = "Animations",
                description = "Breathing guide, expanding cards and screen transitions. Turn off for a still, quiet experience.",
                checked = animationsEnabled,
                onCheckedChange = onAnimationsChange,
            )
        }
        if (hasReflection) {
            item(key = "clear") {
                TridoshaCard {
                    Text(
                        text = "Saved reflection",
                        style = MaterialTheme.typography.titleMedium,
                        color = AppTheme.colors.ink,
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = "Remove the result of your last My Dosha reflection from this device.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = AppTheme.colors.muted,
                    )
                    Spacer(Modifier.height(12.dp))
                    TridoshaButton(text = "Clear result", onClick = onClearReflection, tonal = true)
                }
            }
        }
    }
}

@Composable
private fun SettingSwitchRow(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    val colors = AppTheme.colors
    TridoshaCard {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .toggleable(value = checked, role = Role.Switch, onValueChange = onCheckedChange),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, style = MaterialTheme.typography.titleMedium, color = colors.ink)
                Spacer(Modifier.height(2.dp))
                Text(text = description, style = MaterialTheme.typography.bodyMedium, color = colors.muted)
            }
            Switch(
                checked = checked,
                onCheckedChange = null,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = colors.onDeepSage,
                    checkedTrackColor = colors.deepSage,
                    checkedBorderColor = colors.deepSage,
                    uncheckedThumbColor = colors.muted,
                    uncheckedTrackColor = colors.sageSoft,
                    uncheckedBorderColor = colors.outline,
                ),
            )
        }
    }
}
