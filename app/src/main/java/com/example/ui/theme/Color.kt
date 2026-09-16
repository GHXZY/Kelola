package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// =========================================================================
// COLOR THEME ENUM & PALETTE DEFINITIONS
// 4 Distinct Personalities:
// 1. DEFAULT (Oceanic Modernity - Blue)
// 2. PINK (Blush Blossom - Floral Coral Rose)
// 3. COKLAT (Terra & Flora - Earth Clay & Peat)
// 4. ORANGE (Solar Flare - Warm Gold & Terracotta)
// =========================================================================

enum class ColorTheme(val key: String, val displayName: String, val previewColor: Color) {
    DEFAULT("DEFAULT", "Biru", Color(0xFF006199)),
    PINK("PINK", "Pink", Color(0xFFFF788D)),
    COKLAT("COKLAT", "Coklat", Color(0xFF8A5F41)),
    ORANGE("ORANGE", "Orange", Color(0xFFFCAD38));

    companion object {
        fun fromKey(key: String): ColorTheme {
            return entries.firstOrNull { it.key.equals(key, ignoreCase = true) } ?: DEFAULT
        }
    }
}

val LocalColorTheme = compositionLocalOf { ColorTheme.DEFAULT }

@Composable
@ReadOnlyComposable
fun currentAppColorTheme(): ColorTheme {
    return LocalColorTheme.current
}

data class KelolaColorPalette(
    // Brand Tokens
    val brandDeep: Color,
    val brandPrimary: Color,
    val brandSky: Color,
    val brandNavy: Color,
    val brandTertiary: Color,
    val brandContainer: Color,

    // Light Mode Tokens
    val lightBgBase: Color,
    val lightBgSurface: Color,
    val lightBgSurface2: Color,
    val lightBgSurface3: Color,
    val lightBgSurfaceHighest: Color,
    val lightTextPrimary: Color,
    val lightTextSecondary: Color,
    val lightTextTertiary: Color,
    val lightBorder: Color,
    val lightBorderSubtle: Color,
    val lightBorderHover: Color,
    val lightPositive: Color,
    val lightNegative: Color,
    val lightWarning: Color,
    val lightShadowSoft: Color,

    // Dark Mode Tokens
    val darkBgBase: Color,
    val darkBgSurface: Color,
    val darkBgSurface2: Color,
    val darkBgSurfaceHighest: Color,
    val darkTextPrimary: Color,
    val darkTextSecondary: Color,
    val darkTextTertiary: Color,
    val darkBorder: Color,
    val darkBorderSubtle: Color,
    val darkBorderHover: Color,
    val darkPositive: Color,
    val darkNegative: Color,
    val darkWarning: Color,
    val darkShadowSoft: Color
)

// 1. DEFAULT: Oceanic Modernity (DESIGN (1).md)
val OceanicPalette = KelolaColorPalette(
    brandDeep = Color(0xFF006199),
    brandPrimary = Color(0xFF004974),
    brandSky = Color(0xFF8ACFF8),
    brandNavy = Color(0xFF002B47),
    brandTertiary = Color(0xFF264865),
    brandContainer = Color(0xFF8FD4FE),

    lightBgBase = Color(0xFFF7F9FF),
    lightBgSurface = Color(0xFFFFFFFF),
    lightBgSurface2 = Color(0xFFEEF4FE),
    lightBgSurface3 = Color(0xFFE9EEF8),
    lightBgSurfaceHighest = Color(0xFFDDE3EC),
    lightTextPrimary = Color(0xFF161C23),
    lightTextSecondary = Color(0xFF414750),
    lightTextTertiary = Color(0xFF717881),
    lightBorder = Color(0xFFE2E8F0),
    lightBorderSubtle = Color(0xFFF1F5F9),
    lightBorderHover = Color(0xFF8ACFF8),
    lightPositive = Color(0xFF1E9E6B),
    lightNegative = Color(0xFFBA1A1A),
    lightWarning = Color(0xFFC98A1A),
    lightShadowSoft = Color(0x0E002B47),

    darkBgBase = Color(0xFF0C141B),
    darkBgSurface = Color(0xFF121B24),
    darkBgSurface2 = Color(0xFF192531),
    darkBgSurfaceHighest = Color(0xFF2B3138),
    darkTextPrimary = Color(0xFFECF1FB),
    darkTextSecondary = Color(0xFFC0C7D1),
    darkTextTertiary = Color(0xFF717881),
    darkBorder = Color(0xFF263542),
    darkBorderSubtle = Color(0xFF1C2731),
    darkBorderHover = Color(0xFF8ACFF8),
    darkPositive = Color(0xFF4FCB93),
    darkNegative = Color(0xFFFFB4AB),
    darkWarning = Color(0xFFE3A83B),
    darkShadowSoft = Color(0x66000000)
)

