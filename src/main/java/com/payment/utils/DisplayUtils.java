package com.payment.utils;

/**
 * Class DisplayUtils - Bertanggung Jawab untuk Menampilkan Tampilan CLI yang Konsisten dan Estetis.
 * 
 * Deskripsi Detail:
 * Kelas ini menyimpan semua metode statis yang digunakan untuk menampilkan berbagai bagian
 * antarmuka pengguna di terminal. Dengan menggunakan ANSI color codes, kelas ini memberikan
 * tampilan yang lebih menarik dan mudah dibaca, serta menjaga konsistensi visual di seluruh aplikasi.
 */
public class DisplayUtils {

    // ANSI Color Codes
    private static final String ORANGE = "\u001B[38;5;214m";
    private static final String RESET = "\u001B[0m";

    public static void ASCIIArt(String[] args) {
        System.out.println(ORANGE + "           ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⣤⣄⡀⠀⠀⠀       " + RESET);
        System.out.println(ORANGE + "           ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡸⠋⠀⠘⣇⠀⠀⠀       " + RESET);
        System.out.println(ORANGE + "           ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢰⠇⠀⠀⠀⢸⠀⠀⠀       " + RESET);
        System.out.println(ORANGE + "           ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡜⠀⠀⠀⠀⢸⠀⠀⠀       " + RESET);
        System.out.println(ORANGE + "           ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢰⠇⠀⠀⠀⠀⢸⠇⠀⠀       " + RESET);
        System.out.println(ORANGE + "           ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⡎⠀⠀⠀⠀⠀⢸⠀⠀⠀       " + RESET);
        System.out.println(ORANGE + "           ⠀⠀⢀⣀⣀⣀⠀⠀⠀⠀⠀⢀⣀⣤⡤⠤⠤⠤⠤⢤⣤⣀⡤⢖⡿⠛⠉⢳⠀⠀⠀⠀⠀⢸⠀⠀⠀    " + RESET);
        System.out.println(ORANGE + "           ⠀⢼⠁⠉⠉⠛⠻⢭⡓⠒⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠰⣏⠀⠀⠀⢸⠀⠀⠀⠀⠀⡤⠀⠀⠀     " + RESET);
        System.out.println(ORANGE + "           ⠀⠸⡄⠀⠀⠀⠀⢸⠇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⠂⠀⠀⡜⠀⠀⠀⠀⢀⡇⠀⠀⠀      " + RESET);
        System.out.println(ORANGE + "           ⠀⠀⢷⠀⠀⠀⠠⠇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢣⢠⠏⠀⠀⠀⠀⢸⠃⠀⠀⠀       " + RESET);
        System.out.println(ORANGE + "           ⠀⠀⠈⢧⠀⢀⡆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡞⠀⠀⠀⠀⠀⢸⠀⠀⠀⠀       " + RESET);
        System.out.println(ORANGE + "           ⠀⠀⠀⠈⢳⡈⠁⠀⠀⠀⠀⠀⣀⡀⠀⠀⠀⠀⠀⠀⠀⣶⣶⣦⠀⠀⢹⠀⠀⠀⠀⠀⡎⠀⠀⠀⠀       " + RESET);
        System.out.println(ORANGE + "           ⠀⠀⠀⠀⠀⡇⠀⠀⠀⠀⢠⣾⣟⣹⡄⠀⠀⠀⠀⡀⠀⣿⣿⣿⡇⠀⢈⣧⠤⠤⠶⠶⢷⠒⠒⠂⠀       " + RESET);
        System.out.println(ORANGE + "           ⠀⠀⢀⣀⣠⡧⠄⠀⠀⠀⣾⣿⣿⣿⠇⠀⠀⠀⠙⠁⠀⠙⠻⠿⠃⠀⠨⣼⣤⣀⡀⠀⠈⢧⠀⠀⠀       " + RESET);
        System.out.println(ORANGE + "           ⠘⠉⠁⠀⢸⣤⡤⠀⠀⠀⠛⢿⡿⠋⠀⠀⠀⠀⠴⠦⠀⠀⠀⠀⠀⠐⣲⣯⡀⠀⠈⠙⠓⠺⣧⣄⡀       " + RESET);
        System.out.println(ORANGE + "           ⠀⣀⡤⠚⠉⢳⡴⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡼⠃⠀⠈⠓⢦⡀⠀⠀⢸⠀⠈       " + RESET);
        System.out.println(ORANGE + "           ⠀⠁⠀⢀⡔⠉⠙⡶⢄⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠴⠚⠁⠀⠀⠀⠀⠀⠀⠈⠓⠆⠀⡇⠀       " + RESET);
        System.out.println(ORANGE + "           ⠀⠀⠰⠋⠀⠀⢸⡇⠀⠈⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⠁⠀       " + RESET);
        System.out.println(ORANGE + "           ⠀⠀⠀⠀⠀⠀⠈⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡎⠀⠀       " + RESET);
        System.out.println(ORANGE + "           ⠀⠀⠀⠀⠀⠀⠀⠹⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡇⠀⠀       " + RESET);
        System.out.println(ORANGE + "           ⠀⠀⠀⠀⠀⠀⠀⠀⠙⢆⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⠄⠀⢰⠇⠀⠀       " + RESET);
        System.out.println(ORANGE + "           ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠹⡆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⠶⠺⣇⠀⣀⡜⠀⠀⠀       " + RESET);
        System.out.println(ORANGE + "           ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢱⡄⠀⠀⠀⠹⡟⠒⢢⡀⠀⠀⠀⠀⢀⡏⠀⠀⠀⠈⠉⠉⠁⠀⠀⠀       " + RESET);
        System.out.println(ORANGE + "           ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠹⣄⠀⠀⢀⡇⠀⠀⠻⣄⠀⠀⠀⡸⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀       " + RESET);
        System.out.println(ORANGE + "           ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⢷⠶⠋⠀⠀⠀⠀⠈⣣⠶⠖⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀       " + RESET);
        System.out.println(ORANGE + " " + RESET);
        System.out.println(ORANGE + " " + RESET);
    }

    public static void displayHeader() {
        System.out.println();
        System.out.println(ORANGE + " ██████╗ ███████╗     ██████╗ ███████╗███╗   ██╗████████╗███████╗" + RESET);
        System.out.println(ORANGE + "██╔════╝ ╚════██║    ██╔════╝ ██╔════╝████╗  ██║╚══██╔══╝██╔════╝" + RESET);
        System.out.println(ORANGE + "███████╗     ██╔╝    ██║      █████╗  ██╔██╗ ██║   ██║   ███████╗" + RESET);
        System.out.println(ORANGE + "██╔═══██╗   ██╔╝     ██║      ██╔══╝  ██║╚██╗██║   ██║   ╚════██║" + RESET);
        System.out.println(ORANGE + "╚██████╔╝   ██║      ╚██████╗ ███████╗██║ ╚████║   ██║   ███████║" + RESET);
        System.out.println(ORANGE + " ╚═════╝    ╚═╝       ╚═════╝ ╚══════╝╚═╝  ╚═══╝   ╚═╝   ╚══════╝" + RESET);
        System.out.println();
        System.out.println(ORANGE + "┌─────────────────────────────────────────────────────────────┐" + RESET);
        System.out.println(ORANGE + "│ Wellcome to 67 Cents, the most broke app to move your money │" + RESET);
        System.out.println(ORANGE + "└─────────────────────────────────────────────────────────────┘" + RESET);
        System.out.println();
    }

}