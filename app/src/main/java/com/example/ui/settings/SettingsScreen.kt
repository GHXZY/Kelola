package com.example.ui.settings

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.filled.Crop
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.BrightnessMedium
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.LocalAtm
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.ConfirmationDialog
import com.example.ui.components.KelolaLogoBadge
import com.example.ui.theme.DangerRed
import com.example.ui.theme.KelolaRadius
import com.example.ui.theme.KelolaSpacing
import com.example.ui.theme.PrimaryBlue
import com.example.ui.theme.SecondaryTeal
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WarningAmber
import com.example.ui.theme.WarningContainer
import com.example.ui.theme.kelolaSoftShadow
import com.example.util.FormatUtils
import java.io.File

/**
 * SettingsScreen:
 * Laman penuh mandiri (bukan popup/dialog) untuk mengelola pengaturan usaha,
 * metode pembayaran QRIS, modal awal, format struk, tema, dan pencadangan database.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    currentBusinessName: String,
    currentAddress: String,
    currentPhone: String,
    currentReceiptFooter: String,
    defaultPaymentMethod: String,
    qrisImagePath: String,
    qrisMerchantName: String,
    openingCapital: Long,
    previousSales: Long,
    previousSalesDate: String,
    previousSalesNote: String,
    themeMode: String,
    viewportWidth: String = "412dp",
    onSaveViewportWidth: (String) -> Unit = {},
    onSaveBusinessInfo: (name: String, address: String, phone: String) -> Unit,
    onSaveDefaultPaymentMethod: (String) -> Unit,
    onUploadQris: (Uri) -> Unit,
    onSaveCroppedQris: (Bitmap) -> Unit = {},
    onDeleteQris: () -> Unit,
    onSaveOpeningCapital: (Long) -> Unit,
    onSavePreviousSales: (amount: Long, date: String, note: String) -> Unit,
    onSaveReceiptFooter: (String) -> Unit,
    onUpdateThemeMode: (String) -> Unit,
    onExportBackup: () -> Unit,
    onResetSettingsOnly: () -> Unit,
    onResetAllData: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var businessNameInput by remember { mutableStateOf(currentBusinessName) }
    var addressInput by remember { mutableStateOf(currentAddress) }
    var phoneInput by remember { mutableStateOf(currentPhone) }
    var receiptFooterInput by remember { mutableStateOf(currentReceiptFooter) }

    var openingCapitalInput by remember { mutableStateOf(if (openingCapital > 0L) openingCapital.toString() else "") }
    var previousSalesInput by remember { mutableStateOf(if (previousSales > 0L) previousSales.toString() else "") }
    var previousSalesDateInput by remember { mutableStateOf(previousSalesDate) }
    var previousSalesNoteInput by remember { mutableStateOf(previousSalesNote) }

    val context = LocalContext.current
    var rawBitmapForCrop by remember { mutableStateOf<Bitmap?>(null) }
    var showCropDialog by remember { mutableStateOf(false) }

    var showDeleteQrisConfirm by remember { mutableStateOf(false) }
    var showResetSettingsConfirm by remember { mutableStateOf(false) }
    var showResetAllDataConfirm by remember { mutableStateOf(false) }

    // Photo picker for QRIS
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                try {
                    val bmp = context.contentResolver.openInputStream(uri)?.use {
                        BitmapFactory.decodeStream(it)
                    }
                    if (bmp != null) {
                        rawBitmapForCrop = bmp
                        showCropDialog = true
                    } else {
                        onUploadQris(uri)
                    }
                } catch (_: Exception) {
                    onUploadQris(uri)
                }
            }
        }
    )

    // Load QRIS Bitmap
    val qrisBitmap = remember(qrisImagePath) {
        if (qrisImagePath.isNotBlank()) {
            val file = File(qrisImagePath)
            if (file.exists() && file.length() > 0) {
                BitmapFactory.decodeFile(file.absolutePath)?.asImageBitmap()
            } else null
        } else null
    }

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
                                .testTag("button_back_from_settings")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Kembali ke Beranda",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Column {
                            Text(
                                text = "Pengaturan",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                letterSpacing = (-0.3).sp
                            )
                            Text(
                                text = "Kelola data usaha & preferensi kasir",
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

            // =============================================================
            // 1. IDENTITAS USAHA
            // =============================================================
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Store,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(KelolaSpacing.Space2))
                            Text(
                                text = "IDENTITAS USAHA & TOKO",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                letterSpacing = 0.6.sp
                            )
                        }

                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))

                        OutlinedTextField(
                            value = businessNameInput,
                            onValueChange = { businessNameInput = it },
                            label = { Text("Nama Usaha / Toko") },
                            placeholder = { Text("Misal: Warung Kelola Berkah") },
                            shape = KelolaRadius.ShapeInput,
                            colors = inputColors,
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("input_business_name")
                        )

                        OutlinedTextField(
                            value = addressInput,
                            onValueChange = { addressInput = it },
                            label = { Text("Alamat Usaha (Opsional)") },
                            placeholder = { Text("Misal: Jl. Raya Pasar Baru No. 12") },
                            shape = KelolaRadius.ShapeInput,
                            colors = inputColors,
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("input_business_address")
                        )

                        OutlinedTextField(
                            value = phoneInput,
                            onValueChange = { phoneInput = it },
                            label = { Text("Nomor Telepon / WhatsApp (Opsional)") },
                            placeholder = { Text("Misal: 0812-3456-7890") },
                            shape = KelolaRadius.ShapeInput,
                            colors = inputColors,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("input_business_phone")
                        )

                        Button(
                            onClick = {
                                onSaveBusinessInfo(businessNameInput, addressInput, phoneInput)
                            },
                            shape = KelolaRadius.ShapeInput,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("button_save_business_info")
                        ) {
                            Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(KelolaSpacing.Space2))
                            Text("Simpan Identitas Usaha", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    }
                }
            }

            // =============================================================
            // 2. PEMBAYARAN & QRIS
            // =============================================================
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Payments,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(KelolaSpacing.Space2))
                            Text(
                                text = "METODE PEMBAYARAN & QRIS",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                letterSpacing = 0.6.sp
                            )
                        }

                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))

                        // Default Payment Selection
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                text = "Metode Pembayaran Bawaan Kasir",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Metode ini langsung terpilih saat kasir membuka dialog pembayaran.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                listOf("Tunai", "QRIS").forEach { method ->
                                    val isSelected = defaultPaymentMethod == method
                                    FilterChip(
                                        selected = isSelected,
                                        onClick = { onSaveDefaultPaymentMethod(method) },
                                        shape = KelolaRadius.ShapeSmall,
                                        label = {
                                            Text(
                                                text = method,
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                            )
                                        },
                                        leadingIcon = {
                                            Icon(
                                                imageVector = if (method == "Tunai") Icons.Default.LocalAtm else Icons.Default.QrCode,
                                                contentDescription = null,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        },
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                                            selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary,
                                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                            labelColor = MaterialTheme.colorScheme.onSurface,
                                            iconColor = MaterialTheme.colorScheme.onSurfaceVariant
                                        ),
                                        modifier = Modifier
                                            .heightIn(min = 44.dp)
                                            .testTag("chip_default_payment_$method")
                                    )
                                }
                            }
                        }

                        // Warning if QRIS is default but not uploaded
                        if (defaultPaymentMethod == "QRIS" && qrisImagePath.isBlank()) {
                            Surface(
                                shape = KelolaRadius.ShapeInput,
                                color = WarningContainer,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.HourglassTop,
                                        contentDescription = null,
                                        tint = WarningAmber,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = "QRIS belum diatur. Tambahkan gambar QRIS terlebih dahulu di bawah ini.",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }

                        // QRIS Toko Card & Preview
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                text = "Gambar QRIS Merchant Toko",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            if (qrisBitmap != null) {
                                Surface(
                                    shape = KelolaRadius.ShapeInput,
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(
                                        modifier = Modifier.padding(14.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        Image(
                                            bitmap = qrisBitmap,
                                            contentDescription = "QRIS Merchant",
                                            modifier = Modifier
                                                .size(180.dp)
                                                .clip(KelolaRadius.ShapeInput),
                                            contentScale = ContentScale.Fit
                                        )

                                        Row(
                                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            OutlinedButton(
                                                onClick = {
                                                    if (qrisImagePath.isNotBlank()) {
                                                        val file = File(qrisImagePath)
                                                        if (file.exists()) {
                                                            val bmp = BitmapFactory.decodeFile(file.absolutePath)
                                                            if (bmp != null) {
                                                                rawBitmapForCrop = bmp
                                                                showCropDialog = true
                                                            }
                                                        }
                                                    }
                                                },
                                                shape = KelolaRadius.ShapeInput,
                                                colors = ButtonDefaults.outlinedButtonColors(
                                                    contentColor = MaterialTheme.colorScheme.primary
                                                ),
                                                modifier = Modifier.height(44.dp)
                                            ) {
                                                Icon(Icons.Default.Crop, contentDescription = null, modifier = Modifier.size(16.dp))
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text("Potong", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                                            }

                                            OutlinedButton(
                                                onClick = {
                                                    photoPickerLauncher.launch(
                                                        androidx.activity.result.PickVisualMediaRequest(
                                                            ActivityResultContracts.PickVisualMedia.ImageOnly
                                                        )
                                                    )
                                                },
                                                shape = KelolaRadius.ShapeInput,
                                                colors = ButtonDefaults.outlinedButtonColors(
                                                    contentColor = MaterialTheme.colorScheme.primary
                                                ),
                                                modifier = Modifier.height(44.dp)
                                            ) {
                                                Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text("Ganti", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                                            }

                                            OutlinedButton(
                                                onClick = { showDeleteQrisConfirm = true },
                                                colors = ButtonDefaults.outlinedButtonColors(contentColor = DangerRed),
                                                shape = KelolaRadius.ShapeInput,
                                                modifier = Modifier.height(44.dp)
                                            ) {
                                                Icon(Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(16.dp))
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text("Hapus", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                                            }
                                        }
                                    }
                                }
                            } else {
                                Button(
                                    onClick = {
                                        photoPickerLauncher.launch(
                                            androidx.activity.result.PickVisualMediaRequest(
                                                ActivityResultContracts.PickVisualMedia.ImageOnly
                                            )
                                        )
                                    },
                                    shape = KelolaRadius.ShapeInput,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.primary,
                                        contentColor = MaterialTheme.colorScheme.onPrimary
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp)
                                        .testTag("button_upload_qris")
                                ) {
                                    Icon(Icons.Default.Upload, contentDescription = null, modifier = Modifier.size(20.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Pilih Gambar QRIS dari Galeri", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                }
                            }
                        }
                    }
                }
            }

            // =============================================================
            // 3. MODAL AWAL & SALDO
            // =============================================================
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AccountBalanceWallet,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(KelolaSpacing.Space2))
                            Text(
                                text = "MODAL AWAL KASIR",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                letterSpacing = 0.6.sp
                            )
                        }

                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))

                        OutlinedTextField(
                            value = openingCapitalInput,
                            onValueChange = { openingCapitalInput = it.filter { c -> c.isDigit() } },
                            label = { Text("Modal Kasir Awal Hari Ini") },
                            placeholder = { Text("0") },
                            prefix = { Text("Rp ", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            shape = KelolaRadius.ShapeInput,
                            colors = inputColors,
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("input_opening_capital")
                        )

                        Button(
                            onClick = {
                                val amount = openingCapitalInput.toLongOrNull() ?: 0L
                                onSaveOpeningCapital(amount)
                            },
                            shape = KelolaRadius.ShapeInput,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("button_save_opening_capital")
                        ) {
                            Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(KelolaSpacing.Space2))
                            Text("Simpan Modal Awal", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    }
                }
            }

            // =============================================================
            // 4. PENGATURAN STRUK
            // =============================================================
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.ReceiptLong,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(KelolaSpacing.Space2))
                            Text(
                                text = "CATATAN STRUK PEMBELIAN",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                letterSpacing = 0.6.sp
                            )
                        }

                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))

                        OutlinedTextField(
                            value = receiptFooterInput,
                            onValueChange = { receiptFooterInput = it },
                            label = { Text("Pesan Bawah Struk (Footer)") },
                            placeholder = { Text("Terima kasih atas kunjungan Anda!") },
                            shape = KelolaRadius.ShapeInput,
                            colors = inputColors,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = { onSaveReceiptFooter(receiptFooterInput) },
                            shape = KelolaRadius.ShapeInput,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                        ) {
                            Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(KelolaSpacing.Space2))
                            Text("Simpan Pesan Struk", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    }
                }
            }

            // =============================================================
            // 5. TEMA & TAMPILAN
            // =============================================================
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.BrightnessMedium,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(KelolaSpacing.Space2))
                            Text(
                                text = "TEMA & TAMPILAN APLIKASI",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                letterSpacing = 0.6.sp
                            )
                        }

                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))

                        val modes = listOf(
                            Triple("LIGHT", "Mode Terang", Icons.Default.LightMode),
                            Triple("DARK", "Mode Gelap", Icons.Default.DarkMode),
                            Triple("SYSTEM", "Ikuti Sistem Perangkat", Icons.Default.BrightnessMedium)
                        )

                        modes.forEach { (mode, label, icon) ->
                            val isSelected = themeMode == mode
                            Surface(
                                shape = KelolaRadius.ShapeInput,
                                color = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(KelolaRadius.ShapeInput)
                                    .clickable { onUpdateThemeMode(mode) }
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = icon,
                                            contentDescription = null,
                                            tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.size(22.dp)
                                        )
                                        Spacer(modifier = Modifier.width(14.dp))
                                        Text(
                                            text = label,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                    RadioButton(
                                        selected = isSelected,
                                        onClick = { onUpdateThemeMode(mode) },
                                        colors = RadioButtonDefaults.colors(
                                            selectedColor = MaterialTheme.colorScheme.primary,
                                            unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // =============================================================
            // 5b. UKURAN TAMPILAN LAYAR (VIEWPORT)
            // =============================================================
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.PhoneAndroid,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(KelolaSpacing.Space2))
                            Text(
                                text = "UKURAN TAMPILAN LAYAR (VIEWPORT)",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                letterSpacing = 0.6.sp
                            )
                        }

                        Text(
                            text = "Sesuaikan lebar tampilan layar untuk simulasi dan kenyamanan antarmuka perangkat:",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))

                        val viewportOptions = listOf(
                            Triple("360dp", "Ringkas (360dp)", "Layar kecil / kompak"),
                            Triple("412dp", "Standar (412dp)", "Standar Google Pixel / Galaxy"),
                            Triple("430dp", "Lega (430dp)", "Layar lebar / Max / Plus")
                        )

                        viewportOptions.forEach { (widthValue, title, desc) ->
                            val isSelected = viewportWidth == widthValue
                            Surface(
                                shape = KelolaRadius.ShapeInput,
                                color = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(KelolaRadius.ShapeInput)
                                    .clickable { onSaveViewportWidth(widthValue) }
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = title,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        Text(
                                            text = desc,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    RadioButton(
                                        selected = isSelected,
                                        onClick = { onSaveViewportWidth(widthValue) },
                                        colors = RadioButtonDefaults.colors(
                                            selectedColor = MaterialTheme.colorScheme.primary,
                                            unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // =============================================================
            // 6. CADANGAN & RESET DATA
            // =============================================================
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Security,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(KelolaSpacing.Space2))
                            Text(
                                text = "MANAJEMEN DATA & PEMELIHARAAN",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                letterSpacing = 0.6.sp
                            )
                        }

                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))

                        // Export
                        Button(
                            onClick = onExportBackup,
                            shape = KelolaRadius.ShapeInput,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                contentColor = MaterialTheme.colorScheme.onSurface
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                        ) {
                            Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Cadangkan Data (Export JSON)", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }

                        // Reset Pengaturan Saja
                        Surface(
                            onClick = { showResetSettingsConfirm = true },
                            shape = KelolaRadius.ShapeInput,
                            color = WarningAmber.copy(alpha = 0.12f),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = null,
                                    tint = WarningAmber,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Reset Pengaturan Saja",
                                    color = WarningAmber,
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }

                        // Reset Seluruh Database
                        Surface(
                            onClick = { showResetAllDataConfirm = true },
                            shape = KelolaRadius.ShapeInput,
                            color = DangerRed.copy(alpha = 0.12f),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = null,
                                    tint = DangerRed,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Hapus Semua Data Transaksi & Produk",
                                    color = DangerRed,
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }

            // Info Footer
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = KelolaSpacing.Space3),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Kelola • Versi 1.0 (Offline Local POS)",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(KelolaSpacing.Space6).navigationBarsPadding())
            }
        }
    }

    // Confirmation Dialogs
    if (showDeleteQrisConfirm) {
        ConfirmationDialog(
            title = "Hapus Gambar QRIS?",
            message = "Gambar QRIS akan dihapus dari penyimpanan perangkat. Transaksi QRIS tetap bisa dilakukan tanpa tampilan kode QR.",
            confirmText = "Hapus",
            isDestructive = true,
            onConfirm = {
                showDeleteQrisConfirm = false
                onDeleteQris()
            },
            onDismiss = { showDeleteQrisConfirm = false }
        )
    }

    if (showResetSettingsConfirm) {
        ConfirmationDialog(
            title = "Reset Pengaturan ke Bawaan?",
            message = "Pengaturan nama toko, QRIS, tema, dan data awal akan dikembalikan ke default. Riwayat transaksi, kasir, dan daftar produk TETAP AMAN dan TIDAK akan dihapus.",
            confirmText = "Reset Pengaturan",
            isDestructive = false,
            onConfirm = {
                showResetSettingsConfirm = false
                onResetSettingsOnly()
            },
            onDismiss = { showResetSettingsConfirm = false }
        )
    }

    if (showResetAllDataConfirm) {
        ConfirmationDialog(
            title = "Hapus Seluruh Database?",
            message = "PERINGATAN: Semua riwayat transaksi kasir, hutang kasbon, dan pengeluaran akan dihapus bersih. Tindakan ini tidak dapat dibatalkan.",
            confirmText = "Hapus Seluruh Data",
            isDestructive = true,
            onConfirm = {
                showResetAllDataConfirm = false
                onResetAllData()
            },
            onDismiss = { showResetAllDataConfirm = false }
        )
    }

    if (showCropDialog && rawBitmapForCrop != null) {
        QrisCropDialog(
            rawBitmap = rawBitmapForCrop!!,
            onConfirmCrop = { cropped ->
                showCropDialog = false
                rawBitmapForCrop = null
                onSaveCroppedQris(cropped)
            },
            onDismiss = {
                showCropDialog = false
                rawBitmapForCrop = null
            }
        )
    }
}