// 2. PINK: Blush Blossom (Pink Light.md & Pink Dark.md)
val BlushPalette = KelolaColorPalette(
    brandDeep = Color(0xFFA7354B),
    brandPrimary = Color(0xFF871C35),
    brandSky = Color(0xFFFF788D),
    brandNavy = Color(0xFF5A2834),
    brandTertiary = Color(0xFF874D5A),
    brandContainer = Color(0xFFFFDADA),

    lightBgBase = Color(0xFFFFF8F8),
    lightBgSurface = Color(0xFFFFFFFF),
    lightBgSurface2 = Color(0xFFFFF0F3),
    lightBgSurface3 = Color(0xFFFCE9ED),
    lightBgSurfaceHighest = Color(0xFFF0DEE2),
    lightTextPrimary = Color(0xFF22191C),
    lightTextSecondary = Color(0xFF574143),
    lightTextTertiary = Color(0xFF8A7173),
    lightBorder = Color(0xFFDDBFC1),
    lightBorderSubtle = Color(0xFFF6E4E8),
    lightBorderHover = Color(0xFFFF788D),
    lightPositive = Color(0xFF1E9E6B),
    lightNegative = Color(0xFFBA1A1A),
    lightWarning = Color(0xFFC98A1A),
    lightShadowSoft = Color(0x0E5A2834),

    darkBgBase = Color(0xFF1A1114),
    darkBgSurface = Color(0xFF22191C),
    darkBgSurface2 = Color(0xFF271D20),
    darkBgSurfaceHighest = Color(0xFF3D3235),
    darkTextPrimary = Color(0xFFF0DEE2),
    darkTextSecondary = Color(0xFFDDBFC1),
    darkTextTertiary = Color(0xFFA58A8C),
    darkBorder = Color(0xFF574143),
    darkBorderSubtle = Color(0xFF32272A),
    darkBorderHover = Color(0xFFFF788D),
    darkPositive = Color(0xFF4FCB93),
    darkNegative = Color(0xFFFFB4AB),
    darkWarning = Color(0xFFE3A83B),
    darkShadowSoft = Color(0x66000000)
)

// 3. COKLAT: Terra & Flora (Coklat Light.md & Coklat drak.md)
val TerraPalette = KelolaColorPalette(
    brandDeep = Color(0xFF8A5F41),
    brandPrimary = Color(0xFF6F472B),
    brandSky = Color(0xFFD88B57),
    brandNavy = Color(0xFF311300),
    brandTertiary = Color(0xFF5A6334),
    brandContainer = Color(0xFFDEE8AD),

    lightBgBase = Color(0xFFFDF9F2),
    lightBgSurface = Color(0xFFFFFFFF),
    lightBgSurface2 = Color(0xFFF7F3ED),
    lightBgSurface3 = Color(0xFFF1EDE7),
    lightBgSurfaceHighest = Color(0xFFE6E2DC),
    lightTextPrimary = Color(0xFF1C1C18),
    lightTextSecondary = Color(0xFF51443D),
    lightTextTertiary = Color(0xFF83746C),
    lightBorder = Color(0xFFD5C3B9),
    lightBorderSubtle = Color(0xFFEBE8E1),
    lightBorderHover = Color(0xFF8A5F41),
    lightPositive = Color(0xFF5A6334),
    lightNegative = Color(0xFFBA1A1A),
    lightWarning = Color(0xFF7C4113),
    lightShadowSoft = Color(0x0E49280F),

    darkBgBase = Color(0xFF141310),
    darkBgSurface = Color(0xFF1C1C18),
    darkBgSurface2 = Color(0xFF20201C),
    darkBgSurfaceHighest = Color(0xFF363531),
    darkTextPrimary = Color(0xFFE6E2DC),
    darkTextSecondary = Color(0xFFD5C3B9),
    darkTextTertiary = Color(0xFF9D8E85),
    darkBorder = Color(0xFF51443D),
    darkBorderSubtle = Color(0xFF2B2A26),
    darkBorderHover = Color(0xFFF2BB97),
    darkPositive = Color(0xFFC2CC93),
    darkNegative = Color(0xFFFFB4AB),
    darkWarning = Color(0xFFFFB688),
    darkShadowSoft = Color(0x66000000)
)

