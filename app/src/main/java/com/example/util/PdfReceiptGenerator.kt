package com.example.util

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.data.local.entity.TransactionEntity
import com.example.data.local.entity.TransactionItemEntity
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object PdfReceiptGenerator {

    /**
     * Menghasilkan file PDF struk thermal kasir yang rapi dan elegan.
     */
    fun generateReceiptPdf(
        context: Context,
        transaction: TransactionEntity,
        items: List<TransactionItemEntity>,
        businessName: String,
        businessAddress: String,
        businessPhone: String,
        receiptFooter: String
    ): File? {
        return try {
            val pageWidth = 384 // Lebar standar struk thermal kasir (~58mm/80mm)
            val leftMargin = 20f
            val rightMargin = (pageWidth - 20).toFloat()
            val centerX = (pageWidth / 2).toFloat()

            // Hitung tinggi halaman secara dinamis berdasarkan jumlah item
            val estimatedItemHeight = items.size * 34f
            val baseHeight = 320f + (if (transaction.discount > 0) 24f else 0f) + (if (transaction.paymentMethod == "Tunai") 38f else 0f)
            val totalHeight = (baseHeight + estimatedItemHeight + 60f).toInt().coerceAtLeast(460)

            val document = PdfDocument()
            val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, totalHeight, 1).create()
            val page = document.startPage(pageInfo)
            val canvas: Canvas = page.canvas

            // Background Putih Bersih
            canvas.drawColor(Color.WHITE)

            // Paint Helpers
            val textPaint = Paint().apply {
                isAntiAlias = true
                color = Color.BLACK
                typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL)
            }

            val dashLinePaint = Paint().apply {
                isAntiAlias = true
                color = Color.DKGRAY
                style = Paint.Style.STROKE
                strokeWidth = 1.2f
                pathEffect = DashPathEffect(floatArrayOf(4f, 4f), 0f)
            }

            var currentY = 32f

            // 1. HEADER TOKO (CENTERED)
            textPaint.apply {
                typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
                textSize = 15f
                textAlign = Paint.Align.CENTER
            }
            canvas.drawText(businessName.ifBlank { "Toko Kelola" }, centerX, currentY, textPaint)
            currentY += 16f

            textPaint.apply {
                typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
                textSize = 9.5f
                color = Color.DKGRAY
                textAlign = Paint.Align.CENTER
            }
            if (businessAddress.isNotBlank()) {
                canvas.drawText(businessAddress, centerX, currentY, textPaint)
                currentY += 13f
            }
            if (businessPhone.isNotBlank()) {
                canvas.drawText("Telp: $businessPhone", centerX, currentY, textPaint)
                currentY += 13f
            }
            currentY += 6f

            // Garis Pembatas
            canvas.drawLine(leftMargin, currentY, rightMargin, currentY, dashLinePaint)
            currentY += 16f

            // 2. METADATA TRANSAKSI
            textPaint.apply {
                color = Color.BLACK
                textSize = 9f
                typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL)
                textAlign = Paint.Align.LEFT
            }

            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val dateStr = sdf.format(Date(transaction.createdAt))

            // Baris No. Struk & Tanggal
            canvas.drawText("No: ${transaction.transactionNumber}", leftMargin, currentY, textPaint)
            textPaint.textAlign = Paint.Align.RIGHT
            canvas.drawText(dateStr, rightMargin, currentY, textPaint)
            currentY += 13f

            // Baris Kasir / Pelanggan & Metode
            textPaint.textAlign = Paint.Align.LEFT
            val custStr = if (transaction.customerName.isNotBlank()) "Plg: ${transaction.customerName}" else "Kasir: Toko"
            canvas.drawText(custStr, leftMargin, currentY, textPaint)
            textPaint.textAlign = Paint.Align.RIGHT
            canvas.drawText("Bayar: ${transaction.paymentMethod}", rightMargin, currentY, textPaint)
            currentY += 15f

            // Garis Pembatas
            canvas.drawLine(leftMargin, currentY, rightMargin, currentY, dashLinePaint)
            currentY += 16f

            // 3. DAFTAR ITEM TRANSAKSI
            if (items.isEmpty()) {
                textPaint.textAlign = Paint.Align.LEFT
                canvas.drawText("Item Penjualan", leftMargin, currentY, textPaint)
                textPaint.textAlign = Paint.Align.RIGHT
                canvas.drawText(FormatUtils.formatRupiah(transaction.total), rightMargin, currentY, textPaint)
                currentY += 18f
            } else {
                items.forEach { item ->
                    // Nama Produk (Baris 1)
                    textPaint.apply {
                        typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
                        textAlign = Paint.Align.LEFT
                        textSize = 9.5f
                    }
                    val maxChars = 34
                    val productName = if (item.productNameSnapshot.length > maxChars) {
                        item.productNameSnapshot.take(maxChars - 2) + ".."
                    } else {
                        item.productNameSnapshot
                    }
                    canvas.drawText(productName, leftMargin, currentY, textPaint)
                    currentY += 13f

                    // Qty x Harga Satuan & Subtotal Baris (Baris 2)
                    textPaint.apply {
                        typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL)
                        textSize = 9f
                    }
                    val qtyPriceStr = "  ${item.quantity}x @${FormatUtils.formatRupiah(item.sellingPriceSnapshot)}"
                    canvas.drawText(qtyPriceStr, leftMargin, currentY, textPaint)

                    textPaint.textAlign = Paint.Align.RIGHT
                    canvas.drawText(FormatUtils.formatRupiah(item.subtotal), rightMargin, currentY, textPaint)
                    currentY += 15f
                }
            }

            // Garis Pembatas
            canvas.drawLine(leftMargin, currentY, rightMargin, currentY, dashLinePaint)
            currentY += 16f

            // 4. TOTAL DAN KEMBALIAN
            textPaint.apply {
                typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL)
                textSize = 9.5f
                textAlign = Paint.Align.LEFT
            }

            // Subtotal
            canvas.drawText("Subtotal", leftMargin, currentY, textPaint)
            textPaint.textAlign = Paint.Align.RIGHT
            canvas.drawText(FormatUtils.formatRupiah(transaction.subtotal), rightMargin, currentY, textPaint)
            currentY += 14f

            // Diskon
            if (transaction.discount > 0) {
                textPaint.textAlign = Paint.Align.LEFT
                canvas.drawText("Diskon", leftMargin, currentY, textPaint)
                textPaint.textAlign = Paint.Align.RIGHT
                canvas.drawText("-${FormatUtils.formatRupiah(transaction.discount)}", rightMargin, currentY, textPaint)
                currentY += 14f
            }

            // TOTAL AKHIR (Besar & Tebal)
            textPaint.apply {
                typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
                textSize = 11.5f
                textAlign = Paint.Align.LEFT
            }
            canvas.drawText("TOTAL", leftMargin, currentY, textPaint)
            textPaint.textAlign = Paint.Align.RIGHT
            canvas.drawText(FormatUtils.formatRupiah(transaction.total), rightMargin, currentY, textPaint)
            currentY += 16f

            // Tunai & Kembalian (Jika Tunai)
            if (transaction.paymentMethod == "Tunai") {
                textPaint.apply {
                    typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL)
                    textSize = 9.5f
                    textAlign = Paint.Align.LEFT
                }
                canvas.drawText("Tunai Diterima", leftMargin, currentY, textPaint)
                textPaint.textAlign = Paint.Align.RIGHT
                canvas.drawText(FormatUtils.formatRupiah(transaction.cashReceived), rightMargin, currentY, textPaint)
                currentY += 13f

                textPaint.textAlign = Paint.Align.LEFT
                canvas.drawText("Kembalian", leftMargin, currentY, textPaint)
                textPaint.textAlign = Paint.Align.RIGHT
                canvas.drawText(FormatUtils.formatRupiah(transaction.change), rightMargin, currentY, textPaint)
                currentY += 15f
            }

            // Garis Pembatas
            canvas.drawLine(leftMargin, currentY, rightMargin, currentY, dashLinePaint)
            currentY += 18f

            // 5. FOOTER TOKO
            textPaint.apply {
                typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
                textSize = 9f
                color = Color.DKGRAY
                textAlign = Paint.Align.CENTER
            }
            val footerText = receiptFooter.ifBlank { "Terima kasih atas kunjungan Anda!" }
            canvas.drawText(footerText, centerX, currentY, textPaint)
            currentY += 13f

            textPaint.textSize = 8f
            canvas.drawText("Struk resmi dicetak via Aplikasi Kelola", centerX, currentY, textPaint)

            document.finishPage(page)

            // Simpan ke Cache
            val cacheReceiptsDir = File(context.cacheDir, "receipts")
            if (!cacheReceiptsDir.exists()) {
                cacheReceiptsDir.mkdirs()
            }
            val cleanTxNum = transaction.transactionNumber.replace(Regex("[^a-zA-Z0-9_-]"), "_")
            val pdfFile = File(cacheReceiptsDir, "Struk_$cleanTxNum.pdf")

            val fos = FileOutputStream(pdfFile)
            document.writeTo(fos)
            fos.flush()
            fos.close()
            document.close()

            pdfFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Membagikan struk PDF ke WhatsApp, Telegram, Gmail, atau aplikasi lain.
     */
    fun shareReceiptPdf(
        context: Context,
        transaction: TransactionEntity,
        items: List<TransactionItemEntity>,
        businessName: String,
        businessAddress: String,
        businessPhone: String,
        receiptFooter: String
    ) {
        val pdfFile = generateReceiptPdf(
            context,
            transaction,
            items,
            businessName,
            businessAddress,
            businessPhone,
            receiptFooter
        )

        if (pdfFile == null || !pdfFile.exists()) {
            Toast.makeText(context, "Gagal membuat dokumen PDF struk.", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val contentUri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                pdfFile
            )

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "application/pdf"
                putExtra(Intent.EXTRA_STREAM, contentUri)
                putExtra(
                    Intent.EXTRA_SUBJECT,
                    "Struk Pembelian ${transaction.transactionNumber}"
                )
                putExtra(
                    Intent.EXTRA_TEXT,
                    "Berikut adalah struk transaksi ${transaction.transactionNumber} dari ${businessName.ifBlank { "Toko Kelola" }}."
                )
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            val chooser = Intent.createChooser(shareIntent, "Bagikan Struk via...")
            chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(chooser)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Gagal membagikan struk: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
