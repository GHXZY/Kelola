package com.example.ui.products

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.CategoryEntity
import com.example.data.local.entity.ProductEntity
import com.example.ui.components.KelolaLogoBadge
import com.example.ui.theme.BorderLight
import com.example.ui.theme.BrandDeep
import com.example.ui.theme.BrandSky
import com.example.ui.theme.DangerRed
import com.example.ui.theme.KelolaRadius
import com.example.ui.theme.KelolaSpacing
import com.example.ui.theme.PrimaryBlue
import com.example.ui.theme.PrimaryBlueContainer
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.kelolaSoftShadow
import com.example.util.FormatUtils

/**
 * AddEditProductScreen:
 * Laman penuh (bukan popup) untuk menambah dan mengedit produk secara komprehensif,
 * sesuai prinsip Kelola Layout System (4px grid, 20dp margin, 52dp CTA button).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditProductScreen(
    initialProduct: ProductEntity? = null,
    categories: List<CategoryEntity>,
    onSaveProduct: (
        name: String,
        categoryId: Long,
        costPrice: Long,
        sellingPrice: Long,
        stock: Int,
        minStock: Int,
        unit: String,
        expirationDate: Long?
    ) -> Unit,
    onOpenAddCategory: () -> Unit = {},
    onAddCategoryCustom: (name: String, onCreated: (Long) -> Unit) -> Unit = { _, _ -> },
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isEditing = initialProduct != null

    var name by remember { mutableStateOf(initialProduct?.name ?: "") }
    var categoryId by remember {
        mutableStateOf(initialProduct?.categoryId ?: (categories.firstOrNull()?.id ?: 1L))
    }
    var costPriceText by remember {
        mutableStateOf(initialProduct?.costPrice?.takeIf { it > 0 }?.let { FormatUtils.formatNumberWithDots(it) } ?: "0")
    }
    var sellingPriceText by remember {
        mutableStateOf(initialProduct?.sellingPrice?.takeIf { it > 0 }?.let { FormatUtils.formatNumberWithDots(it) } ?: "")
    }
    var stockText by remember {
        mutableStateOf(initialProduct?.stock?.toString() ?: "0")
    }
    var minStockText by remember {
        mutableStateOf(initialProduct?.minimumStock?.toString() ?: "5")
    }
    var unit by remember { mutableStateOf(initialProduct?.unit ?: "pcs") }
    var expirationDate by remember { mutableStateOf<Long?>(initialProduct?.expirationDate) }
    var showCustomExpiryDialog by remember { mutableStateOf(false) }
    var showAddCategoryPopup by remember { mutableStateOf(false) }

    var categoryDropdownExpanded by remember { mutableStateOf(false) }
    val commonUnits = listOf("pcs", "bungkus", "botol", "gelas", "porsi", "pack", "kotak", "kg", "gram", "ikat")

    LaunchedEffect(categories) {
        if (categories.isNotEmpty() && categories.none { it.id == categoryId }) {
            categoryId = categories.first().id
        }
    }

    val selectedCategoryName = categories.find { it.id == categoryId }?.name ?: "Pilih Kategori"

    var nameError by remember { mutableStateOf(false) }
    var sellingPriceError by remember { mutableStateOf(false) }

    // Live profit calculation
    val costPrice = FormatUtils.parseRupiahInput(costPriceText)
    val sellingPrice = FormatUtils.parseRupiahInput(sellingPriceText)
    val profitPerUnit = sellingPrice - costPrice
    val profitPercentage = if (costPrice > 0L) {
        ((profitPerUnit.toDouble() / costPrice.toDouble()) * 100).toInt()
    } else 0

    val inputColors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
        focusedBorderColor = MaterialTheme.colorScheme.primary,
        unfocusedBorderColor = Color.Transparent,
        focusedLabelColor = MaterialTheme.colorScheme.primary,
        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
        focusedTextColor = MaterialTheme.colorScheme.onSurface,
        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
        focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
        focusedPrefixColor = MaterialTheme.colorScheme.onSurface,
        unfocusedPrefixColor = MaterialTheme.colorScheme.onSurface
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 0.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = KelolaSpacing.ScreenMargin, vertical = KelolaSpacing.Space3),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(KelolaSpacing.Space3)
                    ) {
                        IconButton(
                            onClick = onNavigateBack,
                            modifier = Modifier
                                .size(KelolaSpacing.MinTouchTarget)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .testTag("button_back_from_add_product")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Kembali ke Katalog",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Column {
                            Text(
                                text = if (isEditing) "Edit Produk" else "Tambah Produk Baru",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                letterSpacing = (-0.3).sp
                            )
                            Text(
                                text = "Laman Katalog & Inventaris Kelola",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = FontWeight.Medium,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }

                    KelolaLogoBadge(
                        size = 38.dp,
                        iconSize = 22.dp
                    )
                }
            }
        },
        bottomBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = KelolaSpacing.ScreenMargin, vertical = KelolaSpacing.Space3),
                    horizontalArrangement = Arrangement.spacedBy(KelolaSpacing.Space3)
                ) {
                    OutlinedButton(
                        onClick = onNavigateBack,
                        modifier = Modifier
                            .weight(1f)
                            .height(KelolaSpacing.ButtonHeightCta),
                        shape = KelolaRadius.ShapeInput,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Text(
                            text = "Batal",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp,
                            maxLines = 1,
                            softWrap = false
                        )
                    }

                    Button(
                        onClick = {
                            if (name.isBlank()) {
                                nameError = true
                                return@Button
                            }
                            val sp = FormatUtils.parseRupiahInput(sellingPriceText)
                            if (sp <= 0) {
                                sellingPriceError = true
                                return@Button
                            }

                            val cp = FormatUtils.parseRupiahInput(costPriceText)
                            val stk = stockText.toIntOrNull() ?: 0
                            val minStk = minStockText.toIntOrNull() ?: 5

                            onSaveProduct(
                                name.trim(),
                                categoryId,
                                cp,
                                sp,
                                stk,
                                minStk,
                                unit.trim(),
                                expirationDate
                            )
                        },
                        modifier = Modifier
                            .weight(2f)
                            .height(KelolaSpacing.ButtonHeightCta)
                            .testTag("button_save_product"),
                        shape = KelolaRadius.ShapeInput,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(KelolaSpacing.Space2))
                        Text(
                            text = if (isEditing) "Simpan Perubahan" else "Simpan Produk",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            maxLines = 1,
                            softWrap = false
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = KelolaSpacing.ScreenMargin),
            verticalArrangement = Arrangement.spacedBy(KelolaSpacing.Space4)
        ) {
            item {
                Spacer(modifier = Modifier.height(KelolaSpacing.Space2))
            }

            // =========================================================================
            // CARD 1: INFORMASI UTAMA PRODUK
            // =========================================================================
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .kelolaSoftShadow(shape = KelolaRadius.ShapeCard, elevation = 2.dp),
                    shape = KelolaRadius.ShapeCard,
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier.padding(KelolaSpacing.Space4),
                        verticalArrangement = Arrangement.spacedBy(KelolaSpacing.Space3)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(KelolaSpacing.Space2)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Inventory2,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Text(
                                text = "Informasi Utama",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))

                        // Nama Produk
                        OutlinedTextField(
                            value = name,
                            onValueChange = {
                                name = it
                                nameError = it.isBlank()
                            },
                            label = { Text("Nama Produk *") },
                            placeholder = { Text("Misal: Kopi Susu Aren, Mie Goreng, dsb") },
                            isError = nameError,
                            supportingText = {
                                if (nameError) {
                                    Text("Nama produk wajib diisi", color = DangerRed)
                                }
                            },
                            singleLine = true,
                            shape = KelolaRadius.ShapeInput,
                            colors = inputColors,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("input_product_name")
                        )

                        // Kategori Dropdown & Add Button
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ExposedDropdownMenuBox(
                                expanded = categoryDropdownExpanded,
                                onExpandedChange = { categoryDropdownExpanded = !categoryDropdownExpanded },
                                modifier = Modifier.weight(1f)
                            ) {
                                OutlinedTextField(
                                    value = selectedCategoryName,
                                    onValueChange = {},
                                    readOnly = true,
                                    label = { Text("Kategori Produk") },
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryDropdownExpanded)
                                    },
                                    shape = KelolaRadius.ShapeInput,
                                    colors = inputColors,
                                    modifier = Modifier
                                        .menuAnchor()
                                        .fillMaxWidth()
                                )
                                ExposedDropdownMenu(
                                    expanded = categoryDropdownExpanded,
                                    onDismissRequest = { categoryDropdownExpanded = false }
                                ) {
                                    categories.forEach { cat ->
                                        DropdownMenuItem(
                                            text = { Text(cat.name) },
                                            onClick = {
                                                categoryId = cat.id
                                                categoryDropdownExpanded = false
                                            }
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.width(KelolaSpacing.Space2))

                            IconButton(
                                onClick = { showAddCategoryPopup = true },
                                modifier = Modifier
                                    .size(KelolaSpacing.MinTouchTarget)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primaryContainer)
                            ) {
                                Icon(
                                    Icons.Default.Add,
                                    contentDescription = "Tambah Kategori Baru",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        // Satuan Barang
                        Column(verticalArrangement = Arrangement.spacedBy(KelolaSpacing.Space1)) {
                            Text(
                                text = "Satuan Barang",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = FontWeight.Bold
                            )
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(KelolaSpacing.Space1)
                            ) {
                                items(commonUnits) { u ->
                                    val isSelected = unit.equals(u, ignoreCase = true)
                                    FilterChip(
                                        selected = isSelected,
                                        onClick = { unit = u },
                                        label = {
                                            Text(
                                                text = u,
                                                fontSize = 12.sp,
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                            )
                                        },
                                        shape = KelolaRadius.ShapeSmall,
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                            labelColor = MaterialTheme.colorScheme.onSurface
                                        ),
                                        modifier = Modifier.heightIn(min = 38.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // =========================================================================
            // CARD 2: PENETAPAN HARGA & ESTIMASI MARGIN
            // =========================================================================
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .kelolaSoftShadow(shape = KelolaRadius.ShapeCard, elevation = 2.dp),
                    shape = KelolaRadius.ShapeCard,
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier.padding(KelolaSpacing.Space4),
                        verticalArrangement = Arrangement.spacedBy(KelolaSpacing.Space3)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(KelolaSpacing.Space2)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(SuccessGreen.copy(alpha = 0.12f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Payments,
                                    contentDescription = null,
                                    tint = SuccessGreen,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Text(
                                text = "Penetapan Harga & Margin",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(KelolaSpacing.Space2)
                        ) {
                            OutlinedTextField(
                                value = costPriceText,
                                onValueChange = { costPriceText = FormatUtils.formatRupiahInput(it) },
                                label = { Text("Harga Modal (HPP)") },
                                prefix = { Text("Rp ", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface) },
                                placeholder = { Text("0") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                shape = KelolaRadius.ShapeInput,
                                colors = inputColors,
                                modifier = Modifier.weight(1f)
                            )

                            OutlinedTextField(
                                value = sellingPriceText,
                                onValueChange = {
                                    sellingPriceText = FormatUtils.formatRupiahInput(it)
                                    sellingPriceError = FormatUtils.parseRupiahInput(sellingPriceText) <= 0
                                },
                                label = { Text("Harga Jual *") },
                                prefix = { Text("Rp ", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface) },
                                placeholder = { Text("10.000") },
                                isError = sellingPriceError,
                                supportingText = {
                                    if (sellingPriceError) {
                                        Text("Wajib diisi", color = DangerRed)
                                    }
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                shape = KelolaRadius.ShapeInput,
                                colors = inputColors,
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("input_product_price")
                            )
                        }

                        // Estimasi Margin Keuntungan Box
                        val isDark = isSystemInDarkTheme()
                        val profitColor = if (profitPerUnit >= 0) SuccessGreen else DangerRed
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = KelolaRadius.ShapeInput,
                            color = profitColor.copy(alpha = 0.12f)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = KelolaSpacing.Space3, vertical = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(KelolaSpacing.Space2)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.TrendingUp,
                                        contentDescription = null,
                                        tint = profitColor,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Column {
                                        Text(
                                            text = "Estimasi Untung / Unit",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Text(
                                            text = FormatUtils.formatRupiah(profitPerUnit),
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = profitColor
                                        )
                                    }
                                }

                                if (costPrice > 0L) {
                                    Surface(
                                        shape = KelolaRadius.ShapeSmall,
                                        color = if (isDark) (if (profitPerUnit >= 0) Color(0xFF0D3322) else Color(0xFF381515)) else profitColor
                                    ) {
                                        Text(
                                            text = "$profitPercentage%",
                                            color = if (isDark) profitColor else Color.White,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // =========================================================================
            // CARD 3: MANAJEMEN STOK & INVENTARIS
            // =========================================================================
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .kelolaSoftShadow(shape = KelolaRadius.ShapeCard, elevation = 2.dp),
                    shape = KelolaRadius.ShapeCard,
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier.padding(KelolaSpacing.Space4),
                        verticalArrangement = Arrangement.spacedBy(KelolaSpacing.Space3)
                    ) {
                        Text(
                            text = "Inventaris & Peringatan Stok",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(KelolaSpacing.Space2)
                        ) {
                            OutlinedTextField(
                                value = stockText,
                                onValueChange = { stockText = it.filter { c -> c.isDigit() } },
                                label = { Text(if (isEditing) "Stok Saat Ini" else "Stok Awal") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                shape = KelolaRadius.ShapeInput,
                                colors = inputColors,
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("input_product_stock")
                            )

                            OutlinedTextField(
                                value = minStockText,
                                onValueChange = { minStockText = it.filter { c -> c.isDigit() } },
                                label = { Text("Batas Min. Stok") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                shape = KelolaRadius.ShapeInput,
                                colors = inputColors,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        // Penyesuaian Cepat Stok
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(KelolaSpacing.Space2),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Tambah Cepat:",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = FontWeight.SemiBold
                            )
                            listOf(1, 5, 10, 50).forEach { inc ->
                                Surface(
                                    onClick = {
                                        val current = stockText.toIntOrNull() ?: 0
                                        stockText = (current + inc).toString()
                                    },
                                    shape = KelolaRadius.ShapeSmall,
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    modifier = Modifier.height(34.dp)
                                ) {
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier.padding(horizontal = 12.dp)
                                    ) {
                                        Text(
                                            text = "+$inc",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // =========================================================================
            // CARD 4: MASA SIMPAN & KADALUARSA
            // =========================================================================
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .kelolaSoftShadow(shape = KelolaRadius.ShapeCard, elevation = 2.dp),
                    shape = KelolaRadius.ShapeCard,
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier.padding(KelolaSpacing.Space4),
                        verticalArrangement = Arrangement.spacedBy(KelolaSpacing.Space3)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.CalendarMonth,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(KelolaSpacing.Space1))
                                Text(
                                    text = "Masa Simpan & Kadaluarsa",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }

                            if (expirationDate != null) {
                                TextButton(onClick = { expirationDate = null }) {
                                    Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(14.dp), tint = DangerRed)
                                    Spacer(modifier = Modifier.width(2.dp))
                                    Text("Hapus", color = DangerRed, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }

                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))

                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = KelolaRadius.ShapeInput,
                            color = if (expirationDate != null) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f) else MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 14.dp, vertical = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = if (expirationDate != null) {
                                            FormatUtils.formatDateTime(expirationDate!!)
                                        } else {
                                            "Tanpa tanggal kadaluarsa"
                                        },
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = if (expirationDate != null) FontWeight.Bold else FontWeight.Normal,
                                        color = if (expirationDate != null) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = if (expirationDate != null) "Notifikasi akan muncul saat mendekati masa kadaluarsa" else "Pilih preset cepat atau tentukan waktu custom",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        fontSize = 11.sp
                                    )
                                }

                                OutlinedButton(
                                    onClick = { showCustomExpiryDialog = true },
                                    shape = KelolaRadius.ShapeSmall,
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = MaterialTheme.colorScheme.primary
                                    ),
                                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                    modifier = Modifier
                                        .height(38.dp)
                                        .testTag("button_open_custom_expiry_picker")
                                ) {
                                    Text(
                                        text = if (expirationDate == null) "Pilih Custom" else "Ubah",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                        // Presets
                        Text(
                            text = "Preset Cepat (Jam & Hari):",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 11.sp
                        )
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            val presets = listOf(
                                "+1 Jam" to 1L * 3600000L,
                                "+3 Jam" to 3L * 3600000L,
                                "+6 Jam" to 6L * 3600000L,
                                "+12 Jam" to 12L * 3600000L,
                                "+1 Hari" to 1L * 86400000L,
                                "+3 Hari" to 3L * 86400000L,
                                "+7 Hari" to 7L * 86400000L,
                                "+1 Bulan" to 30L * 86400000L,
                                "+3 Bulan" to 90L * 86400000L,
                                "+6 Bulan" to 180L * 86400000L,
                                "+1 Tahun" to 365L * 86400000L
                            )
                            items(presets) { (label, duration) ->
                                FilterChip(
                                    selected = false,
                                    onClick = {
                                        expirationDate = System.currentTimeMillis() + duration
                                    },
                                    label = { Text(label, fontSize = 11.sp, fontWeight = FontWeight.Medium) },
                                    shape = KelolaRadius.ShapeSmall,
                                    colors = FilterChipDefaults.filterChipColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                        labelColor = MaterialTheme.colorScheme.onSurface
                                    ),
                                    modifier = Modifier.heightIn(min = 36.dp)
                                )
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(KelolaSpacing.Space6))
            }
        }
    }

    if (showCustomExpiryDialog) {
        CustomExpirationPickerDialog(
            currentExpiration = expirationDate,
            onConfirm = { expirationDate = it },
            onClear = { expirationDate = null },
            onDismiss = { showCustomExpiryDialog = false }
        )
    }

    if (showAddCategoryPopup) {
        AddCategoryDialog(
            onSaveCategory = { catName ->
                onAddCategoryCustom(catName) { newId ->
                    categoryId = newId
                }
                showAddCategoryPopup = false
            },
            onDismiss = { showAddCategoryPopup = false }
        )
    }
}
