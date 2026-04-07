# Spesifikasi Project C - Sistem Pembayaran Digital (E-Wallet)

Dokumen ini merangkum spesifikasi resmi berdasarkan gambar yang diberikan, termasuk struktur class minimum, fitur, dan rubrik penilaian per milestone.

## 1) Deskripsi Project
Mahasiswa membuat sistem pembayaran digital sederhana berbasis CLI yang mensimulasikan e-wallet seperti GoPay, OVO, atau DANA.

Sistem mendukung berbagai metode pembayaran dengan perhitungan biaya (fee) berbeda-beda.

## 2) Konsep OOP yang Wajib Digunakan

| Konsep OOP | Implementasi | Contoh di Project |
|---|---|---|
| Encapsulation | Atribut `private` + getter/setter | `balance`, `transactionHistory` hanya via method |
| Inheritance | Hierarki `User` + `Payment` | `BankTransfer`, `QRPayment` extends `Payment` |
| Polymorphism | Override method per jenis pembayaran | `processPayment(Payment p)` |
| Abstract | Abstract class + abstract method | `User` dan `Payment` (di tahap abstract) |

## 3) Struktur Class Minimum

### 3.1 `User` (di awal sebagai class, lalu jadi abstract class pada milestone lanjut)
Atribut (private):
- `String userId`
- `String name`
- `String phone`
- `double balance`
- `List<Transaction> transactionHistory`

Method konkret:
- `void topUp(double amount)`
- `boolean hasSufficientBalance(double amount)`
- `void showBalance()`
- `void showTransactionHistory()`

Abstract method (untuk fase abstract):
- `double getTransactionLimit()`
- `double getCashbackRate()`
- `String getAccountType()`

### 3.2 Turunan `User`
- `RegularUser extends User`
  - `getTransactionLimit()`: Rp2.000.000 per transaksi
  - `getCashbackRate()`: 1%
- `PremiumUser extends User`
  - `getTransactionLimit()`: Rp10.000.000 per transaksi
  - `getCashbackRate()`: 5% (maks Rp50.000)
- `MerchantUser extends User`
  - `getTransactionLimit()`: Rp50.000.000 per transaksi
  - `getCashbackRate()`: 0%
  - Keunikan: bisa menerima pembayaran dari user lain

### 3.3 `Payment` (parent class, lalu jadi abstract class)
Atribut (private):
- `String paymentId`
- `double amount`
- `User sender`
- `User receiver`
- `String status` (`PENDING` / `SUCCESS` / `FAILED`)

Method konkret:
- `void execute()` (validasi + proses)
- `void printReceipt()`

Abstract method:
- `double calculateFee()`
- `boolean validate()`
- `String getPaymentMethod()`

### 3.4 Turunan `Payment`
- `BankTransfer extends Payment`
  - Fee: flat Rp2.500/transaksi
  - Validasi: cek rekening + saldo cukup
  - Proses: simulasi PIN
- `QRPayment extends Payment`
  - Fee: gratis `< Rp100.000`, 0.7% jika di atasnya
  - Validasi: cek QR valid + saldo cukup
  - Proses: scan QR (simulasi input kode)
- `WalletTransfer extends Payment`
  - Fee: gratis antar user
  - Validasi: nomor HP terdaftar + saldo cukup
  - Proses: transfer instan

### 3.5 `Transaction`
Menyimpan record transaksi (timestamp, amount, type, status) dan dipakai untuk riwayat transaksi.

### 3.6 `EWalletApp` (Main)
Menu minimum:
- Login
- Top Up
- Transfer ke User
- Bayar Merchant
- Riwayat Transaksi
- Exit

Gunakan polymorphism saat memproses berbagai jenis pembayaran.

## 4) Fitur Minimum
- Minimal 2 jenis user (Regular, Premium) dengan limit & cashback berbeda
- Minimal 3 metode pembayaran dengan fee berbeda
- Sistem saldo (`topUp`, `deduct`) ter-encapsulate
- Riwayat transaksi per user
- Validasi saldo cukup, limit transaksi, input valid

## 5) Fitur Bonus (Nilai Tambah)
- Sistem promo/diskon (`abstract class Promo` -> `Cashback`, `Discount`, `FreeTransfer`)
- Fitur split bill
- PIN/password keamanan
- Laporan keuangan (total pengeluaran bulanan)
- Save/load data user ke file

---

## 6) Timeline & Rubrik Penilaian Milestone

## Milestone 1
### Target
- Buat class `User` dengan atribut `userId`, `name`, `phone`, `balance`
- Constructor inisialisasi
- Method `topUp(double)`, `pay(double)`, `showBalance()`
- Di `Main`: minimal 2 object `User`, lakukan `topUp` dan `pay`

### Rubrik (Total 10 poin)
| Kriteria | Indikator | Poin |
|---|---|---:|
| Class dibuat dengan benar | Ada atribut + constructor | 3 |
| Method berfungsi | Semua method bisa dipanggil tanpa error | 3 |
| Object berhasil dibuat di Main | Minimal 2 object, interaksi antar object | 2 |
| Output jelas dan rapi | Output menunjukkan state object berubah | 2 |

