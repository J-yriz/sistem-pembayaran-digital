#!/bin/bash

mkdir -p bin

javac -d bin -sourcepath src/main/java $(find src/main/java -name "*.java")

if [ $? -eq 0 ]; then
    echo "Compile berhasil."
    echo ""

    java -cp bin com.payment.Main
else
    echo "Compile gagal. Silakan cek error di atas."
    exit 1
fi
