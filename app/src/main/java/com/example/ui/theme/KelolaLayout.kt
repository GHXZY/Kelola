package com.example.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Kelola — Layout System (Mobile)
 * Skala 4px terstandarisasi untuk grid, jarak, dan aturan penempatan.
 *
 * Sesuai panduan resmi Kelola Layout System:
 * - space-1 (4px)  : Jarak antara ikon dan teks yang menempel
 * - space-2 (8px)  : Jarak antar baris dalam satu kartu (judul -> subjudul)
 * - space-3 (12px) : Jarak antar elemen dalam satu grup (label -> nilai) & minimum tap gap
 * - space-4 (16px) : Padding dalam kartu; jarak antar kartu sejenis; gap kategori
 * - space-5 (24px) : Jarak antar section (mis. "Ringkasan" -> "Transaksi Terbaru")
 * - space-6 (32px) : Margin atas/bawah halaman, jarak sebelum tombol utama
 */
object KelolaSpacing {
    val Space1: Dp = 4.dp
    val Space2: Dp = 8.dp
    val Space3: Dp = 12.dp
    val Space4: Dp = 16.dp
    val Space5: Dp = 24.dp
    val Space6: Dp = 32.dp

    // Margin tepi layar: 16dp default (Layout.txt baseline: 16-20dp)
    val ScreenMargin: Dp = 16.dp

    // Aturan Ukuran Tombol & Input (Android Touch Ergonomics)
    val ButtonHeightCta: Dp = 48.dp          // Tombol utama (CTA penuh): 48dp
    val ButtonHeightSecondary: Dp = 48.dp    // Tombol standar/sekunder: 48dp
    val ButtonHeightCompact: Dp = 40.dp      // Tombol kompak: 40dp
    val ButtonHeightSmall: Dp = 36.dp        // Tombol kecil: 36dp
    val MinTouchTarget: Dp = 48.dp           // Area sentuh minimum Android (Touch target >= 48dp)
    val MaxIconInTarget: Dp = 24.dp          // Ikon standar 24x24dp di dalam target sentuh 48dp
    val ChipHeight: Dp = 36.dp               // Chip / filter: 32-36dp
    val InputHeight: Dp = 48.dp              // Input field height: 48-52dp
    val HeaderHeight: Dp = 56.dp             // Header / Top App Bar height: 56dp
    val IconContainerSize: Dp = 40.dp        // Icon container: 40dp (dengan icon 20-24dp)

    // Aturan Jarak Antar Elemen Interaktif
    val MinInteractiveGap: Dp = 12.dp        // Minimum 12px antara dua elemen tap
    val ButtonGap: Dp = 12.dp                // Jarak antar tombol (Layout.txt: 8-12dp)
    val SectionSpacing: Dp = 24.dp           // Jarak antar section: 24dp
    val ComponentSpacing: Dp = 16.dp         // Jarak antar komponen: 16dp

    // Aturan Perataan Kolom Nominal Daftar Transaksi
    val AmountColumnWidth: Dp = 112.dp       // Kolom nominal rata kanan dengan lebar tetap
}
