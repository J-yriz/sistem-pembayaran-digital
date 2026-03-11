# Sistem Pembayaran Digital (E-Wallet)

## Deskripsi Project
Project ini merupakan sistem pembayaran digital sederhana berbasis **CLI (Command Line Interface)** yang mensimulasikan fitur e-wallet seperti **GoPay, OVO, atau DANA**.  
Sistem mendukung berbagai metode pembayaran dengan perhitungan biaya (fee) yang berbeda-beda.

---

## Konsep OOP yang Digunakan

| Konsep OOP | Implementasi | Contoh dalam Project |
|---|---|---|
| Encapsulation | Atribut `private` + getter/setter | `balance`, `transactionHistory` hanya bisa diakses lewat method |
| Inheritance | Hierarki `payment` + `user` | `BankTransfer`, `QRPay` extends `Payment` |
| Polymorphism | Override method per jenis bayar | `processPayment()` berbeda tiap metode |
| Abstract | Abstract class + abstract method | abstract class `Payment`, abstract class `User` |

---

## Fitur Utama (ringkas)
- Simulasi e-wallet berbasis CLI
- Mendukung beberapa metode pembayaran
- Perhitungan biaya transaksi berbeda tergantung metode
- Riwayat transaksi (transaction history)
- Pengelolaan saldo (balance)

---
