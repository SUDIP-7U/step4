package com.example.step4.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val LightColorScheme = lightColorScheme(
    primary = LightTridoshaColors.deepSage,
    onPrimary = LightTridoshaColors.onDeepSage,
    primaryContainer = LightTridoshaColors.sageSoft,
    onPrimaryContainer = LightTridoshaColors.ink,
    secondary = LightTridoshaColors.sage,
    onSecondary = LightTridoshaColors.ink,
    secondaryContainer = LightTridoshaColors.sageSoft,
    onSecondaryContainer = LightTridoshaColors.ink,
    tertiary = LightTridoshaColors.kapha,
    onTertiary = LightTridoshaColors.ink,
    background = LightTridoshaColors.cream,
    onBackground = LightTridoshaColors.ink,
    surface = LightTridoshaColors.cardBackground,
    onSurface = LightTridoshaColors.ink,
    surfaceVariant = LightTridoshaColors.sageSoft,
    onSurfaceVariant = LightTridoshaColors.muted,
    outline = LightTridoshaColors.outline,
    outlineVariant = LightTridoshaColors.outline,
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkTridoshaColors.deepSage,
    onPrimary = DarkTridoshaColors.onDeepSage,
    primaryContainer = DarkTridoshaColors.sageSoft,
    onPrimaryContainer = DarkTridoshaColors.ink,
    secondary = DarkTridoshaColors.sage,
    onSecondary = DarkTridoshaColors.ink,
    secondaryContainer = DarkTridoshaColors.sageSoft,
    onSecondaryContainer = DarkTridoshaColors.ink,
    tertiary = DarkTridoshaColors.kapha,
    onTertiary = DarkTridoshaColors.ink,
    background = DarkTridoshaColors.cream,
    onBackground = DarkTridoshaColors.ink,
    surface = DarkTridoshaColors.cardBackground,
    onSurface = DarkTridoshaColors.ink,
    surfaceVariant = DarkTridoshaColors.sageSoft,
    onSurfaceVariant = DarkTridoshaColors.muted,
    outline = DarkTridoshaColors.outline,
    outlineVariant = DarkTridoshaColors.outline,
)

private val TridoshaTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.1.sp,
    ),
    bodyLarge = TextStyle(fontSize = 16.sp, lineHeight = 24.sp),
    bodyMedium = TextStyle(fontSize = 14.sp, lineHeight = 22.sp),
    labelLarge = TextStyle(fontWeight = FontWeight.Medium, fontSize = 14.sp, lineHeight = 20.sp),
    labelMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.8.sp,
    ),
)

private val TridoshaShapes = Shapes(
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(20.dp),
    large = RoundedCornerShape(26.dp),
)

private val LocalTridoshaColors = staticCompositionLocalOf { LightTridoshaColors }

/** Access point for the app's semantic palette: `AppTheme.colors.cream`, `AppTheme.colors.ink`, … */
internal object AppTheme {
    val colors: TridoshaColors
        @Composable
        @ReadOnlyComposable
        get() = LocalTridoshaColors.current
}

@Composable
internal fun TridoshaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val palette = if (darkTheme) DarkTridoshaColors else LightTridoshaColors
    val scheme = if (darkTheme) DarkColorScheme else LightColorScheme
    CompositionLocalProvider(LocalTridoshaColors provides palette) {
        MaterialTheme(
            colorScheme = scheme,
            typography = TridoshaTypography,
            shapes = TridoshaShapes,
            content = content,
        )
    }
}
