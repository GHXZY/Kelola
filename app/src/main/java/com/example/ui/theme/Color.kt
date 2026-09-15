package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// =========================================================================
// OCEANIC MODERNITY DESIGN SYSTEM TOKENS
// High-contrast, stately editorial palette balancing marine depths and atmosphere.
// =========================================================================

// Brand Tokens (Oceanic Modernity)
val BrandDeep = Color(0xFF006199)          // Primary Ocean Blue (Calls-to-action, headers, active states)
val BrandPrimary = Color(0xFF004974)       // Deep Primary Anchor (#004974)
val BrandSky = Color(0xFF8ACFF8)           // Vibrant Ice Blue (Illumination, highlights, focus halos)
val BrandNavy = Color(0xFF002B47)          // Abyssal Navy (Editorial subheadings, high contrast)
val BrandTertiary = Color(0xFF264865)      // Slate Marine (#264865)
val BrandContainer = Color(0xFF8FD4FE)     // Secondary Container (#8FD4FE)

val GradientBrand = Brush.linearGradient(
    colors = listOf(BrandPrimary, BrandDeep),
    start = Offset(0f, 0f),
    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
)

val GradientBrandVertical = Brush.verticalGradient(
    colors = listOf(BrandDeep, BrandPrimary)
)

val GradientChartFill = Brush.verticalGradient(
    colors = listOf(BrandSky.copy(alpha = 0.85f), BrandDeep.copy(alpha = 0.05f))
)

// Light Mode Tokens (Oceanic Modernity: Crisp Editorial)
val LightBgBase = Color(0xFFF7F9FF)        // background / surface #f7f9ff
val LightBgSurface = Color(0xFFFFFFFF)     // surface-container-lowest #ffffff (Pure White Cards)
val LightBgSurface2 = Color(0xFFEEF4FE)    // surface-container-low #eef4fe
val LightBgSurface3 = Color(0xFFE9EEF8)    // surface-container #e9eef8
val LightBgSurfaceHighest = Color(0xFFDDE3EC) // surface-container-highest #dde3ec
val LightTextPrimary = Color(0xFF161C23)   // on-surface #161c23 (Rich Slate Black #1E242B)
val LightTextSecondary = Color(0xFF414750) // on-surface-variant #414750
val LightTextTertiary = Color(0xFF717881)  // outline #717881
val LightBorder = Color(0xFFE2E8F0)        // 1px border of #E2E8F0 (Surface Tier 1)
val LightBorderSubtle = Color(0xFFF1F5F9)  // subtle divider #f1f5f9
val LightBorderHover = Color(0xFF8ACFF8)   // Surface Tier 2 interactive border
val LightPositive = Color(0xFF1E9E6B)
val LightNegative = Color(0xFFBA1A1A)      // error #ba1a1a
val LightWarning = Color(0xFFC98A1A)
val LightShadowSoft = Color(0x0E002B47)    // diffuse ambient shadow: rgba(0, 43, 71, 0.05)

// Dark Mode Tokens (Deep Bluish Base, Brand stays radiant)
val DarkBgBase = Color(0xFF0C141B)
val DarkBgSurface = Color(0xFF121B24)
val DarkBgSurface2 = Color(0xFF192531)
val DarkBgSurfaceHighest = Color(0xFF2B3138) // inverse-surface #2b3138
val DarkTextPrimary = Color(0xFFECF1FB)    // inverse-on-surface #ecf1fb
val DarkTextSecondary = Color(0xFFC0C7D1)  // outline-variant #c0c7d1
val DarkTextTertiary = Color(0xFF717881)   // outline #717881
val DarkBorder = Color(0xFF263542)
val DarkBorderSubtle = Color(0xFF1C2731)
val DarkBorderHover = Color(0xFF8ACFF8)
val DarkPositive = Color(0xFF4FCB93)
val DarkNegative = Color(0xFFFFB4AB)
val DarkWarning = Color(0xFFE3A83B)
val DarkShadowSoft = Color(0x66000000)

// Compatibility Aliases for smoothly upgrading all existing screens
// Dynamic getters guarantee perfect readability in both Light and Dark mode
val PrimaryBlue: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) BrandSky else BrandDeep

val PrimaryBlueDark: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) BrandSky else Color(0xFF00497D)

val PrimaryBlueLight: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) Color(0xFFB5E3FA) else BrandSky

val PrimaryBlueContainer: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) DarkBgSurface2 else LightBgSurface2

val OnPrimaryBlueContainer: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) BrandSky else BrandDeep

val SecondaryTeal: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) BrandSky else BrandDeep

val SecondaryTealContainer: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) DarkBgSurface2 else LightBgSurface2

val SecondarySlate: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) DarkTextSecondary else LightTextSecondary

val SecondarySlateContainer: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) DarkBgSurface2 else LightBgSurface2

// Neutral & Background Colors
val BackgroundLight: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) DarkBgBase else LightBgBase

val SurfaceLight: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) DarkBgSurface else LightBgSurface

val SurfaceVariantLight: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) DarkBgSurface2 else LightBgSurface2

val BorderLight: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) DarkBorder else LightBorder

val BorderLightSubtle: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) DarkBorderSubtle else LightBorderSubtle

val TextPrimary: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) DarkTextPrimary else LightTextPrimary

val TextSecondary: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) DarkTextSecondary else LightTextSecondary

val TextMuted: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) DarkTextTertiary else LightTextTertiary

// Semantic Status Colors
val SuccessGreen: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) DarkPositive else LightPositive

val SuccessDot: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) DarkPositive else LightPositive

val SuccessContainer: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) Color(0xFF0E3826) else Color(0xFFE2F7ED)

val WarningAmber: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) DarkWarning else LightWarning

val WarningContainer: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) Color(0xFF3D2A08) else Color(0xFFFFF4D9)

val DangerRed: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) DarkNegative else LightNegative

val DangerContainer: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) Color(0xFF4A1010) else Color(0xFFFFDAD6)

// Dark Theme Colors
val BackgroundDark = DarkBgBase
val SurfaceDark = DarkBgSurface
val SurfaceVariantDark = DarkBgSurface2
val BorderDark: Color = Color(0xFF263542)
val TextPrimaryDark = DarkTextPrimary
val TextSecondaryDark = DarkTextSecondary
