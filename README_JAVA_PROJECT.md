# 💰 Sistem Pembayaran Digital - Aplikasi CLI Java untuk Pemula

Aplikasi sistem pembayaran digital berbasis CLI (Command Line Interface) yang dirancang khusus untuk **pemula belajar Java**. Project ini mengimplementasikan konsep Object-Oriented Programming (OOP) dengan struktur yang rapi dan mudah dipahami.

## ✨ Fitur Utama

- 👤 **Kelola User** - Tambah, lihat, dan cari user
- 💸 **Transfer Uang** - Transfer dengan validasi saldo otomatis
- 📊 **Riwayat Transaksi** - Lihat history semua transaksi atau per user
- 💼 **Info User** - Cek saldo dan informasi user
- ✅ **Validasi Input** - Input validation otomatis

## 🏗️ Arsitektur Project

```
com.payment/
├── Main.java                          # Entry point aplikasi
├── models/
│   ├── User.java                     # Class User
│   └── Transaction.java              # Class Transaction
├── services/
│   ├── UserService.java              # Business logic user
│   └── TransactionService.java       # Business logic transaksi
└── utils/
    ├── InputHandler.java             # Handle input user
    └── DisplayUtils.java             # Utility display/menu
```

## 🚀 Cara Memulai

### Prasyarat

- Java 11 atau lebih tinggi

### Step 1: Clone/Download Project

```bash
cd /run/media/jariz/SATA/coding/udinus/sistem-pembayaran-digital
```

### Step 2: Menjalankan Program

Tidak butuh compiler seperti Maven. Mulai Java 11+, Anda bisa jalankan file `Main.java` secara langsung. 
Ketikkan ini di Terminal/CMD:

```bash
java src/main/java/com/payment/Main.java
```

**Bisa juga memakai Script Bawaan:**
```bash
# Untuk Linux / Mac OS
chmod +x quick.sh
./quick.sh

# Untuk Windows
quickrun.bat
```

### Alternative: Run dari IDE

1. Buka project di VS Code, IntelliJ IDEA atau Eclipse
2. Buka dan Right-click pada `src/main/java/com/payment/Main.java`
3. Klik **Run 'Main()' / Run Java**

## 📝 Cara Menggunakan

### Saat Program Dimulai

Program akan menampilkan 3 user sample:

- **Budi Santoso** (ID: user001, Saldo: Rp500.000)
- **Siti Nurhaliza** (ID: user002, Saldo: Rp750.000)
- **Ahmad Wijaya** (ID: user003, Saldo: Rp1.000.000)

### Menu Utama

```
1. Kelola User
2. Lakukan Transfer
3. Lihat Riwayat Transaksi
4. Lihat Info User
5. Keluar
```

### Contoh Penggunaan

#### 1. Tambah User Baru

```
Menu: 1 → 1 (Tambah User Baru)
ID: user004
Nama: Dewi Lestari
Email: dewi@email.com
Saldo: 600000
```

#### 2. Transfer Uang

```
Menu: 2
ID Pengirim: user001
ID Penerima: user002
Jumlah Transfer: 150000
Deskripsi: Bayar hutang
```

#### 3. Lihat Riwayat Transaksi

```
Menu: 3
Pilih: 1 (Lihat Semua) atau 2 (User Spesifik)
```

## 📚 Dokumentasi

| File                       | Deskripsi                                       |
| -------------------------- | ----------------------------------------------- |
| `README_JAVA_PROJECT.md` | File ini - Overview project                     |
| `STRUKTUR_PROJECT.md`    | Penjelasan detail struktur folder dan component |
| `PANDUAN_PEMULA.md`      | Tutorial lengkap untuk pemula belajar Java      |
| `pom.xml`                | Maven configuration                             |

## 🎓 Konsep Java yang Dipelajari

### OOP (Object-Oriented Programming)

- ✅ Class dan Object
- ✅ Encapsulation (private, public, getter/setter)
- ✅ Constructor
- ✅ Methods

### Collections

- ✅ HashMap (key-value storage)
- ✅ ArrayList (dynamic array)
- ✅ Enhanced for loop

### Input/Output

