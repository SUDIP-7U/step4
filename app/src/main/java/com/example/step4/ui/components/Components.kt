package com.tridosha.balance.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tridosha.balance.data.model.Dosha
import com.tridosha.balance.ui.theme.AppTheme

/** Whether decorative motion (breathing, expand/collapse, screen transitions) is enabled. */
internal val LocalAnimationsEnabled = compositionLocalOf { true }

internal const val EDUCATIONAL_DISCLAIMER =
    "Tridosha Balance is for educational and general wellness purposes only. It does not provide " +
        "medical advice, diagnosis or treatment. Please consult a qualified healthcare professional " +
        "before changing your diet, routine or care, especially if you are pregnant, have a medical " +
        "condition or take medication."

@ReadOnlyComposable
@Composable
internal fun Dosha.accentColor(): Color = when (this) {
    Dosha.VATA -> AppTheme.colors.vata
    Dosha.PITTA -> AppTheme.colors.pitta
    Dosha.KAPHA -> AppTheme.colors.kapha
}

@ReadOnlyComposable
@Composable
internal fun Dosha.softColor(): Color = when (this) {
    Dosha.VATA -> AppTheme.colors.vataSoft
    Dosha.PITTA -> AppTheme.colors.pittaSoft
    Dosha.KAPHA -> AppTheme.colors.kaphaSoft
}

/** Rounded, softly elevated card; clickable when [onClick] is provided. */
@Composable
internal fun TridoshaCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    containerColor: Color = AppTheme.colors.cardBackground,
    shape: Shape = RoundedCornerShape(20.dp),
    border: BorderStroke? = BorderStroke(1.dp, AppTheme.colors.outline),
    contentPadding: PaddingValues = PaddingValues(18.dp),
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = CardDefaults.cardColors(
        containerColor = containerColor,
        contentColor = AppTheme.colors.ink,
    )
    val elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    val body: @Composable ColumnScope.() -> Unit = {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding),
            content = content,
        )
    }
    if (onClick != null) {
        Card(
            onClick = onClick,
            modifier = modifier,
            shape = shape,
            colors = colors,
            elevation = elevation,
            border = border,
            content = body,
        )
    } else {
        Card(
            modifier = modifier,
            shape = shape,
            colors = colors,
            elevation = elevation,
            border = border,
            content = body,
        )
    }
}

@Composable
internal fun EmojiBadge(
    emoji: String,
    modifier: Modifier = Modifier,
    background: Color = AppTheme.colors.sageSoft,
    size: Dp = 44.dp,
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(background),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = emoji, fontSize = (size.value * 0.5f).sp)
    }
}

@Composable
internal fun ScreenTitle(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
) {
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            color = AppTheme.colors.ink,
        )
        if (subtitle != null) {
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyLarge,
                color = AppTheme.colors.muted,
            )
        }
    }
}

@Composable
internal fun DetailHeader(
    title: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = AppTheme.colors.ink,
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                color = AppTheme.colors.ink,
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppTheme.colors.muted,
                )
            }
        }
    }
}

@Composable
internal fun SectionLabel(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text.uppercase(),
        modifier = modifier,
        style = MaterialTheme.typography.labelMedium,
        color = AppTheme.colors.muted,
    )
}

@Composable
internal fun DoshaChip(
    dosha: Dosha,
    modifier: Modifier = Modifier,
    label: String = dosha.displayName,
) {
    Row(
        modifier = modifier
            .clip(CircleShape)
            .background(dosha.softColor())
            .padding(horizontal = 10.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(dosha.accentColor(), CircleShape),
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = AppTheme.colors.ink,
        )
    }
}

@Composable
internal fun BulletList(
    items: List<String>,
    modifier: Modifier = Modifier,
    accent: Color = AppTheme.colors.sage,
) {
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items.forEach { item ->
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Box(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .size(6.dp)
                        .background(accent, CircleShape),
                )
                Text(
                    text = item,
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppTheme.colors.ink,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
internal fun DisclaimerCard(
    modifier: Modifier = Modifier,
    text: String = EDUCATIONAL_DISCLAIMER,
) {
    TridoshaCard(
        modifier = modifier,
        containerColor = AppTheme.colors.sageSoft,
        border = null,
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(text = "🌿", fontSize = 20.sp)
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Educational use only",
                    style = MaterialTheme.typography.labelLarge,
                    color = AppTheme.colors.ink,
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppTheme.colors.muted,
                )
            }
        }
    }
}

/** Standard scrolling list for a screen; applies the Scaffold [padding] plus consistent gutters. */
@Composable
internal fun ScreenList(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    content: LazyListScope.() -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 20.dp,
            end = 20.dp,
            top = padding.calculateTopPadding() + 12.dp,
            bottom = padding.calculateBottomPadding() + 24.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        content = content,
    )
}

/** Primary (filled) or tonal button in the app's palette. */
@Composable
internal fun TridoshaButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tonal: Boolean = false,
) {
    val colors = AppTheme.colors
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (tonal) colors.sageSoft else colors.deepSage,
            contentColor = if (tonal) colors.ink else colors.onDeepSage,
        ),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
    ) {
        Text(text = text, style = MaterialTheme.typography.labelLarge)
    }
}

/** Chevron that rotates when [expanded] changes (instantly when animations are off). */
@Composable
internal fun ExpandChevron(expanded: Boolean, modifier: Modifier = Modifier) {
    val animate = LocalAnimationsEnabled.current
    val rotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        animationSpec = if (animate) tween(250) else snap(),
        label = "chevron",
    )
    Icon(
        imageVector = Icons.Filled.KeyboardArrowDown,
        contentDescription = if (expanded) "Collapse" else "Expand",
        tint = AppTheme.colors.muted,
        modifier = modifier.rotate(rotation),
    )
}

/** Expand/collapse container that respects the animations setting. */
@Composable
internal fun ExpandSection(
    visible: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable AnimatedVisibilityScope.() -> Unit,
) {
    val animate = LocalAnimationsEnabled.current
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = if (animate) expandVertically() + fadeIn() else EnterTransition.None,
        exit = if (animate) shrinkVertically() + fadeOut() else ExitTransition.None,
        content = content,
    )
}