// 4. ORANGE: Solar Flare (Orenge light.md & Orange dark.md)
val SolarPalette = KelolaColorPalette(
    brandDeep = Color(0xFF845400),
    brandPrimary = Color(0xFF643F00),
    brandSky = Color(0xFFFCAD38),
    brandNavy = Color(0xFF2A1800),
    brandTertiary = Color(0xFFAE3020),
    brandContainer = Color(0xFFFFDDB6),

    lightBgBase = Color(0xFFFFF8F6),
    lightBgSurface = Color(0xFFFFFFFF),
    lightBgSurface2 = Color(0xFFFBF2EF),
    lightBgSurface3 = Color(0xFFF5ECEA),
    lightBgSurfaceHighest = Color(0xFFEAE1DE),
    lightTextPrimary = Color(0xFF1F1B1A),
    lightTextSecondary = Color(0xFF524435),
    lightTextTertiary = Color(0xFF847463),
    lightBorder = Color(0xFFD7C3AF),
    lightBorderSubtle = Color(0xFFEFE6E4),
    lightBorderHover = Color(0xFFFCAD38),
    lightPositive = Color(0xFF176B4B),
    lightNegative = Color(0xFFBA1A1A),
    lightWarning = Color(0xFFAE3020),
    lightShadowSoft = Color(0x0E643F00),

    darkBgBase = Color(0xFF161312),
    darkBgSurface = Color(0xFF1F1B1A),
    darkBgSurface2 = Color(0xFF231F1E),
    darkBgSurfaceHighest = Color(0xFF383433),
    darkTextPrimary = Color(0xFFEAE1DE),
    darkTextSecondary = Color(0xFFD7C3AF),
    darkTextTertiary = Color(0xFF9F8E7B),
    darkBorder = Color(0xFF524435),
    darkBorderSubtle = Color(0xFF2D2928),
    darkBorderHover = Color(0xFFFFD199),
    darkPositive = Color(0xFF9AE9C0),
    darkNegative = Color(0xFFFFB4AB),
    darkWarning = Color(0xFFFFB4A7),
    darkShadowSoft = Color(0x66000000)
)

@Composable
@ReadOnlyComposable
fun currentPalette(): KelolaColorPalette {
    return when (LocalColorTheme.current) {
        ColorTheme.DEFAULT -> OceanicPalette
        ColorTheme.PINK -> BlushPalette
        ColorTheme.COKLAT -> TerraPalette
        ColorTheme.ORANGE -> SolarPalette
    }
}

// =========================================================================
// BRAND TOKENS (Dynamic @Composable getters that adapt to active theme)
// =========================================================================
val BrandDeep: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) {
        when (LocalColorTheme.current) {
            ColorTheme.DEFAULT -> Color(0xFF97CBFF)
            ColorTheme.PINK -> Color(0xFFFFB2BA)
            ColorTheme.COKLAT -> Color(0xFFF2BB97)
            ColorTheme.ORANGE -> Color(0xFFFFD199)
        }
    } else currentPalette().brandDeep

val BrandPrimary: Color
    @Composable
    @ReadOnlyComposable
    get() = currentPalette().brandPrimary

val BrandSky: Color
    @Composable
    @ReadOnlyComposable
    get() = currentPalette().brandSky

val BrandNavy: Color
    @Composable
    @ReadOnlyComposable
    get() = currentPalette().brandNavy

val BrandTertiary: Color
    @Composable
    @ReadOnlyComposable
    get() = currentPalette().brandTertiary

val BrandContainer: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) currentPalette().darkBgSurface2 else currentPalette().brandContainer

// Dynamic Gradients
val GradientBrand: Brush
    @Composable
    get() {
        val p = currentPalette()
        val isDark = isAppInDarkTheme()
        val startColor = if (isDark) p.brandPrimary else p.brandPrimary
        val endColor = if (isDark) {
            when (LocalColorTheme.current) {
                ColorTheme.DEFAULT -> Color(0xFF006199)
                ColorTheme.PINK -> Color(0xFFA7354B)
                ColorTheme.COKLAT -> Color(0xFF8A5F41)
                ColorTheme.ORANGE -> Color(0xFF845400)
            }
        } else p.brandDeep
        return Brush.linearGradient(
            colors = listOf(startColor, endColor),
            start = Offset(0f, 0f),
            end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
        )
    }

val GradientBrandVertical: Brush
    @Composable
    get() {
        val p = currentPalette()
        return Brush.verticalGradient(
            colors = listOf(p.brandDeep, p.brandPrimary)
        )
    }

val GradientChartFill: Brush
    @Composable
    get() {
        val p = currentPalette()
        return Brush.verticalGradient(
            colors = listOf(p.brandSky.copy(alpha = 0.85f), p.brandDeep.copy(alpha = 0.05f))
        )
    }

