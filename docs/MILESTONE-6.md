# Milestone 6 - Integrasi Final + Bonus

## Target Fitur

- Sistem promo (`abstract class Promo` -> `CashbackPromo`, `DiscountPromo`)
- Fitur split bill
- PIN/password keamanan (default `1234`, verifikasi saat login & transaksi sensitif)
- Laporan keuangan bulanan (`showMonthlyFinancialReport()`)
- Save/load data user ke file `data/users_data.txt`

## Menu M6

| No | Fitur                   |
| -- | ----------------------- |
| 1  | Login + PIN             |
| 2  | Lihat Saldo             |
| 3  | Top Up (PIN)            |
| 4  | Bayar / Transfer + Promo|
| 5  | Riwayat Transaksi       |
| 6  | Split Bill              |
| 7  | Laporan Keuangan Bulanan|
| 8  | Simpan Data             |
| 9  | Muat Data               |
| 10 | Logout                  |
| 0  | Exit                    |

## Struktur Class Tambahan (M6)

```
com.payment.models.promo
├── Promo (abstract)
├── CashbackPromo
└── DiscountPromo

com.payment.services
└── UserDataStorage
```

## Kriteria Penilaian (Total 15)

| Komponen                         | Poin |
| -------------------------------- | ---- |
| Program utuh & berjalan (M1-M5 terintegrasi, no error) | 4    |
| Laporan lengkap (class diagram + penjelasan OOP + screenshot) | 3    |
| Demo/presentasi (menjelaskan kode sendiri, menjawab pertanyaan) | 4    |
| Fitur bonus (minimal 1 fitur bonus) | 2    |
| Code quality keseluruhan (penamaan, struktur, komentar, konsistensi) | 2    |

## Detail Implementasi

### 1. Sistem Promo

#### Promo (Abstract Class)
- Abstract class parent untuk semua jenis promo
- Abstract methods:
  - `getPromoType()` — return tipe promo (String)
  - `isApplicable(double amount)` — cek apakah promo applicable
  - `adjustAmount(double amount)` — adjust nominal pembayaran
  - `adjustFee(double fee)` — adjust fee transaksi

#### CashbackPromo
- Promo code contoh: `CBEXTRA`
- Minimum transaksi: Rp50.000
- Bonus cashback: +2% dari nominal
- Implementasi: `CashbackPromo.java`

#### DiscountPromo
- Promo code contoh: `DISKON10`
- Minimum transaksi: Rp25.000
- Diskon: 10% dari nominal pembayaran
- Implementasi: `DiscountPromo.java`

### 2. Split Bill

- Fitur bagi tagihan ke beberapa peserta
- Alur:
  1. Input total tagihan
  2. Input jumlah peserta (min 2)
  3. Hitung bagian per orang = total / peserta
  4. Cek saldo user saat ini
  5. User saat ini bayar bagiannya
  6. Pilih peserta lain untuk menagih bagian mereka
  7. Jika peserta lain punya saldo cukup, bayar + transfer ke user

- Fungsi utama: `Main.java:performSplitBill()`
- Fungsi bantuan: `Main.java:chooseSplitBillPayer()`, `Main.java:isAlreadySelected()`

### 3. PIN Keamanan

- PIN default: `1234`
- Validasi PIN: 4-6 digit angka
- Verifikasi required saat:
  - Login (menu 1)
  - Top Up (menu 3)
  - Bayar/Transfer (menu 4)
  - Split Bill (menu 6)
- Max percobaan: 3 kali

- Implementasi di `User.java`:
  - `verifyPin(String inputPin)` — verifikasi PIN
  - `setPin(String pin)` — setter dengan validasi
  - `getPinForStorage()` — getter untuk save ke file

- Implementasi di `Main.java`:
  - `verifyPinForUser(User)` — minta input PIN (3 percobaan)
  - `verifyCurrentUserPin()` — wrapper untuk user saat ini

### 4. Laporan Keuangan Bulanan

- Method: `showMonthlyFinancialReport()` di `User.java:221-254`
- Menampilkan:
  - Periode bulan berjalan (YearMonth.now())
  - Nama pemilik akun
  - Total pengeluaran + jumlah transaksi keluar
  - Total pemasukan + jumlah transaksi masuk
  - Saldo saat ini

- Filter transaksi berdasarkan bulan ini:
```java
YearMonth currentMonth = YearMonth.now();
if (!YearMonth.from(tx.getTimestamp()).equals(currentMonth)) {
    continue;
}
```

### 5. Save/Load Data

#### Format File: `data/users_data.txt`

Format per baris:
```
USER;TYPE;USER_ID;NAME;PHONE;BALANCE;PIN
TX;USER_ID;TIMESTAMP;AMOUNT;TYPE;STATUS
```

#### UserDataStorage.java

| Method                  | Fungsi                                         |
| ----------------------- | ---------------------------------------------- |
| `save(User[] users)`    | Simpan semua user + transaksi ke file         |
| `load()`                | Muat user + reconstruct transaksi dari file   |
| `getDataFilePath()`     | Return path file data                         |
| `formatUserLine()`      | Format 1 baris data user                      |
| `formatTransactionLine()`| Format 1 baris transaksi                     |
| `parseUser()`           | Parse baris user ke object                    |

#### Auto-load saat Startup
- `Main.java:initializeUsers()` mencoba load data terlebih dahulu
- Jika file tidak ada atau error, gunakan data default

## Catatan Penting

- Program harus **tetap berjalan tanpa error** setelah integrasi M1-M5
- Semua fitur M1-M5 harus tetap berfungsi:
  - M1: User class, topUp, pay, showBalance
  - M2: Encapsulation, validasi, Transaction history
  - M3: Inheritance (RegularUser, PremiumUser, MerchantUser, BankTransfer, QRPayment, WalletTransfer)
  - M4: Polymorphism, method overriding
  - M5: Abstract class (User, Payment)
- M6 menambahkan fitur baru tanpa merusak fitur lama
