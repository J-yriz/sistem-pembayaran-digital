# Project C - Sistem Pembayaran Digital (E-Wallet)

Dokumen ini merangkum **timeline milestone resmi M1 sampai M6** sesuai spesifikasi tugas.

## Ringkasan Konsep OOP Wajib

| Konsep OOP    | Implementasi                                                                  |
| ------------- | ----------------------------------------------------------------------------- |
| Encapsulation | Semua atribut `private` + akses via getter/setter/method                    |
| Inheritance   | Hierarki class `User` dan `Payment`                                       |
| Polymorphism  | Override method di subclass, dipanggil via reference parent                   |
| Abstract      | `abstract class User` dan `abstract class Payment` (mulai tahap refactor) |

## Milestone 1 - Dasar User & Saldo

### Target Fitur

- Buat class `User` dengan atribut: `userId`, `name`, `phone`, `balance`
- Constructor inisialisasi semua atribut
- Method:
  - `topUp(double amount)`
  - `pay(double amount)` (kurangi saldo jika cukup)
  - `showBalance()`
- Di `Main`: buat minimal 2 object `User`, lakukan `topUp` dan `pay`

### Kriteria Penilaian (Total 10)

- Class dibuat dengan benar: 3
- Method berfungsi: 3
- Object berhasil dibuat di `Main`: 2
- Output jelas dan rapi: 2

## Milestone 2 - Encapsulation + Transaction History

### Target Fitur

- Semua atribut `User` diubah ke `private`
- Validasi:
  - `balance` tidak boleh negatif
  - `topUp` harus positif
  - `pay` harus `> 0` dan `<= balance`
- Tambah class `Transaction` untuk record: `timestamp`, `amount`, `type`, `status`
- Setiap `topUp`/`pay` otomatis membuat object `Transaction` dan masuk ke `List<Transaction>`
- Tambah `showTransactionHistory()` di `User`
- Tambah validasi input pada menu (handle input salah)

### Kriteria Penilaian (Total 15)

- Semua atribut private: 3
- Getter/setter lengkap: 3
- Validasi pada setter/method: 4
- Fitur baru berfungsi (`loop` / `timePasses` / `Transaction`): 3
- Code quality (penamaan jelas, konsisten, ada komentar): 2

## Milestone 3 - Inheritance User & Payment

### Target Fitur

- `User` menjadi parent class
- Buat subclass:
  - `RegularUser extends User` (limit Rp2.000.000, cashback 1%)
  - `PremiumUser extends User` (limit Rp10.000.000, cashback 5%)
  - `MerchantUser extends User` (limit Rp50.000.000, cashback 0%, bisa terima pembayaran)
- Buat `Payment` sebagai parent class dengan subclass:
  - `BankTransfer`
  - `QRPayment`
  - `WalletTransfer`
- Di `Main`, login sebagai salah satu jenis user

### Kriteria Penilaian (Total 20)

- Hierarki class benar (`extends`, relasi IS-A): 5
- Constructor chaining (`super`) benar: 4
- Minimal 3 subclass dengan atribut/method tambahan: 4
- Subclass terintegrasi di `Main`: 4
- Encapsulation milestone sebelumnya tetap terjaga: 3

## Milestone 4 - Overriding & Polymorphism

### Target Fitur

- Override `calculateFee()` di tiap subclass `Payment`:
  - `BankTransfer`: flat Rp2.500
  - `QRPayment`: gratis di bawah Rp100.000, 0.7% di atasnya
  - `WalletTransfer`: gratis
- Override `validate()` dengan aturan berbeda per metode bayar
- Override `getCashbackRate()` dan `getTransactionLimit()` per jenis user
- Buat `processPayment(Payment p)` menerima tipe parent `Payment` (polymorphism)

### Kriteria Penilaian (Total 20)

- Method overriding benar (`@Override`, behavior beda): 6
- Polymorphic reference dipakai: 5
- Dynamic dispatch terlihat sesuai tipe runtime: 5
- Milestone sebelumnya tetap utuh: 4

## Milestone 5 - Refactor ke Abstract Class

### Target Fitur

- Ubah `User` menjadi `abstract class User`
- Tandai abstract method di `User`:
  - `getTransactionLimit()`
  - `getCashbackRate()`
  - `getAccountType()`
- Ubah `Payment` menjadi `abstract class Payment`
- Tandai abstract method di `Payment`:
  - `calculateFee()`
  - `validate()`
  - `getPaymentMethod()`
- Pastikan semua subclass mengimplementasikan method abstract
- Dokumentasikan alasan `User` dan `Payment` dijadikan abstract

### Pertanyaan Refleksi (Wajib di Laporan)

- Apa yang berubah di kode setelah refactor ke abstract? Apakah banyak yang berubah?
- Apa yang terjadi jika subclass baru dibuat tapi lupa implement abstract method?
- Apa keuntungan abstract class dibanding class biasa sebagai parent?
- Dalam situasi apa sebaiknya tidak menggunakan abstract class?

### Kriteria Penilaian (Total 20)

- Abstract class benar (tidak bisa di-instantiation): 5
- Abstract method benar (minimal 2, semua subclass implement): 5
- Refactoring bersih (program tetap berjalan, tidak regress): 4
- Pertanyaan refleksi dijawab baik: 3
- Bonus abstract class tambahan (`Item`/`Food`/`Promo`): 3

## Milestone 6 - Integrasi Final + Bonus

### Target Fitur

- Sistem promo (`abstract class Promo` -> `Cashback`, `Discount`)
- Fitur split bill
- PIN/password keamanan
- Laporan keuangan bulanan
- Save/load data user ke file

### Kriteria Penilaian (Total 15)

- Program utuh & berjalan (fitur M1-M5 terintegrasi, no error): 4
- Laporan lengkap (class diagram + penjelasan OOP + screenshot): 3
- Demo/presentasi (menjelaskan kode sendiri, menjawab pertanyaan): 4
- Fitur bonus (minimal 1 fitur bonus): 2
- Code quality keseluruhan (penamaan, struktur, komentar, konsistensi): 2

## Catatan Penggunaan Timeline

- Implementasi dikerjakan **bertahap per milestone**.
- Jangan melompati milestone jika tahap sebelumnya belum stabil.
- Saat submit, sesuaikan branch/versi dengan milestone yang sedang dinilai.

## Menjalankan Program

```bash
./compile_and_run.sh
```
