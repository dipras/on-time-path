# 🚗 ON-TIME PATH

**Sampai Kampus Tepat Waktu di Surabaya**

Game simulasi berbasis graph berbobot dengan Terminal User Interface (TUI) menggunakan Java dan Lanterna.

## 📖 Cerita

Kamu baru saja bangun dan hanya punya **15 menit** untuk sampai ke kampus ITS dari rumahmu di Wonokromo, Surabaya! Pilih jalur terbaik melewati berbagai wilayah Surabaya dan sampai tepat waktu. Hati-hati dengan kemacetan dan event acak lainnya!

## 🎮 Fitur

- **Graph Berbobot**: Setiap wilayah Surabaya direpresentasikan sebagai node, jalur sebagai edge dengan bobot waktu tempuh
- **Algoritma Dijkstra**: Menghitung jalur optimal untuk perbandingan
- **Event Dinamis**: Kemacetan, jalan lancar, kecelakaan, hujan, dll.
- **TUI Interaktif**: Navigasi menggunakan keyboard (Arrow Keys + Enter)
- **Perbandingan Jalur**: Bandingkan jalur pemain dengan jalur optimal Dijkstra

## 🗺️ Peta Wilayah Surabaya

```
┌─────────────────────────────────────────────────────┐
│                                                     │
│    Rumah (Wonokromo) ────┬──── Darmo ──── Tegalsari │
│           │              │       │            │     │
│           │              │       │            │     │
│           └─── Ngagel ───┴───────┤            │     │
│                  │               │            │     │
│                  │           Gubeng ──────────┤     │
│                  │               │            │     │
│                  └─── Kertajaya ─┴─── Manyar ─┘     │
│                           │            │            │
│                           │            │            │
│                      Mulyorejo ────────┘            │
│                           │                         │
│                           │                         │
│                      Sukolilo                       │
│                           │                         │
│                           │                         │
│                      Kampus ITS 🎯                  │
│                                                     │
└─────────────────────────────────────────────────────┘
```

## ⚡ Event Dinamis

| Event | Efek | Probabilitas |
|-------|------|--------------|
| 🚗 Jalan Macet | +2-4 menit | 25% |
| 🏃 Jalan Lancar | -1-2 menit | 20% |
| ⚠️ Kecelakaan | +3-5 menit | 8% |
| 🚧 Perbaikan Jalan | +2-3 menit | 5% |
| 🌧️ Hujan Deras | +1-3 menit | 5% |
| 🐭 Jalan Tikus | -2-3 menit | 2% |
| ➡️ Normal | Tidak ada perubahan | 35% |

## 🛠️ Teknologi

- **Java 17**
- **Maven** (Build Tool)
- **Lanterna** (TUI Library)
- **JUnit 4** (Testing)

## 📂 Struktur Proyek

```
on-time-path/
├── pom.xml
├── README.md
└── src/
    ├── main/java/com/otp/
    │   ├── App.java              # Main entry point
    │   ├── graph/
    │   │   ├── Node.java         # Representasi wilayah
    │   │   ├── Edge.java         # Representasi jalur
    │   │   ├── Graph.java        # Struktur graph Surabaya
    │   │   └── Dijkstra.java     # Algoritma shortest path
    │   ├── game/
    │   │   ├── GameEngine.java   # Logika game utama
    │   │   ├── GameState.java    # State pemain
    │   │   └── GameStatus.java   # Enum status game
    │   ├── event/
    │   │   ├── EventType.java    # Tipe-tipe event
    │   │   ├── GameEvent.java    # Representasi event
    │   │   └── EventManager.java # Generator event acak
    │   └── ui/
    │       └── TUIManager.java   # Terminal User Interface
    └── test/java/com/otp/
        └── AppTest.java          # Unit tests
```

## 🚀 Cara Menjalankan

### Prerequisites
- Java 17 atau lebih baru
- Maven 3.6+

### Build & Run

```bash
# Clone atau masuk ke direktori proyek
cd on-time-path

# Compile dan package
mvn clean package

# Jalankan game
java -jar target/on-time-path-1.0-SNAPSHOT.jar
```

### Development Mode

```bash
# Compile
mvn compile

# Run tests
mvn test

# Run application
mvn exec:java -Dexec.mainClass="com.otp.App"
```

## 🎯 Cara Bermain

1. **Mulai Permainan**: Pilih "Mulai Permainan" dari menu utama
2. **Pilih Jalur**: Gunakan Arrow Keys (↑↓) untuk memilih tujuan berikutnya
3. **Konfirmasi**: Tekan Enter untuk bergerak
4. **Perhatikan Event**: Event acak dapat mengubah waktu perjalanan
5. **Sampai Tepat Waktu**: Sampai ke Kampus ITS sebelum waktu habis!

### Tips
- Perhatikan jalur optimal yang disarankan (ditandai dengan ⭐)
- Event bisa menguntungkan atau merugikan
- Waktu minimum ke tujuan ditampilkan untuk referensi

## 🧪 Testing

```bash
# Jalankan semua unit tests
mvn test

# Test dengan output verbose
mvn test -X
```

## 📊 Algoritma Dijkstra

Game ini mengimplementasikan algoritma Dijkstra untuk menghitung jalur terpendek:

```java
// Pseudocode
1. Inisialisasi jarak ke semua node = ∞, kecuali source = 0
2. Gunakan Priority Queue untuk memproses node dengan jarak terkecil
3. Untuk setiap node, update jarak ke tetangganya jika ditemukan jalur lebih pendek
4. Ulangi sampai semua node dikunjungi
5. Trace back untuk mendapatkan jalur optimal
```

## 📝 Lisensi

Project ini dibuat untuk keperluan pembelajaran mata kuliah **Struktur Data** di Telkom University.

## 👨‍💻 Author

- **Mata Kuliah**: Struktur Data
- **Institusi**: Telkom University
- **Kelas**: SE0702
- **Anggota Kelompok**:
```
Aldy Prastyo - 103082400021
Alif Akbar - 103082400035
Muh Aqsa Sirojudin - 103082400004
```

---

*Selamat bermain dan semoga tepat waktu! 🎓*
