<div align="center">

  <img src="docs/assets/logo.png" alt="Logo Kelola" width="130" style="border-radius: 24px; box-shadow: 0 8px 24px rgba(0, 73, 116, 0.15);" />

  # 💼 Kelola (Kasir & Keuangan Kampus)

  **Aplikasi Kasir Sederhana, Cepat, dan 100% Offline untuk Mahasiswa Pejuang Usaha di Kampus**

  [![Android](https://img.shields.io/badge/Platform-Android_7.0+-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com)
  [![Kotlin](https://img.shields.io/badge/Kotlin-2.0+-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org)
  [![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack_Compose_Material3-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
  [![Room Database](https://img.shields.io/badge/Database-Room_Local--First-FF6F00?style=for-the-badge&logo=sqlite&logoColor=white)](https://developer.android.com/training/data-storage/room)
  [![License](https://img.shields.io/badge/License-MIT-006199?style=for-the-badge)](LICENSE)

  <br />

  <p align="center">
    <a href="#-tentang-kelola">Tentang Kelola</a> •
    <a href="#-fitur-unggulan">Fitur Unggulan</a> •
    <a href="#-sistem-penyimpanan-data">Sistem Penyimpanan</a> •
    <a href="#%EF%B8%8F-teknologi">Teknologi</a> •
    <a href="#-cara-instalasi">Instalasi</a> •
    <a href="#-panduan-build">Build</a>
  </p>

</div>

---

## 📖 Tentang Kelola

Banyak mahasiswa memulai usaha di lingkungan kampus—mulai dari berjualan makanan ringan di kelas, minuman dingin saat kepanitiaan, sistem pre-order (PO) makanan, pulsa & paket data, hingga merchandise organisasi. Namun, transaksi di kampus memiliki tantangan nyata:

* 📶 **Sinyal Kampus Sering Drop**: Berada di gedung bertingkat, lorong, atau basement sering membuat aplikasi kasir berbasis cloud gagal memuat atau macet di tengah transaksi.
* 🤝 **Teman Sering "Kasbon"**: Budaya titip beli atau *"bayar nanti pas selesai kelas ya"* sering berakhir lupa dan bikin uang modal jualan nombok.
* 🪙 **Uang Pecahan Kembalian Kurang**: Saat istirahat singkat antar-mata kuliah, antrean cepat sering terhambat uang kembalian pecahan kecil yang belum tersedia di dompet.
* 💸 **Aplikasi POS Lain Mahal & Rumit**: Aplikasi kasir umum mengharuskan langganan bulanan, wajib registrasi email/nomor HP, serta penuh fitur rumit yang tidak dibutuhkan pedagang kecil.

**Kelola** dirancang khusus untuk memecahkan semua masalah tersebut. Mengusung filosofi **Local-First & Zero Latency**, Kelola memberikan pengalaman kasir yang instan, tanpa login, tanpa internet, dan dilengkapi fitur khas mahasiswa seperti *Pelacak Kasbon Teman* dan *Pencatat Kembalian Tertunda*.

---

## ✨ Fitur Unggulan

```
┌────────────────────────────────────────────────────────────────────────┐
│                        CORE FEATURES AT A GLANCE                       │
├───────────────────┬─────────────────────┬──────────────────────────────┤
│ 🛒 KASIR INSTAN   │ 🤝 KASBON & UTANG   │ 🪙 KEMBALIAN TERTUNDA        │
│ Transaksi cepat,  │ Catat nama teman,   │ Simpan pengingat kembalian   │
│ keranjang belanja,│ rincian barang,     │ receh yang belum diberikan,  │
│ tunai & QRIS statis  nominal, & status pelunasan │ lunasi saat uang pas ada   │
├───────────────────┼─────────────────────┼──────────────────────────────┤
│ 📦 MANAJEMEN STOK │ 📊 LAPORAN BISNIS   │ 📐 RESPONSIF MULTI-UKURAN    │
│ Hitung harga modal│ Laba bersih harian, │ Presisi tinggi di layar      │
│ & jual, restock,  │ produk terlaris,    │ 360dp, 412dp, hingga 430dp   │
│ kategori kustom   │ kerugian & arus kas │ dengan Oceanic Design System │
└───────────────────┴─────────────────────┴──────────────────────────────┘
```

### 1. ⚡ Kasir Cepat & Keranjang Pintar
* Tambah produk ke keranjang hanya dengan satu ketukan.
* Tombol kuantitas cepat `+` dan `-` yang responsif tanpa jeda.
* Dukungan diskon transaksi dan catatan pesanan khusus.

### 2. 📱 Tampilan QRIS dengan Pemotong Presisi (Smart Crop)
* Pengguna dapat mengunggah gambar QRIS usaha/pribadi.
* Dilengkapi alat pemotong (*crop*) rasio 1:1 langsung di dalam aplikasi agar gambar fokus hanya pada kode QR, tidak buram, dan rapi saat disodorkan ke pembeli.

### 3. 🤝 Manajemen Kasbon & Piutang Teman
* Laman khusus pelacak piutang/kasbon.
* Rekam siapa nama teman yang meminjam/berhutang, item apa saja yang diambil, dan nomor kontak.
* Beranda menampilkan pengingat total nominal kasbon yang belum lunas.
* Fitur cicilan atau pelunasan sekali klik.

### 4. 🪙 Pengingat Kembalian Belum Diberikan
* Solusi cerdas jika pedagang kehabisan uang pecahan kecil saat transaksi kasir.
* Sistem menyimpan data kembalian yang masih harus dikembalikan ke pembeli.
* Beranda menyediakan kartu pengingat yang langsung mengarahkan ke daftar kembalian aktif.

### 5. 📦 Katalog Produk, Stok, & Kategori Kustom
* Catat harga beli (modal) dan harga jual untuk kalkulasi margin profit otomatis.
* Indikator peringatan jika stok produk menipis atau habis.
* Fitur tambah kategori kustom langsung dari dialog produk dengan tombol `+`.
* Fitur catat barang rusak/hilang (kerugian stok) agar pembukuan tetap akurat.

### 6. 📊 Laporan Keuangan & Produk Terlaris
* **Ringkasan Penjualan**: Pendapatan kotor, laba bersih, dan total transaksi harian.
* **Fitur Produk Terlaris**: Tahu produk mana yang paling laku di kalangan mahasiswa.
* **Arus Kas Masuk & Keluar**: Catat biaya operasional (seperti transportasi, plastik, es batu) dengan menu edit/hapus di setiap baris data.

### 7. 🎨 Tampilan Elegan & Desain Adaptif
* Menggunakan tema modern **Oceanic Modernity** dengan kontras tajam dan tipografi bersih.
* Pengaturan ukuran tampilan (360dp untuk HP compact, 412dp standar, 430dp layar besar) langsung dari menu pengaturan.

---

## 💾 Sistem Penyimpanan Data (Local-First Architecture)

Kelola mengadopsi prinsip **100% Local-First**, di mana kedaulatan data sepenuhnya berada di tangan pengguna:

```
[ Antarmuka Jetpack Compose ]
              ▲
              │ (StateFlow)
              ▼
   [ MainViewModel (MVVM) ]
              ▲
              │ (Coroutines Flow)
              ▼
    [ Room Database (DAOs) ]
              │
    ┌─────────┴─────────┐
    ▼                   ▼
[ SQLite Engine ]   [ Local App Sandbox ]
 (kelola_database)   (/data/data/com.aistudio.kelola.kasir/)
```

### Mengapa Pendekatan Ini Terbaik untuk Mahasiswa?
1. **Zero Data Leakage / 100% Privat**: Data omset, keuntungan, dan daftar pelanggan tidak pernah dikirim ke server luar atau pihak ketiga.
2. **Tanpa Biaya Server & Bebas Selamanya**: Tidak membutuhkan biaya langganan API atau hosting cloud.
3. **Kecepatan Instan (0ms Latency)**: Seluruh operasi baca dan tulis terjadi langsung pada penyimpanan internal perangkat, tanpa ada waktu tunggu (*loading spinner*) jaringan.
4. **Cadangkan & Pulihkan (Backup/Restore)**: Pengguna dapat mengekspor seluruh basis data ke format berkas lokal untuk dipindahkan ke HP baru kapan saja.

---

## 🛠️ Teknologi & Pustaka yang Digunakan

* **Bahasa**: [Kotlin](https://kotlinlang.org/) (Coroutines, StateFlow)
* **Framework UI**: [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3, Single Activity Architecture)
* **Database**: [Android Jetpack Room](https://developer.android.com/training/data-storage/room) (SQLite ORM)
* **Image Handling**: [Coil Compose](https://coil-kt.github.io/coil/) (Asynchronous Image Loading)
* **Build System**: Gradle 9.3.1 (Kotlin DSL `.gradle.kts`)
* **Arsitektur**: MVVM (Model-View-ViewModel) + Reactive Streams

---

## 📥 Cara Instalasi (Siap Pakai)

Anda dapat langsung memasang aplikasi di ponsel Android Anda menggunakan berkas release yang telah disediakan:

1. Unduh berkas **[`Kelola-release.apk`](Kelola-release.apk)** yang ada di repositori ini.
2. Kirim berkas APK ke HP Android Anda (bisa lewat WhatsApp, Telegram, Google Drive, atau kabel USB).
3. Buka File Manager di HP dan ketuk berkas `Kelola-release.apk`.
4. Jika diminta izin keamanan, aktifkan **"Izinkan penginstalan dari sumber ini"**.
5. Tekan **Install** dan aplikasi siap digunakan untuk berjualan!

> **Kebutuhan Sistem Minimum**: Android 7.0 (Nougat / API 24) atau yang lebih baru.

---

## 💻 Panduan Build dari Source Code

Bagi developer yang ingin mengembangkan atau memodifikasi aplikasi ini di komputer lokal:

### Prasyarat
* **Android Studio**: Ladybug / Meerkat / versi terbaru
* **JDK**: Versi 17 atau 21/25 (JBR bawaan Android Studio direkomendasikan)
* **Android SDK**: Platform 36 (Android 16 / VanillaIceCream)

### Langkah-langkah Build
1. **Clone repositori ini**:
   ```bash
   git clone https://github.com/username/Kelola.git
   cd Kelola
   ```

2. **Kompilasi & Jalankan Mode Debug**:
   ```bash
   ./gradlew assembleDebug
   ```

3. **Membuat Berkas APK Release**:
   ```bash
   ./gradlew assembleRelease
   ```
   *Berkas APK release akan dihasilkan pada folder `app/build/outputs/apk/release/app-release.apk`.*

---

## 🤝 Berkontribusi

Kontribusi dari sesama mahasiswa dan komunitas pengembang terbuka lebar!
1. Fork repositori ini
2. Buat branch fitur baru (`git checkout -b fitur/fitur-keren`)
3. Commit perubahan Anda (`git commit -m 'Menambahkan fitur baru'`)
4. Push ke branch (`git push origin fitur/fitur-keren`)
5. Buat **Pull Request**

---

<div align="center">

  Dibuat dengan ❤️ untuk mendukung semangat kemandirian wirausaha mahasiswa Indonesia.

  ⭐ **Suka dengan aplikasi ini? Jangan lupa berikan bintang di GitHub!** ⭐

</div>