## Milestone 2
### Target
- Semua atribut `User` menjadi `private`
- Validasi: balance tidak negatif, `topUp` positif, `pay > 0 && <= balance`
- Tambah class `Transaction` (timestamp, amount, type, status)
- Setiap `topUp`/`pay` otomatis membuat `Transaction` dan masuk `List<Transaction>`
- Tambah `showTransactionHistory()`
- Input validation pada menu

### Rubrik (Total 15 poin)
| Kriteria | Indikator | Poin |
|---|---|---:|
| Semua atribut private | Tidak ada atribut public/default | 3 |
| Getter/Setter lengkap | Setiap atribut punya accessor sesuai | 3 |
| Validasi pada setter/method | Input invalid ditolak dengan pesan jelas | 4 |
| Fitur baru berfungsi | Loop/timePasses/Transaction berjalan | 3 |
| Code quality | Penamaan jelas, konsisten, ada komentar | 2 |

## Milestone 3
### Target
- `User` menjadi parent class
- Buat `RegularUser`, `PremiumUser`, `MerchantUser` extends `User`
- Buat `Payment` parent class + subclass `BankTransfer`, `QRPayment`, `WalletTransfer`
- Integrasi subclass di `Main` (menu pilih jenis user)

### Rubrik (Total 20 poin)
| Kriteria | Indikator | Poin |
|---|---|---:|
| Hierarki class benar | `extends` tepat, relasi IS-A logis | 5 |
| Constructor chaining (`super`) | Subclass memanggil `super()` | 4 |
| Minimal 3 subclass | Masing-masing punya atribut/method tambahan | 4 |
| Subclass terintegrasi di Main | Menu pilih jenis, program berjalan | 4 |
| Encapsulation tetap terjaga | Milestone 2 tidak rusak | 3 |

## Milestone 4
### Target
- Override `calculateFee()` di tiap subclass `Payment`
  - `BankTransfer`: flat Rp2.500
  - `QRPayment`: gratis < Rp100.000, 0.7% di atasnya
  - `WalletTransfer`: gratis
- Override `validate()` per metode bayar
- Override `getCashbackRate()` & `getTransactionLimit()` per jenis user
- `processPayment(Payment p)` untuk polymorphism

### Rubrik (Total 20 poin)
| Kriteria | Indikator | Poin |
|---|---|---:|
| Method overriding benar | `@Override`, behavior beda tiap subclass | 6 |
| Polymorphic reference digunakan | Variabel parent berisi object subclass | 5 |
| Dynamic dispatch terlihat | Method dipanggil sesuai tipe runtime | 5 |
| Milestone sebelumnya utuh | Encapsulation + inheritance tidak rusak | 4 |

## Milestone 5
### Target
- Ubah `User` menjadi `abstract class User`
- Method abstract: `getTransactionLimit()`, `getCashbackRate()`, `getAccountType()`
- Ubah `Payment` menjadi `abstract class Payment`
- Method abstract: `calculateFee()`, `validate()`, `getPaymentMethod()`
- Semua subclass wajib implement abstract method
- Dokumentasi: alasan `User` & `Payment` dijadikan abstract

### Pertanyaan refleksi (wajib laporan)
- Apa yang berubah di kode setelah refactor ke abstract? Apakah banyak berubah?
- Apa yang terjadi jika subclass baru dibuat tapi lupa implement abstract method?
- Apa keuntungan abstract class dibanding class biasa sebagai parent?
- Dalam situasi apa sebaiknya tidak menggunakan abstract class?

### Rubrik (Total 20 poin)
| Kriteria | Indikator | Poin |
|---|---|---:|
| Abstract class benar | Class ditandai abstract, tidak bisa di-instansiasi | 5 |
| Abstract method benar | Minimal 2 abstract method, semua subclass implement | 5 |
| Refactoring bersih | Program tetap berjalan, tidak ada regresi | 4 |
| Pertanyaan refleksi dijawab | Jawaban menunjukkan pemahaman konsep | 3 |
| Abstract class tambahan (bonus) | `Item`/`Food`/`Promo` abstract class | 3 |

## Milestone 6
### Target
- Integrasi seluruh fitur M1–M5
- Tambah minimal 1 fitur bonus
- Persiapan laporan dan demo/presentasi

### Rubrik (Total 15 poin)
| Kriteria | Indikator | Poin |
|---|---|---:|
| Program utuh & berjalan | Semua fitur M1–M5 terintegrasi, no error | 4 |
| Laporan lengkap | Class diagram + penjelasan OOP + screenshot | 3 |
| Demo/Presentasi | Bisa menjelaskan kode sendiri, menjawab pertanyaan | 4 |
| Fitur bonus | Implementasi minimal 1 fitur bonus | 2 |
| Code quality keseluruhan | Penamaan, struktur, komentar, konsistensi | 2 |

---

## 7) Contoh Output Program (ringkas)

```text
=== DIGITAL WALLET SYSTEM ===
Login sebagai: Ahmad (Premium User)
Saldo: Rp 500.000

Menu:
1. Top Up
2. Transfer ke User
3. Bayar Merchant
4. Riwayat Transaksi
5. Logout
```

## 8) Catatan Implementasi
- Kerjakan bertahap per milestone agar aman saat penilaian.
- Pastikan milestone sebelumnya tidak rusak ketika lanjut milestone berikutnya.
- Simpan bukti screenshot output sesuai rubrik.
