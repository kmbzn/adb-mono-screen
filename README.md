# MonoScreen

A lightweight Android background service for smartphone digital detox. Automatically restores 100% grayscale upon screen events and maintains a 50% pastel saturation baseline when color is toggled.

---

## ✨ Key Features

1. **Auto Grayscale Lock**:
   - Automatically re-enables 100% grayscale (`accessibility_display_daltonizer_enabled = 1`) whenever the screen turns off (`ACTION_SCREEN_OFF`), turns on (`ACTION_SCREEN_ON`), or is unlocked (`ACTION_USER_PRESENT`).
2. **50% Pastel Saturation Baseline**:
   - When temporarily turning off grayscale from Quick Settings tiles (e.g., for maps or camera), the system displays colors at **50% saturation (subdued pastel tones)** rather than 100% vivid colors, reducing eye strain and dopamine stimulation.
3. **Ultra Lightweight & Ad-Free**:
   - Single native background service with near-zero memory footprint and no unnecessary dependencies.
4. **Auto-Start on Boot**:
   - Automatically registers and starts via `BOOT_COMPLETED` receiver upon device restart.

---

## 🚀 Installation & Setup

### 1. Install APK
Download `app-debug.apk` from GitHub Actions / Releases or build locally, then install via ADB:

```bash
adb install -r app-debug.apk
```

### 2. Grant Permissions (One-Time Setup)
Grant secure settings write permission via ADB:

```bash
adb shell pm grant com.custom.grayscaleauto android.permission.WRITE_SECURE_SETTINGS
```

### 3. Set Baseline 50% Saturation (Recommended)
Set the system-wide saturation level to 50%:

```bash
adb shell cmd color_display set-saturation 50
```

### 4. Start Service
```bash
adb shell am start -n com.custom.grayscaleauto/.MainActivity
```