// Base Theme Token Values (Light & Dark)
val LightBgBase: Color @Composable @ReadOnlyComposable get() = currentPalette().lightBgBase
val LightBgSurface: Color @Composable @ReadOnlyComposable get() = currentPalette().lightBgSurface
val LightBgSurface2: Color @Composable @ReadOnlyComposable get() = currentPalette().lightBgSurface2
val LightBgSurface3: Color @Composable @ReadOnlyComposable get() = currentPalette().lightBgSurface3
val LightBgSurfaceHighest: Color @Composable @ReadOnlyComposable get() = currentPalette().lightBgSurfaceHighest
val LightTextPrimary: Color @Composable @ReadOnlyComposable get() = currentPalette().lightTextPrimary
val LightTextSecondary: Color @Composable @ReadOnlyComposable get() = currentPalette().lightTextSecondary
val LightTextTertiary: Color @Composable @ReadOnlyComposable get() = currentPalette().lightTextTertiary
val LightBorder: Color @Composable @ReadOnlyComposable get() = currentPalette().lightBorder
val LightBorderSubtle: Color @Composable @ReadOnlyComposable get() = currentPalette().lightBorderSubtle
val LightBorderHover: Color @Composable @ReadOnlyComposable get() = currentPalette().lightBorderHover
val LightPositive: Color @Composable @ReadOnlyComposable get() = currentPalette().lightPositive
val LightNegative: Color @Composable @ReadOnlyComposable get() = currentPalette().lightNegative
val LightWarning: Color @Composable @ReadOnlyComposable get() = currentPalette().lightWarning
val LightShadowSoft: Color @Composable @ReadOnlyComposable get() = currentPalette().lightShadowSoft

val DarkBgBase: Color @Composable @ReadOnlyComposable get() = currentPalette().darkBgBase
val DarkBgSurface: Color @Composable @ReadOnlyComposable get() = currentPalette().darkBgSurface
val DarkBgSurface2: Color @Composable @ReadOnlyComposable get() = currentPalette().darkBgSurface2
val DarkBgSurfaceHighest: Color @Composable @ReadOnlyComposable get() = currentPalette().darkBgSurfaceHighest
val DarkTextPrimary: Color @Composable @ReadOnlyComposable get() = currentPalette().darkTextPrimary
val DarkTextSecondary: Color @Composable @ReadOnlyComposable get() = currentPalette().darkTextSecondary
val DarkTextTertiary: Color @Composable @ReadOnlyComposable get() = currentPalette().darkTextTertiary
val DarkBorder: Color @Composable @ReadOnlyComposable get() = currentPalette().darkBorder
val DarkBorderSubtle: Color @Composable @ReadOnlyComposable get() = currentPalette().darkBorderSubtle
val DarkBorderHover: Color @Composable @ReadOnlyComposable get() = currentPalette().darkBorderHover
val DarkPositive: Color @Composable @ReadOnlyComposable get() = currentPalette().darkPositive
val DarkNegative: Color @Composable @ReadOnlyComposable get() = currentPalette().darkNegative
val DarkWarning: Color @Composable @ReadOnlyComposable get() = currentPalette().darkWarning
val DarkShadowSoft: Color @Composable @ReadOnlyComposable get() = currentPalette().darkShadowSoft

// =========================================================================
// COMPATIBILITY ALIASES (Dynamic Getters)
// Guarantee instant, seamless theme & mode switching across all screens.
// =========================================================================
val PrimaryBlue: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) {
        when (LocalColorTheme.current) {
            ColorTheme.DEFAULT -> OceanicPalette.brandSky
            ColorTheme.PINK -> BlushPalette.brandSky
            ColorTheme.COKLAT -> Color(0xFFF2BB97)
            ColorTheme.ORANGE -> Color(0xFFFFD199)
        }
    } else currentPalette().brandDeep

val PrimaryBlueDark: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) {
        when (LocalColorTheme.current) {
            ColorTheme.DEFAULT -> OceanicPalette.brandSky
            ColorTheme.PINK -> BlushPalette.brandSky
            ColorTheme.COKLAT -> Color(0xFFF2BB97)
            ColorTheme.ORANGE -> Color(0xFFFFD199)
        }
    } else currentPalette().brandPrimary

val PrimaryBlueLight: Color
    @Composable
    @ReadOnlyComposable
    get() = currentPalette().brandSky

val PrimaryBlueContainer: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) currentPalette().darkBgSurface2 else currentPalette().lightBgSurface2

