# Struktur Project Java - Sistem Pembayaran Digital

## 📁 Struktur Folder

```
sistem-pembayaran-digital/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── payment/
│                   ├── Main.java                 # Entry point aplikasi
│                   ├── models/                   # Package untuk model data
│                   │   ├── User.java            # Class User
│                   │   └── Transaction.java     # Class Transaction
│                   ├── services/                 # Package untuk business logic
│                   │   ├── UserService.java     # Service untuk mengelola user
│                   │   └── TransactionService.java # Service untuk transaksi
│                   └── utils/                    # Package untuk utility
│                       ├── InputHandler.java    # Handling input user
│                       └── DisplayUtils.java    # Utility untuk display
├── README.md                        # Dokumentasi utama
└── STRUKTUR_PROJECT.md             # File ini
```

## 🎯 Penjelasan Setiap Komponen

### 1. **models/** - Model Data

Folder ini berisi class yang merepresentasikan entitas data:

- **User.java**

  - Merepresentasikan pengguna sistem
  - Properties: id, name, email, balance
  - Methods: getter, setter, addBalance(), deductBalance()
- **Transaction.java**

  - Merepresentasikan transaksi pembayaran
  - Properties: id, fromUserId, toUserId, amount, description, timestamp
  - Tracking setiap transaksi yang terjadi

### 2. **services/** - Business Logic

Folder ini berisi class yang menghandle logic bisnis:

- **UserService.java**

  - Mengelola operasi user (add, search, update)
  - Menggunakan HashMap untuk menyimpan data user
  - Methods: addUser(), getUserById(), displayAllUsers(), dll
- **TransactionService.java**

  - Mengelola transaksi pembayaran
  - Melakukan validasi sebelum transfer
  - Methods: transfer(), displayTransactionHistory(), dll

### 3. **utils/** - Utility Functions

Folder ini berisi class helper:

- **InputHandler.java**

  - Menangani input dari user melalui CLI
  - Methods: readString(), readDouble(), readInteger(), readMenuChoice()
  - Validasi input otomatis
- **DisplayUtils.java**

  - Menampilkan menu dan format output
  - Methods: displayHeader(), displayMainMenu(), displaySuccess(), dll

### 4. **Main.java** - Entry Point

Class utama yang:

- Menginialisasi services
- Menjalankan loop aplikasi utama
- Handle interaksi user dengan menu

## 🚀 Cara Menjalankan

### Menjalankan Langsung (Java 11+)

Anda tidak memerlukan build system (seperti Gradle / Maven). Java 11 mendukung eksekusi file tanpa `javac` manual:

```bash
java src/main/java/com/payment/Main.java
```

### Menggunakan Script Cepat:

```bash
# Di OS Linux / Mac
./quick.sh

# Di OS Windows
quickrun.bat
```

### Menggunakan IDE (IntelliJ/Eclipse/VS Code):

1. Open folder project
2. Right-click pada file `/src/main/java/com/payment/Main.java`
3. Klik "Run Main()"

## 💡 Konsep Java yang Digunakan

1. **Object-Oriented Programming (OOP)**

   - Class dan Object
   - Encapsulation (getter/setter)
   - Inheritance (optional)
2. **Collections**

   - HashMap untuk menyimpan user
   - ArrayList untuk menyimpan transaksi
3. **Input/Output**

   - Scanner untuk membaca input
   - System.out untuk menampilkan output
4. **String & Formatting**

   - String manipulation
   - LocalDateTime untuk timestamp
5. **Control Flow**

   - If-else, Switch-case
   - While loop untuk menu

## 📝 Fitur Aplikasi

1. ✅ Kelola User (Tambah, Lihat, Cari)
2. ✅ Transfer Uang (dengan validasi saldo)
3. ✅ Riwayat Transaksi
4. ✅ Lihat Info User
5. ✅ Validasi Input

## 🔧 Cara Mengembangkan

### Menambah Fitur Baru:

1. **Tambah Class Model** (jika ada entitas baru)

   ```java
   // Di folder models/
   public class Wallet {
       private String id;
       private String userId;
       // ... properties dan methods
   }
   ```
2. **Tambah Service** (jika ada logic baru)

   ```java
   // Di folder services/
   public class WalletService {
       // ... business logic
   }
   ```
3. **Update Main.java** untuk mengintegrasikan fitur baru

### Contoh Menambah Fitur "Lihat Statistik":

```java
// 1. Tambah method di TransactionService
public void displayStatistics() {
    System.out.println("Total Transaksi: " + transactions.size());
    double totalAmount = transactions.stream()
        .mapToDouble(Transaction::getAmount)
        .sum();
    System.out.println("Total Nilai: Rp" + totalAmount);
}

// 2. Update Main.java
case 6:
    transactionService.displayStatistics();
    break;
```

## 📚 Tips Belajar Java

1. **Pahami OOP**: Setiap class punya tanggung jawab spesifik
2. **Read the Code**: Mulai dari Main.java, ikuti alur eksekusi
3. **Experiment**: Ubah kode, lihat hasilnya
4. **Add Features**: Coba tambah fitur sendiri
5. **Use Documentation**: Baca JavaDoc comments

## 🎓 Konsep yang Bisa Dipelajari

- Package dan imports
- Access modifiers (public, private)
- Constructor
- Methods dan parameters
- Collections (HashMap, ArrayList)
- Exception handling (try-catch)
- String operations
- Date/Time handling

---

Selamat belajar! 🎉
