package com.example.ui.theme

import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val LocalIsDarkTheme = compositionLocalOf { false }

@Composable
@ReadOnlyComposable
fun isAppInDarkTheme(): Boolean {
    return LocalIsDarkTheme.current
}

// =========================================================================
// OCEANIC MODERNITY RADIUS TOKENS (Level 2: Rounded)
// - sm: 4dp (0.25rem)
// - DEFAULT: 8dp (0.5rem) -> Inputs, buttons, chips, form elements
// - md: 12dp (0.75rem)
// - lg: 16dp (1rem) -> Cards, modals, flyout panels
// - xl: 24dp (1.5rem)
// - full: 9999px (CircleShape)
// =========================================================================
object KelolaRadius {
    val Sm: Dp = 4.dp             // 0.25rem
    val Small: Dp = 8.dp          // 0.5rem - chip, badges
    val Input: Dp = 12.dp         // 0.75rem - inputs, buttons, form elements
    val Md: Dp = 12.dp            // 0.75rem
    val Card: Dp = 12.dp          // 0.75rem - cards, containers
    val Hero: Dp = 12.dp          // 0.75rem
    val Sheet: Dp = 16.dp         // 1rem (rounded-lg)
    val Xl: Dp = 24.dp            // 1.5rem
    val Full: Shape = CircleShape // 9999px

    val ShapeSm = RoundedCornerShape(4.dp)
    val ShapeSmall = RoundedCornerShape(8.dp)
    val ShapeChip = RoundedCornerShape(8.dp)
    val ShapeInput = RoundedCornerShape(12.dp)
    val ShapeMedium = RoundedCornerShape(12.dp)
    val ShapeCard = RoundedCornerShape(12.dp)
    val ShapeHero = RoundedCornerShape(12.dp)
    val ShapeSheet = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    val ShapeXl = RoundedCornerShape(24.dp)
}

// =========================================================================
// OCEANIC MODERNITY COLOR SCHEMES
// =========================================================================
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF97CBFF),           // inverse-primary #97cbff
    onPrimary = Color(0xFF001D33),         // on-primary-fixed #001d33
    primaryContainer = Color(0xFF004974),  // primary #004974
    onPrimaryContainer = Color(0xFFCEE5FF),
    secondary = Color(0xFF8ACFF8),         // secondary-fixed-dim #8acff8
    onSecondary = Color(0xFF001E2D),       // on-secondary-fixed #001e2d
    secondaryContainer = Color(0xFF004C69),
    onSecondaryContainer = Color(0xFFC4E7FF),
    tertiary = Color(0xFFA8CAED),          // tertiary-fixed-dim #a8caed
    onTertiary = Color(0xFF001D32),
    tertiaryContainer = Color(0xFF264865),
    onTertiaryContainer = Color(0xFFCEE5FF),
    background = Color(0xFF0C141B),
    onBackground = Color(0xFFECF1FB),
    surface = Color(0xFF121B24),
    onSurface = Color(0xFFECF1FB),         // inverse-on-surface #ecf1fb
    surfaceVariant = Color(0xFF192531),
    onSurfaceVariant = Color(0xFFC0C7D1),  // outline-variant #c0c7d1
    outline = Color(0xFF717881),           // outline #717881
    outlineVariant = Color(0xFF263542),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF006199),           // primary #006199 (Deep Ocean Blue)
    onPrimary = Color.White,               // on-primary #ffffff
    primaryContainer = Color(0xFF006199),  // primary-container #006199
    onPrimaryContainer = Color(0xFFB7D9FF),// on-primary-container #b7d9ff
    secondary = Color(0xFF0B658A),         // secondary #0b658a
    onSecondary = Color.White,             // on-secondary #ffffff
    secondaryContainer = Color(0xFF8FD4FE),// secondary-container #8fd4fe
    onSecondaryContainer = Color(0xFF005D7F),
    tertiary = Color(0xFF264865),          // tertiary #264865 / #002B47
    onTertiary = Color.White,              // on-tertiary #ffffff
    tertiaryContainer = Color(0xFF3F607E), // tertiary-container #3f607e
    onTertiaryContainer = Color(0xFFB8DAFE),
    background = Color(0xFFF7F9FF),        // background #f7f9ff
    onBackground = Color(0xFF161C23),      // on-background #161c23 (Rich slate black)
    surface = Color(0xFFFFFFFF),           // surface-container-lowest #ffffff (Pure white cards)
    onSurface = Color(0xFF161C23),         // on-surface #161c23
    surfaceVariant = Color(0xFFEEF4FE),    // surface-container-low #eef4fe
    onSurfaceVariant = Color(0xFF414750),  // on-surface-variant #414750
    outline = Color(0xFF717881),           // outline #717881
    outlineVariant = Color(0xFFE2E8F0),    // 1px border of #E2E8F0 (Surface Tier 1)
    error = Color(0xFFBA1A1A),             // error #ba1a1a
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD6),    // error-container #ffdad6
    onErrorContainer = Color(0xFF93000A)   // on-error-container #93000a
)

// =========================================================================
// EXTENSIONS & UTILITIES
// =========================================================================
object KelolaTheme {
    val brandGradient = GradientBrand

    val shadowSoft: Color
        @Composable
        @ReadOnlyComposable
        get() = if (isAppInDarkTheme()) DarkShadowSoft else LightShadowSoft

    val positive: Color
        @Composable
        @ReadOnlyComposable
        get() = if (isAppInDarkTheme()) DarkPositive else LightPositive

    val negative: Color
        @Composable
        @ReadOnlyComposable
        get() = if (isAppInDarkTheme()) DarkNegative else LightNegative

    val warning: Color
        @Composable
        @ReadOnlyComposable
        get() = if (isAppInDarkTheme()) DarkWarning else LightWarning

    val textTertiary: Color
        @Composable
        @ReadOnlyComposable
        get() = if (isAppInDarkTheme()) DarkTextTertiary else LightTextTertiary
}

@Composable
fun Modifier.kelolaSoftShadow(
    shape: Shape = KelolaRadius.ShapeCard,
    elevation: Dp = 2.dp,
    showBorder: Boolean = true
): Modifier {
    val isDark = isAppInDarkTheme()
    val shadowColor = if (isDark) DarkShadowSoft else LightShadowSoft
    val borderColor = if (isDark) DarkBorder else LightBorder
    val shadowed = this.shadow(
        elevation = elevation,
        shape = shape,
        clip = false,
        ambientColor = shadowColor,
        spotColor = shadowColor
    )
    return if (showBorder) {
        shadowed.border(width = 1.dp, color = borderColor, shape = shape)
    } else {
        shadowed
    }
}

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    CompositionLocalProvider(LocalIsDarkTheme provides darkTheme) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