val OnPrimaryBlueContainer: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) {
        when (LocalColorTheme.current) {
            ColorTheme.DEFAULT -> OceanicPalette.brandSky
            ColorTheme.PINK -> BlushPalette.brandSky
            ColorTheme.COKLAT -> Color(0xFFF2BB97)
            ColorTheme.ORANGE -> Color(0xFFFFD199)
        }
    } else currentPalette().brandDeep

val SecondaryTeal: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) {
        when (LocalColorTheme.current) {
            ColorTheme.DEFAULT -> OceanicPalette.brandSky
            ColorTheme.PINK -> BlushPalette.brandSky
            ColorTheme.COKLAT -> Color(0xFFC2CC93)
            ColorTheme.ORANGE -> Color(0xFF9AE9C0)
        }
    } else currentPalette().brandTertiary

val SecondaryTealContainer: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) currentPalette().darkBgSurface2 else currentPalette().lightBgSurface2

val SecondarySlate: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) currentPalette().darkTextSecondary else currentPalette().lightTextSecondary

val SecondarySlateContainer: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) currentPalette().darkBgSurface2 else currentPalette().lightBgSurface2

// Neutral & Background Colors
val BackgroundLight: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) currentPalette().darkBgBase else currentPalette().lightBgBase

val SurfaceLight: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) currentPalette().darkBgSurface else currentPalette().lightBgSurface

val SurfaceVariantLight: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) currentPalette().darkBgSurface2 else currentPalette().lightBgSurface2

val BorderLight: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) currentPalette().darkBorder else currentPalette().lightBorder

val BorderLightSubtle: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) currentPalette().darkBorderSubtle else currentPalette().lightBorderSubtle

val TextPrimary: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) currentPalette().darkTextPrimary else currentPalette().lightTextPrimary

val TextSecondary: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) currentPalette().darkTextSecondary else currentPalette().lightTextSecondary

val TextMuted: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) currentPalette().darkTextTertiary else currentPalette().lightTextTertiary

// Semantic Status Colors
val SuccessGreen: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) currentPalette().darkPositive else currentPalette().lightPositive

val SuccessDot: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) currentPalette().darkPositive else currentPalette().lightPositive

val SuccessContainer: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) {
        when (LocalColorTheme.current) {
            ColorTheme.DEFAULT -> Color(0xFF0E3826)
            ColorTheme.PINK -> Color(0xFF0E3826)
            ColorTheme.COKLAT -> Color(0xFF2C340A)
            ColorTheme.ORANGE -> Color(0xFF003824)
        }
    } else {
        when (LocalColorTheme.current) {
            ColorTheme.DEFAULT -> Color(0xFFE2F7ED)
            ColorTheme.PINK -> Color(0xFFE2F7ED)
            ColorTheme.COKLAT -> Color(0xFFDEE8AD)
            ColorTheme.ORANGE -> Color(0xFFE0F8EE)
        }
    }

val WarningAmber: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) currentPalette().darkWarning else currentPalette().lightWarning

val WarningContainer: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) {
        when (LocalColorTheme.current) {
            ColorTheme.DEFAULT -> Color(0xFF3D2A08)
            ColorTheme.PINK -> Color(0xFF3D2A08)
            ColorTheme.COKLAT -> Color(0xFF512400)
            ColorTheme.ORANGE -> Color(0xFF670400)
        }
    } else {
        when (LocalColorTheme.current) {
            ColorTheme.DEFAULT -> Color(0xFFFFF4D9)
            ColorTheme.PINK -> Color(0xFFFFF4D9)
            ColorTheme.COKLAT -> Color(0xFFFFE4D5)
            ColorTheme.ORANGE -> Color(0xFFFFDAD4)
        }
    }

val DangerRed: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) currentPalette().darkNegative else currentPalette().lightNegative

val DangerContainer: Color
    @Composable
    @ReadOnlyComposable
    get() = if (isAppInDarkTheme()) Color(0xFF4A1010) else Color(0xFFFFDAD6)

// Dark Theme Colors
val BackgroundDark: Color @Composable @ReadOnlyComposable get() = currentPalette().darkBgBase
val SurfaceDark: Color @Composable @ReadOnlyComposable get() = currentPalette().darkBgSurface
val SurfaceVariantDark: Color @Composable @ReadOnlyComposable get() = currentPalette().darkBgSurface2
val BorderDark: Color @Composable @ReadOnlyComposable get() = currentPalette().darkBorder
val TextPrimaryDark: Color @Composable @ReadOnlyComposable get() = currentPalette().darkTextPrimary
val TextSecondaryDark: Color @Composable @ReadOnlyComposable get() = currentPalette().darkTextSecondary
