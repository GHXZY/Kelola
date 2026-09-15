package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.BrandDeep
import com.example.ui.theme.BrandSky
import com.example.ui.theme.GradientBrand
import com.example.ui.theme.KelolaRadius
import com.example.ui.theme.KelolaSpacing

import androidx.compose.foundation.Image

/**
 * Logo Kelola Resmi:
 * Logo utama aplikasi berukuran sumber 683dp x 683dp (1:1),
 * dengan monogram 'K' modern, strip mata uang finansial, dan badge sirkular resmi.
 */
@Composable
fun KelolaLogoIcon(
    modifier: Modifier = Modifier,
    size: Dp = 26.dp,
    tint: Color = Color.Unspecified
) {
    if (tint == Color.Unspecified) {
        Image(
            painter = painterResource(id = R.drawable.logo_kelola_1),
            contentDescription = "Logo Kelola",
            modifier = modifier.size(size)
        )
    } else {
        Icon(
            painter = painterResource(id = R.drawable.ic_kelola_logo),
            contentDescription = "Logo Kelola",
            tint = tint,
            modifier = modifier.size(size)
        )
    }
}

/**
 * KelolaLogoBadge:
 * Menampilkan logo resmi Kelola (683dp x 683dp) secara proporsional
 * sesuai ukuran standar sentuh min 44x44px.
 */
@Composable
fun KelolaLogoBadge(
    modifier: Modifier = Modifier,
    size: Dp = 44.dp,
    iconSize: Dp = 26.dp
) {
    Image(
        painter = painterResource(id = R.drawable.logo_kelola_1),
        contentDescription = "Logo Kelola",
        modifier = modifier.size(size)
    )
}

/**
 * KelolaBrandHeader:
 * Menampilkan Logo SVG Kelola dan tipografi brand yang sejajar rapi
 * mengikuti baseline grid dan layout system.
 */
@Composable
fun KelolaBrandHeader(
    modifier: Modifier = Modifier,
    title: String = "Kelola",
    subtitle: String = "KASIR & KEUANGAN",
    showStatusDot: Boolean = true,
    statusColor: Color = BrandSky
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(KelolaSpacing.Space3) // 12px
    ) {
        KelolaLogoBadge(
            size = KelolaSpacing.MinTouchTarget, // 44px
            iconSize = KelolaSpacing.MaxIconInTarget // 24px
        )

        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
                letterSpacing = (-0.4).sp
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(KelolaSpacing.Space1) // 4px
            ) {
                if (showStatusDot) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(statusColor)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                }
                Text(
                    text = subtitle.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp
                )
            }
        }
    }
}
