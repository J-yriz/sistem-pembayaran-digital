package com.payment.services;

import com.payment.models.User;
import java.util.HashMap;
import java.util.Map;

/**
 * Class UserService - Mengelola dan menyimpan seluruh data array pengguna di memory aktif (Logic Bisnis User).
 * 
 * Deskripsi Detail:
 * Melalui panel service inila layer database `Map users` dilindungi pengaksesannya.
 * Semua fungsionalitas CRUD Utama (Buat, Cari, Tampilkan, Hitung) berkumpul disini.
 * 
 * Digunakan / Berelasi di:
 * - Disediakan atau Di-Inject kepada Main (untuk diakses Registrasi Main Menu CLI).
 * - Disediakan kepada TransactionService (Untuk meminjam instans User demi menggeser nilai saldonya).
 */
public class UserService {
    private Map<String, User> users = new HashMap<>();

    // Wrapper fungsi mendaftarkan User instanciation baru ke dalam array memori Hash Map.
    // Dipanggil / Didapatkan di: Main.addNewUser() sesaat setelah user selesai ngisi form cli,
    // Serta pada dummy data Main.initializeSampleData().
    public void addUser(User user) {
        if (user != null && !users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            System.out.println("✓ User '" + user.getName() + "' berhasil ditambahkan");
        } else {
            System.out.println("✗ User gagal ditambahkan atau sudah ada");
        }
    }

    // Mengembalikan objek referensi utama kelas `User` dengan mencocokan ID sebagai Kunci Index Map.
    // Dipanggil sangat krusial di: TransactionService.transfer() menvalidasi apakah akun "fromUser" & "toUser" terdaftar riil.
    public User getUserById(String id) {
        return users.get(id);
    }

    // Cari user berdasarkan nama
    public User getUserByName(String name) {
        for (User user : users.values()) {
            if (user.getName().equalsIgnoreCase(name)) {
                return user;
            }
        }
        return null;
    }

    // Tampilkan semua user
    public void displayAllUsers() {
        if (users.isEmpty()) {
            System.out.println("Belum ada user terdaftar.");
            return;
        }
        System.out.println("\n=== DAFTAR USER ===");
        for (User user : users.values()) {
            System.out.println(user);
        }
    }

    // Update saldo user
    public boolean updateBalance(String userId, double amount) {
        User user = users.get(userId);
        if (user != null) {
            user.setBalance(amount);
            return true;
        }
        return false;
    }

    // Cek apakah user ada
    public boolean userExists(String userId) {
        return users.containsKey(userId);
    }

    // Dapatkan jumlah user
    public int getUserCount() {
        return users.size();
    }
}
