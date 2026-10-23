package com.example.step4.ui.screens.food

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
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
import com.example.step4.data.content.FoodData
import com.example.step4.data.model.Dosha
import com.example.step4.data.model.FoodCategory
import com.example.step4.data.model.FoodItem
import com.example.step4.ui.components.BulletList
import com.example.step4.ui.components.DetailHeader
import com.example.step4.ui.components.DisclaimerCard
import com.example.step4.ui.components.DoshaChip
import com.example.step4.ui.components.EmojiBadge
import com.example.step4.ui.components.ScreenList
import com.example.step4.ui.components.ScreenTitle
import com.example.step4.ui.components.SectionLabel
import com.example.step4.ui.components.TridoshaCard
import com.example.step4.ui.theme.AppTheme

private const val FILTER_ALL = "ALL"

@Composable
internal fun FoodScreen(
    padding: PaddingValues,
    onOpenCategory: (String) -> Unit,
) {
    ScreenList(padding) {
        item(key = "title") {
            ScreenTitle(
                title = "Food Guide",
                subtitle = "Nourishment through the lens of the doshas.",
            )
        }
        item(key = "intro") { FoodIntroCard() }
        item(key = "categories_label") { SectionLabel("Explore by category") }
        items(FoodData.categories, key = { it.id }) { category ->
            FoodCategoryCard(category = category, onClick = { onOpenCategory(category.id) })
        }
    }
}

@Composable
private fun FoodIntroCard() {
    val colors = AppTheme.colors
    TridoshaCard(containerColor = colors.sageSoft, border = null) {
        Text(
            text = FoodData.INTRO_TITLE,
            style = MaterialTheme.typography.titleLarge,
            color = colors.ink,
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = FoodData.INTRO_BODY,
            style = MaterialTheme.typography.bodyMedium,
            color = colors.ink,
        )
        Spacer(Modifier.height(12.dp))
        BulletList(items = FoodData.principles, accent = colors.deepSage)
    }
}

@Composable
private fun FoodCategoryCard(category: FoodCategory, onClick: () -> Unit) {
    val colors = AppTheme.colors
    TridoshaCard(onClick = onClick) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            EmojiBadge(emoji = category.emoji, size = 52.dp)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = category.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = colors.ink,
                )
                Text(
                    text = "${category.items.size} foods to explore",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.muted,
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = colors.muted,
            )
        }
    }
}

/** Detail page for one food category, with a filter by dosha. */
@Composable
internal fun FoodCategoryScreen(
    category: FoodCategory,
    padding: PaddingValues,
    onBack: () -> Unit,
) {
    val colors = AppTheme.colors
    var filter by rememberSaveable(category.id) { mutableStateOf(FILTER_ALL) }
    val selectedDosha = remember(filter) { Dosha.entries.firstOrNull { it.name == filter } }
    val visibleItems = remember(category, selectedDosha) { FoodData.itemsFor(category, selectedDosha) }

    ScreenList(padding) {
        item(key = "header") {
            DetailHeader(
                title = category.title,
                onBack = onBack,
                subtitle = "${category.items.size} foods",
            )
        }
        item(key = "intro") {
            TridoshaCard(containerColor = colors.sageSoft, border = null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    EmojiBadge(emoji = category.emoji, background = colors.cardBackground, size = 52.dp)
                    Text(
                        text = category.intro,
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.ink,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
        item(key = "filter") {
            DoshaFilterRow(selected = filter, onSelect = { filter = it })
        }
        if (visibleItems.isEmpty()) {
            item(key = "empty") {
                Text(
                    text = "No foods in this list are especially recommended for that dosha. Try another filter.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.muted,
                )
            }
        } else {
            items(visibleItems, key = { it.name }) { food -> FoodItemCard(food) }
        }
        item(key = "guidance") {
            TridoshaCard {
                SectionLabel("Good to remember")
                Spacer(Modifier.height(10.dp))
                BulletList(items = category.guidance, accent = colors.deepSage)
            }
        }
        item(key = "disclaimer") { DisclaimerCard() }
    }
}

@Composable
private fun DoshaFilterRow(selected: String, onSelect: (String) -> Unit) {
    val options = listOf(FILTER_ALL to "All") + Dosha.entries.map { it.name to it.displayName }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        options.forEach { (key, label) ->
            DoshaFilterChip(label = label, selected = selected == key, onClick = { onSelect(key) })
        }
    }
}

@Composable
private fun DoshaFilterChip(label: String, selected: Boolean, onClick: () -> Unit) {
    val colors = AppTheme.colors
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(label) },
        leadingIcon = if (selected) {
            {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    modifier = Modifier.size(FilterChipDefaults.IconSize),
                )
            }
        } else {
            null
        },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = colors.cardBackground,
            labelColor = colors.ink,
            selectedContainerColor = colors.deepSage,
            selectedLabelColor = colors.onDeepSage,
            selectedLeadingIconColor = colors.onDeepSage,
        ),
        border = BorderStroke(1.dp, colors.outline),
    )
}

@Composable
private fun FoodItemCard(food: FoodItem) {
    val colors = AppTheme.colors
    TridoshaCard {
        Text(
            text = food.name,
            style = MaterialTheme.typography.titleMedium,
            color = colors.ink,
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = food.note,
            style = MaterialTheme.typography.bodyMedium,
            color = colors.muted,
        )
        if (food.favors.isNotEmpty()) {
            Spacer(Modifier.height(12.dp))
            SectionLabel("Especially good for")
            Spacer(Modifier.height(6.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                food.favors.forEach { DoshaChip(dosha = it) }
            }
        }
        if (food.moderate.isNotEmpty()) {
            Spacer(Modifier.height(10.dp))
            SectionLabel("Enjoy in moderation")
            Spacer(Modifier.height(6.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                food.moderate.forEach { DoshaChip(dosha = it) }
            }
        }
    }
}
