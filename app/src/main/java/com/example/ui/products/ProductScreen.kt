package com.example.ui.products

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.CategoryEntity
import com.example.data.local.entity.ProductEntity
import com.example.ui.components.CategoryChipGroup
import com.example.ui.components.EditorialCategoryTag
import com.example.ui.components.EmptyState
import com.example.ui.components.SearchField
import com.example.ui.components.StockBadge
import com.example.ui.theme.BorderLight
import com.example.ui.theme.BrandDeep
import com.example.ui.theme.DangerContainer
import com.example.ui.theme.DangerRed
import com.example.ui.theme.GradientBrand
import com.example.ui.theme.KelolaRadius
import com.example.ui.theme.KelolaSpacing
import com.example.ui.theme.KelolaTheme
import com.example.ui.theme.PrimaryBlue
import com.example.ui.theme.PrimaryBlueContainer
import com.example.ui.theme.SuccessContainer
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WarningAmber
import com.example.ui.theme.WarningContainer
import com.example.ui.theme.kelolaSoftShadow
import com.example.util.FormatUtils

@Composable
fun ProductScreen(
    products: List<ProductEntity>,
    categories: List<CategoryEntity>,
    onOpenAddProduct: () -> Unit,
    onEditProduct: (ProductEntity) -> Unit,
    onRestockProduct: (ProductEntity) -> Unit,
    onReduceStockProduct: (ProductEntity) -> Unit,
    onDeleteProduct: (ProductEntity) -> Unit,
    onNavigateToCashier: () -> Unit = {},
    onOpenAddExpense: () -> Unit = {},
    onOpenPromo: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategoryName by remember { mutableStateOf("Semua") }

    val categoryMap = remember(categories) { categories.associateBy { it.id } }
    val categoryNames = remember(categories) {
        listOf("Semua") + categories.map { it.name }
    }

    val filteredProducts = remember(products, searchQuery, selectedCategoryName, categoryMap) {
        products.filter { prod ->
            val matchSearch = searchQuery.isBlank() ||
                    prod.name.contains(searchQuery, ignoreCase = true) ||
                    (categoryMap[prod.categoryId]?.name?.contains(searchQuery, ignoreCase = true) == true)

            val matchCategory = selectedCategoryName == "Semua" ||
                    categoryMap[prod.categoryId]?.name.equals(selectedCategoryName, ignoreCase = true)

            matchSearch && matchCategory
        }
    }

    val totalAssetValue = remember(products) {
        products.sumOf { it.costPrice * it.stock }
    }

    var isFabExpanded by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                androidx.compose.animation.AnimatedVisibility(
                    visible = isFabExpanded,
                    enter = androidx.compose.animation.fadeIn() + androidx.compose.animation.slideInVertically(initialOffsetY = { it / 2 }),
                    exit = androidx.compose.animation.fadeOut() + androidx.compose.animation.slideOutVertically(targetOffsetY = { it / 2 })
                ) {
                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // 1. Promo & Bundling
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.clickable {
                                isFabExpanded = false
                                onOpenPromo()
                            }
                        ) {
                            Surface(
                                shape = KelolaRadius.ShapeChip,
                                color = MaterialTheme.colorScheme.surface,
                                shadowElevation = 2.dp,
                                border = BorderStroke(1.dp, BorderLight)
                            ) {
                                Text(
                                    text = "Promo & Bundling",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                            FloatingActionButton(
                                onClick = {
                                    isFabExpanded = false
                                    onOpenPromo()
                                },
                                shape = CircleShape,
                                containerColor = PrimaryBlueContainer,
                                contentColor = MaterialTheme.colorScheme.primary,
                                elevation = androidx.compose.material3.FloatingActionButtonDefaults.elevation(defaultElevation = 2.dp),
                                modifier = Modifier
                                    .size(44.dp)
                                    .testTag("fab_promo")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocalOffer,
                                    contentDescription = "Promo & Bundling",
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        // 2. Pengeluaran (Catat Biaya)
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.clickable {
                                isFabExpanded = false
                                onOpenAddExpense()
                            }
                        ) {
                            Surface(
                                shape = KelolaRadius.ShapeChip,
                                color = MaterialTheme.colorScheme.surface,
                                shadowElevation = 2.dp,
                                border = BorderStroke(1.dp, BorderLight)
                            ) {
                                Text(
                                    text = "Catat Biaya / Pengeluaran",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = DangerRed,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                            FloatingActionButton(
                                onClick = {
                                    isFabExpanded = false
                                    onOpenAddExpense()
                                },
                                shape = CircleShape,
                                containerColor = DangerContainer,
                                contentColor = DangerRed,
                                elevation = androidx.compose.material3.FloatingActionButtonDefaults.elevation(defaultElevation = 2.dp),
                                modifier = Modifier
                                    .size(44.dp)
                                    .testTag("fab_add_expense")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.TrendingDown,
                                    contentDescription = "Catat Biaya",
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        // 3. Tambah Produk
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.clickable {
                                isFabExpanded = false
                                onOpenAddProduct()
                            }
                        ) {
                            Surface(
                                shape = KelolaRadius.ShapeChip,
                                color = MaterialTheme.colorScheme.surface,
                                shadowElevation = 2.dp,
                                border = BorderStroke(1.dp, BorderLight)
                            ) {
                                Text(
                                    text = "Tambah Produk",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                            FloatingActionButton(
                                onClick = {
                                    isFabExpanded = false
                                    onOpenAddProduct()
                                },
                                shape = CircleShape,
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = Color.White,
                                elevation = androidx.compose.material3.FloatingActionButtonDefaults.elevation(defaultElevation = 2.dp),
                                modifier = Modifier
                                    .size(44.dp)
                                    .testTag("fab_sub_add_product")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Tambah Produk",
                                    tint = Color.White,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                    }
                }

                // Main Toggle FAB
                FloatingActionButton(
                    onClick = { isFabExpanded = !isFabExpanded },
                    shape = CircleShape,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White,
                    elevation = androidx.compose.material3.FloatingActionButtonDefaults.elevation(defaultElevation = 4.dp),
                    modifier = Modifier
                        .size(56.dp)
                        .testTag("fab_main_toggle")
                ) {
                    Icon(
                        imageVector = if (isFabExpanded) Icons.Default.Close else Icons.Default.Add,
                        contentDescription = if (isFabExpanded) "Tutup Menu Aksi" else "Menu Aksi Cepat",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = KelolaSpacing.ScreenMargin)
        ) {
            Spacer(modifier = Modifier.height(KelolaSpacing.Space1))

            // Akses Cepat Catat Biaya & Promo (Tombol Sekunder 44px, Radius 14px)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(KelolaSpacing.Space2)
            ) {
                Surface(
                    onClick = onOpenAddExpense,
                    modifier = Modifier
                        .weight(1f)
                        .height(KelolaSpacing.ButtonHeightSecondary)
                        .testTag("button_quick_record_expense"),
                    shape = KelolaRadius.ShapeInput,
                    color = DangerContainer,
                    border = BorderStroke(1.dp, DangerRed.copy(alpha = 0.25f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = KelolaSpacing.Space2),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            Icons.Default.TrendingDown,
                            contentDescription = null,
                            tint = DangerRed,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(KelolaSpacing.Space2))
                        Text(
                            "Catat Biaya",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = DangerRed,
                            maxLines = 1,
                            softWrap = false
                        )
                    }
                }

                Surface(
                    onClick = onOpenPromo,
                    modifier = Modifier
                        .weight(1f)
                        .height(KelolaSpacing.ButtonHeightSecondary)
                        .testTag("button_quick_promo"),
                    shape = KelolaRadius.ShapeInput,
                    color = MaterialTheme.colorScheme.surface,
                    border = BorderStroke(1.dp, BorderLight)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = KelolaSpacing.Space2),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            Icons.Default.LocalOffer,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(KelolaSpacing.Space2))
                        Text(
                            "Promo",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                            maxLines = 1,
                            softWrap = false
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Search Field
            SearchField(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                placeholder = "Cari produk inventori...",
                testTag = "inventory_search_input"
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Category Chips
            CategoryChipGroup(
                categories = categoryNames,
                selectedCategory = selectedCategoryName,
                onSelectCategory = { selectedCategoryName = it }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Asset Summary Card (Tanpa border, soft shadow)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .kelolaSoftShadow(shape = KelolaRadius.ShapeCard, elevation = 2.dp),
                shape = KelolaRadius.ShapeCard,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Total katalog",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "${products.size} jenis barang",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "Estimasi nilai stok",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = FormatUtils.formatRupiah(totalAssetValue),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (filteredProducts.isEmpty()) {
                EmptyState(
                    icon = Icons.Default.Inventory2,
                    title = "Belum Ada Produk",
                    description = if (searchQuery.isNotEmpty()) "Tidak ada produk yang sesuai dengan pencarian." else "Tambahkan produk jualanmu agar bisa mulai bertransaksi di Kasir.",
                    buttonText = if (searchQuery.isEmpty()) "+ Tambah Produk Pertama" else null,
                    onButtonClick = if (searchQuery.isEmpty()) onOpenAddProduct else null,
                    modifier = Modifier.weight(1f)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(filteredProducts, key = { it.id }) { product ->
                        var menuExpanded by remember { mutableStateOf(false) }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .kelolaSoftShadow(shape = KelolaRadius.ShapeCard, elevation = 3.dp)
                                .testTag("product_row_${product.id}"),
                            shape = KelolaRadius.ShapeCard,
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                                        ) {
                                            val catName = categoryMap[product.categoryId]?.name ?: "Umum"
                                            EditorialCategoryTag(category = catName)

                                            StockBadge(
                                                stock = product.stock,
                                                minimumStock = product.minimumStock,
                                                unit = product.unit
                                            )

                                            // Expiration Status Badge
                                            if (product.expirationDate != null) {
                                                val now = System.currentTimeMillis()
                                                val isExpired = product.expirationDate < now
                                                val isExpiringSoon = !isExpired && (product.expirationDate - now) < (7L * 86400000L)

                                                Surface(
                                                    shape = KelolaRadius.ShapeSmall,
                                                    color = when {
                                                        isExpired -> DangerRed.copy(alpha = 0.15f)
                                                        isExpiringSoon -> WarningAmber.copy(alpha = 0.2f)
                                                        else -> MaterialTheme.colorScheme.surfaceVariant
                                                    }
                                                ) {
                                                    Row(
                                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        if (isExpired || isExpiringSoon) {
                                                            Icon(
                                                                Icons.Default.Warning,
                                                                contentDescription = null,
                                                                tint = if (isExpired) DangerRed else WarningAmber,
                                                                modifier = Modifier.size(12.dp)
                                                            )
                                                            Spacer(modifier = Modifier.width(4.dp))
                                                        }
                                                        Text(
                                                            text = when {
                                                                isExpired -> "Kadaluarsa"
                                                                isExpiringSoon -> "Segera Exp."
                                                                else -> "Exp: ${FormatUtils.formatDate(product.expirationDate)}"
                                                            },
                                                            style = MaterialTheme.typography.labelSmall,
                                                            color = when {
                                                                isExpired -> DangerRed
                                                                isExpiringSoon -> WarningAmber
                                                                else -> MaterialTheme.colorScheme.onSurfaceVariant
                                                            },
                                                            fontWeight = FontWeight.SemiBold
                                                        )
                                                    }
                                                }
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(6.dp))

                                        Text(
                                            text = product.name,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.SemiBold,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )

                                        Spacer(modifier = Modifier.height(4.dp))

                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                                        ) {
                                            Column {
                                                Text(
                                                    text = "Harga Jual",
                                                    style = MaterialTheme.typography.labelSmall,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                                )
                                                Text(
                                                    text = FormatUtils.formatRupiah(product.sellingPrice),
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                            }

                                            Column {
                                                Text(
                                                    text = "Modal (HPP)",
                                                    style = MaterialTheme.typography.labelSmall,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                                )
                                                Text(
                                                    text = FormatUtils.formatRupiah(product.costPrice),
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                                )
                                            }

                                            val profitPerItem = product.sellingPrice - product.costPrice
                                            Column {
                                                Text(
                                                    text = "Profit/pcs",
                                                    style = MaterialTheme.typography.labelSmall,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                                )
                                                Text(
                                                    text = FormatUtils.formatRupiah(profitPerItem),
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = SuccessGreen
                                                )
                                            }
                                        }
                                    }

                                    Box {
                                        IconButton(
                                            onClick = { menuExpanded = true },
                                            modifier = Modifier.testTag("button_product_options_${product.id}")
                                        ) {
                                            Icon(
                                                Icons.Default.MoreVert,
                                                contentDescription = "Opsi Produk",
                                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }

                                        DropdownMenu(
                                            expanded = menuExpanded,
                                            onDismissRequest = { menuExpanded = false }
                                        ) {
                                            DropdownMenuItem(
                                                text = { Text("Tambah Stok (Restock)") },
                                                leadingIcon = { Icon(Icons.Default.Add, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
                                                onClick = {
                                                    menuExpanded = false
                                                    onRestockProduct(product)
                                                }
                                            )
                                            DropdownMenuItem(
                                                text = { Text("Kurangi Stok") },
                                                leadingIcon = { Icon(Icons.Default.Remove, contentDescription = null, tint = DangerRed) },
                                                onClick = {
                                                    menuExpanded = false
                                                    onReduceStockProduct(product)
                                                }
                                            )
                                            DropdownMenuItem(
                                                text = { Text("Edit Produk") },
                                                leadingIcon = { Icon(Icons.Default.Edit, contentDescription = null) },
                                                onClick = {
                                                    menuExpanded = false
                                                    onEditProduct(product)
                                                }
                                            )
                                            DropdownMenuItem(
                                                text = { Text("Hapus Produk", color = DangerRed) },
                                                leadingIcon = { Icon(Icons.Default.Delete, contentDescription = null, tint = DangerRed) },
                                                onClick = {
                                                    menuExpanded = false
                                                    onDeleteProduct(product)
                                                }
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                // Fast Action Buttons (Quick Restock and Quick Reduce Stock) - Radius 10px, Tanpa Border
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Surface(
                                        onClick = { onReduceStockProduct(product) },
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(40.dp)
                                            .testTag("button_quick_reduce_${product.id}"),
                                        shape = KelolaRadius.ShapeSmall,
                                        color = DangerContainer
                                    ) {
                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = Modifier.fillMaxSize()
                                        ) {
                                            Icon(Icons.Default.Remove, contentDescription = "Kurangi Stok", tint = DangerRed, modifier = Modifier.size(18.dp))
                                        }
                                    }

                                    Surface(
                                        onClick = { onRestockProduct(product) },
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(40.dp)
                                            .testTag("button_quick_restock_${product.id}"),
                                        shape = KelolaRadius.ShapeSmall,
                                        color = MaterialTheme.colorScheme.primary
                                    ) {
                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = Modifier.fillMaxSize()
                                        ) {
                                            Icon(Icons.Default.Add, contentDescription = "Tambah Stok", tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(18.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
