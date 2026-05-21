#!/bin/bash

# ==========================================
# SCRIPT COMPILE & RUN JAVA CLASSIC
# ==========================================
chcp.com 65001 > /dev/null 2>&1

# 1. Buat folder 'bin' untuk menampung file .class (jika belum ada)
mkdir -p bin

# 2. Compile semua file .java
# Menggunakan flag -d untuk menaruh hasil compile ke folder bin
# Menggunakan flag -sourcepath agar javac tidak bingung mencari import antar package
javac -d bin -sourcepath src/main/java $(find src/main/java -name "*.java")

# Cek apakah compile berhasil (exit code 0)
if [ $? -eq 0 ]; then
    echo "Compile berhasil."
    echo ""
    
    # 3. Jalankan program dengan classpath (-cp) ke folder bin
    java -Dfile.encoding=UTF-8 -cp bin com.payment.Main
else
    echo "Compile gagal. Silakan cek error di atas."
    exit 1
fi