- ✅ Scanner untuk input user
- ✅ System.out untuk output
- ✅ Input validation

### Java Standard Library

- ✅ String operations
- ✅ LocalDateTime
- ✅ Exceptions

## 🔧 Struktur Class

### User.java

```java
public class User {
    private String id;
    private String name;
    private String email;
    private double balance;
  
    public User(String id, String name, String email, double balance)
    public void addBalance(double amount)
    public boolean deductBalance(double amount)
}
```

### Transaction.java

```java
public class Transaction {
    private String id;
    private String fromUserId;
    private String toUserId;
    private double amount;
    private String description;
    private LocalDateTime timestamp;
}
```

### UserService.java

```java
public class UserService {
    private Map<String, User> users = new HashMap<>();
  
    public void addUser(User user)
    public User getUserById(String id)
    public User getUserByName(String name)
    public void displayAllUsers()
}
```

### TransactionService.java

```java
public class TransactionService {
    private List<Transaction> transactions = new ArrayList<>();
  
    public boolean transfer(String from, String to, double amount, String desc)
    public void displayTransactionHistory()
    public void displayUserTransactions(String userId)
}
```

## 💡 Cara Mengembangkan

### Menambah Fitur Baru

**Contoh: Tambah fitur "Withdraw"**

1. **Update User.java** (sudah ada `deductBalance()`)
2. **Tambah method di UserService.java**

```java
public boolean withdrawBalance(String userId, double amount) {
    User user = users.get(userId);
    if (user != null && user.deductBalance(amount)) {
        return true;
    }
    return false;
}
```

3. **Tambah method di Main.java**

```java
private static void handleWithdraw() {
    String userId = InputHandler.readString("Masukkan ID User: ");
    double amount = InputHandler.readDouble("Masukkan Jumlah Withdraw: ");
    if (userService.withdrawBalance(userId, amount)) {
        DisplayUtils.displaySuccess("Withdraw berhasil!");
    } else {
        DisplayUtils.displayError("Withdraw gagal!");
    }
}
```

4. **Update Main Menu**

```java
case 6:
    handleWithdraw();
    break;
```

## 🐛 Troubleshooting

### Error: "javac: command not found"

**Solusi:** Install Java Development Kit (JDK) 11+

```bash
# Ubuntu/Debian
sudo apt-get install openjdk-11-jdk

# macOS (dengan Homebrew)
brew install openjdk@11
```

### Error: "mvn: command not found"

**Solusi:** Install Maven

```bash
# Ubuntu/Debian
sudo apt-get install maven

# macOS
brew install maven
```

### Error: "NoSuchElementException" saat input

**Solusi:** Pastikan scanner tidak ditutup atau jangan gunakan NextLine() setelah NextInt()

## 📊 Project Status

- [X] Struktur project selesai
- [X] Model User & Transaction
- [X] UserService
- [X] TransactionService
- [X] CLI Menu & Input handling
- [X] Sample data
- [ ] Database integration
- [ ] Login system
- [ ] File persistence
- [ ] Unit testing

## 🎯 Learning Path

**Minggu 1-2:**

- Baca `PANDUAN_PEMULA.md`
- Pahami struktur folder dan setiap class
- Run program dan coba semua menu

**Minggu 3:**

- Ubah-ubah kode kecil (nama, jumlah, dll)
- Tambah system.out.println() untuk debugging
- Coba buat class baru sederhana

**Minggu 4+:**

- Tambah fitur baru sesuai ide
- Refactor code untuk lebih baik
- Belajar konsep Java lebih dalam

## 📖 Resources Belajar

- [Oracle Java Tutorials](https://docs.oracle.com/javase/tutorial/)
- [Baeldung Java Articles](https://www.baeldung.com/)
- [W3Schools Java](https://www.w3schools.com/java/)

## 👨‍💻 Author

Created for Java beginners learning Object-Oriented Programming

## 📄 License

MIT License - Bebas digunakan untuk keperluan pendidikan

## 🤝 Kontribusi

Untuk kontribusi atau saran, silakan buat issue atau pull request

---

**Happy Coding! 🎉**

Jangan takut untuk experiment dan break the code - itu adalah cara terbaik untuk belajar!
