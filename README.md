# ifs25003-pbo-p2

Praktikum 2 Pemrograman Berorientasi Objek (Java) — Clean Architecture.

## Struktur

| Folder | Isi |
|---|---|
| `latihan-todo-list/` | Latihan 1.1 — Aplikasi Todo List |
| `catatan-keuangan/` | Studi kasus 2.1 |
| `buku-tamu/` | Studi kasus 2.2 |
| `inventaris-barang/` | Studi kasus 2.3 |
| `jadwal-kegiatan/` | Studi kasus 2.4 |
| `kontak-teman/` | Studi kasus 2.5 |

Setiap proyek memakai struktur layer yang sama:

```
<proyek>/
├── src/
│   ├── App.java                # Composition Root (wiring dependency)
│   ├── domain/
│   │   ├── entity/             # Entity + enum domain (SortOption membawa Comparator-nya)
│   │   └── repository/         # Interface port (kontrak data)
│   ├── usecase/                # Logika bisnis (tanpa I/O)
│   ├── adapter/
│   │   ├── repository/         # Implementasi penyimpanan in-memory
│   │   └── presenter/          # Format output ke layar
│   └── framework/
│       ├── view/               # UI konsol + menu
│       └── util/               # InputUtil, InputClosedException
└── test-cases/                 # TC-*.tc (input) dan TC-*.expected (output yang diharapkan)
```

Arah dependensi: `domain` ← `usecase` ← `adapter` / `framework`.

## Compile dan jalankan

```bash
cd catatan-keuangan          # atau proyek lain
javac -d output -sourcepath src src/App.java
java -cp output App
```

## Menjalankan test case

```bash
./run-tests.sh
```

Skrip meng-compile setiap proyek, menjalankan semua `test-cases/TC-*.tc` sebagai stdin,
lalu memeriksa bahwa baris di `TC-*.expected` muncul berurutan pada output.

## Konvensi

- Input `x` membatalkan form pada **semua** field yang berlabel `(x Jika Batal)`.
- Field kosong pada form "Ubah" berarti field tersebut tidak diubah.
- Jika input habis (EOF) sebelum user keluar, semua view berhenti dengan pesan yang sama
  lewat `InputClosedException` (dilempar `InputUtil`, ditangani di view, ditampilkan presenter).
- Validasi jumlah (Catatan Keuangan) dan jumlah stok (Inventaris Barang) harus angka **> 0**.
