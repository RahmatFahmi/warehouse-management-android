# Panduan Lengkap Integrasi Hilt (Dagger Hilt)

Dokumen ini berisi langkah-langkah detail konfigurasi Hilt pada proyek **Project1** dengan Kotlin versi **2.4.20** dan AGP **9.3.2**.

## 1. Konfigurasi Version Catalog (`gradle/libs.versions.toml`)

Buka file `gradle/libs.versions.toml` dan tambahkan baris berikut di bagian masing-masing:

### A. Versi ([versions])
Gunakan versi yang saling kompatibel (terutama Kotlin dan KSP).
```toml
[versions]
kotlin = "2.4.20"
hilt = "2.60.1"
ksp = "2.3.12"  # Harus sesuai dengan versi Kotlin
hiltNavigationCompose = "1.4.0"
```

### B. Library ([libraries])
```toml
[libraries]
hilt-android = { group = "com.google.dagger", name = "hilt-android", version.ref = "hilt" }
hilt-compiler = { group = "com.google.dagger", name = "hilt-android-compiler", version.ref = "hilt" }
androidx-hilt-navigation-compose = { group = "androidx.hilt", name = "hilt-navigation-compose", version.ref = "hiltNavigationCompose" }
```

### C. Plugin ([plugins])
```toml
[plugins]
hilt = { id = "com.google.dagger.hilt.android", version.ref = "hilt" }
ksp = { id = "com.google.devtools.ksp", version.ref = "ksp" }
```

---

## 2. Konfigurasi Gradle Root (`build.gradle.kts`)

Daftarkan plugin Hilt dan KSP di file root agar modul `:app` bisa mengenalnya.

```kotlin
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
}
```

---

## 3. Konfigurasi Gradle Module (`app/build.gradle.kts`)

Terapkan plugin dan tambahkan library ke dalam modul aplikasi.

### A. Plugins Section
```kotlin
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}
```

### B. Dependencies Section
Gunakan `ksp` untuk pengolahan anotasi (lebih cepat dibanding `kapt`).
```kotlin
dependencies {
    // ... dependensi lainnya ...
    
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose) // Untuk hiltViewModel()
}
```

---

## 4. Setup Class Application (`MainApplication.kt`)

Buat file baru di `com.rahmat.project1.MainApplication`. Hilt memerlukan titik masuk di tingkat aplikasi.

```kotlin
package com.rahmat.project1

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application()
```

---

## 5. Registrasi di `AndroidManifest.xml`

Tambahkan atribut `android:name` ke dalam tag `<application>` untuk menghubungkan class yang baru dibuat.

```xml
<application
    android:name=".MainApplication"
    ... >
```

---

## 6. Setup Entry Point di Activity (`MainActivity.kt`)

Tambahkan anotasi `@AndroidEntryPoint` pada Activity agar Hilt bisa melakukan injeksi dependensi ke dalamnya.

```kotlin
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // Sekarang Anda bisa melakukan @Inject di sini
}
```

---

## Ringkasan Kompatibilitas
- **Kotlin**: `2.4.20`
- **KSP**: `2.3.12` (Khusus untuk Kotlin 2.4.20)
- **Hilt**: `2.60.1`
- **AGP**: `9.3.2`

Dokumen ini disimpan di: `HILT_SETUP_GUIDE.md`
