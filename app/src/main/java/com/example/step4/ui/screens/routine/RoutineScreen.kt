package com.example.step4.ui.screens.routine


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.step4.data.content.RoutineData
import com.example.step4.data.model.RoutinePeriod
import com.example.step4.ui.components.BulletList
import com.example.step4.ui.components.DoshaChip
import com.example.step4.ui.components.EmojiBadge
import com.example.step4.ui.components.ExpandChevron
import com.example.step4.ui.components.ExpandSection
import com.example.step4.ui.components.ScreenList
import com.example.step4.ui.components.ScreenTitle
import com.example.step4.ui.components.SectionLabel
import com.example.step4.ui.components.TridoshaCard
import com.example.step4.ui.theme.AppTheme
import java.time.LocalDateTime

@Composable
internal fun RoutineScreen(padding: PaddingValues) {
    val currentId = remember { RoutineData.periodForHour(LocalDateTime.now().hour).id }
    ScreenList(padding) {
        item(key = "title") {
            ScreenTitle(
                title = "Daily Routine",
                subtitle = "Dinacharya: gentle rhythms for every part of the day.",
            )
        }
        items(RoutineData.periods, { it.id }) { period ->
            RoutineCard(period = period, isNow = period.id == currentId)
        }
        item(key = "note") {
            Text(
                text = "These are traditional guidelines. Adapt them to your own life, and begin with just one or two.",
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.colors.muted,
                modifier = Modifier.padding(horizontal = 4.dp),
            )
        }
    }
}

@Composable
private fun RoutineCard(period: RoutinePeriod, isNow: Boolean) {
    val colors = AppTheme.colors
    var expanded by rememberSaveable(period.id) { mutableStateOf(isNow) }
    TridoshaCard(onClick = { expanded = !expanded }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            EmojiBadge(emoji = period.emoji, size = 48.dp)
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = period.title,
                        style = MaterialTheme.typography.titleLarge,
                        color = colors.ink,
                    )
                    if (isNow) NowPill()
                }
                Text(
                    text = period.timeRange,
                    style = MaterialTheme.typography.labelMedium,
                    color = colors.muted,
                )
            }
            ExpandChevron(expanded = expanded)
        }
        Spacer(Modifier.height(10.dp))
        Text(
            text = period.summary,
            style = MaterialTheme.typography.bodyMedium,
            color = colors.ink,
        )
        Spacer(Modifier.height(10.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            period.doshas.forEach { dosha -> DoshaChip(dosha = dosha) }
        }
        ExpandSection(visible = expanded) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(Modifier.height(14.dp))
                SectionLabel("Gentle tips")
                Spacer(Modifier.height(8.dp))
                BulletList(items = period.tips)
            }
        }
    }
}

@Composable
private fun NowPill() {
    Text(
        text = "NOW",
        style = MaterialTheme.typography.labelMedium,
        color = AppTheme.colors.onDeepSage,
        modifier = Modifier.background(AppTheme.colors.deepSage, CircleShape)
            .padding(horizontal = 8.dp, vertical = 2.dp),
    )
}
