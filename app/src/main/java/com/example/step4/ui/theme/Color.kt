package com.example.step4.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Semantic palette for the whole app. This file is the ONLY place raw color values are defined;
 * UI code reads them through [AppTheme.colors].
 */
@Immutable
internal data class TridoshaColors(
    val isDark: Boolean,
    val cream: Color,
    val cardBackground: Color,
    val ink: Color,
    val muted: Color,
    val sage: Color,
    val sageSoft: Color,
    val deepSage: Color,
    val onDeepSage: Color,
    val outline: Color,
    val vata: Color,
    val vataSoft: Color,
    val pitta: Color,
    val pittaSoft: Color,
    val kapha: Color,
    val kaphaSoft: Color,
)

internal val LightTridoshaColors = TridoshaColors(
    isDark = false,
    cream = Color(0xFFF6F2E8),
    cardBackground = Color(0xFFFFFDF8),
    ink = Color(0xFF24302A),
    muted = Color(0xFF5F6D64),
    sage = Color(0xFF8FAE8B),
    sageSoft = Color(0xFFE2EBDC),
    deepSage = Color(0xFF3F6B52),
    onDeepSage = Color(0xFFF6F2E8),
    outline = Color(0xFFDCE3D3),
    vata = Color(0xFF7C8DB5),
    vataSoft = Color(0xFFE6EAF4),
    pitta = Color(0xFFE08A5B),
    pittaSoft = Color(0xFFFBE7DB),
    kapha = Color(0xFF5E9C9A),
    kaphaSoft = Color(0xFFDDEFEC),
)

internal val DarkTridoshaColors = TridoshaColors(
    isDark = true,
    cream = Color(0xFF111915),
    cardBackground = Color(0xFF1A2520),
    ink = Color(0xFFE8EFE4),
    muted = Color(0xFFA1B0A6),
    sage = Color(0xFF6F9B7C),
    sageSoft = Color(0xFF22332A),
    deepSage = Color(0xFF3F7556),
    onDeepSage = Color(0xFFF4F8F1),
    outline = Color(0xFF2C3A32),
    vata = Color(0xFF9FB0DE),
    vataSoft = Color(0xFF232A40),
    pitta = Color(0xFFF2A57E),
    pittaSoft = Color(0xFF3F2A20),
    kapha = Color(0xFF82C7BE),
    kaphaSoft = Color(0xFF1E3834),
)
