# Color Cheatsheet — FlowShip

Referensi cepat mapping warna ke slot Material. Pakai ini saat coding UI.

---

## Di UI pakai `MaterialTheme.colorScheme.*`

| Kebutuhan UI                        | Pakai slot ini                              |
|-------------------------------------|---------------------------------------------|
| Background halaman                  | `colorScheme.background`                    |
| Warna teks utama                    | `colorScheme.onBackground`                  |
| Background card / surface           | `colorScheme.surface`                       |
| Teks di atas surface                | `colorScheme.onSurface`                     |
| Surface alternatif (chip, row)      | `colorScheme.surfaceVariant`                |
| Teks sekunder / placeholder         | `colorScheme.onSurfaceVariant`              |
| Warna brand / tombol utama          | `colorScheme.primary`                       |
| Teks/icon di atas tombol primary    | `colorScheme.onPrimary`                     |
| Background soft tint (glow, badge)  | `colorScheme.primaryContainer`              |
| Garis / divider                     | `colorScheme.outline`                       |
| Garis lebih tipis / alt             | `colorScheme.outlineVariant`                |
| Warna error / validasi gagal        | `colorScheme.error`                         |
| Background error                    | `colorScheme.errorContainer`                |
| Accent biru (badge, highlight)      | `colorScheme.secondary`                     |

---

## Warna custom pakai `MaterialTheme.appColors.*`

Untuk warna yang tidak ada slot-nya di Material (status badge, chip, dsb).

| Kebutuhan UI                        | Pakai token ini                             |
|-------------------------------------|---------------------------------------------|
| Teks status sukses                  | `appColors.success`                         |
| Background badge sukses             | `appColors.successContainer`                |
| Teks status warning                 | `appColors.warning`                         |
| Background badge warning            | `appColors.warningContainer`                |
| Teks status info                    | `appColors.info`                            |
| Background badge info               | `appColors.infoContainer`                   |
| Teks status danger/error custom     | `appColors.danger`                          |
| Background badge danger             | `appColors.dangerContainer`                 |

---

## Contoh penggunaan

```kotlin
// Background halaman
Box(modifier = Modifier.background(MaterialTheme.colorScheme.background))

// Card
Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface))

// Teks sekunder
Text(color = MaterialTheme.colorScheme.onSurfaceVariant)

// Tombol brand
Button(colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary))

// Badge sukses
Box(modifier = Modifier.background(MaterialTheme.appColors.successContainer)) {
    Text("Selesai", color = MaterialTheme.appColors.success)
}

// Badge danger
Box(modifier = Modifier.background(MaterialTheme.appColors.dangerContainer)) {
    Text("Ditolak", color = MaterialTheme.appColors.danger)
}
```

---

## ⚠️ Yang TIDAK boleh dilakukan

```kotlin
// ❌ Jangan pakai AppColor langsung di UI
.background(AppColor.Background)
Text(color = AppColor.TextSecondary)

// ❌ Jangan hardcode hex di UI
Text(color = Color(0xFF64748B))
```
