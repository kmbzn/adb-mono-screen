# mono-display

스마트폰 디톡스를 위한 초경량 자동 흑백(Monochrome) & 50% 파스텔톤 유지 안드로이드 백그라운드 서비스 앱입니다.

---

## ✨ 핵심 기능

1. **상시 흑백 모드 자동 복구**:
   - 화면을 끄거나(`SCREEN_OFF`), 켜거나(`SCREEN_ON`), 잠금을 풀 때(`USER_PRESENT`) 즉시 시스템 흑백 모드(`accessibility_display_daltonizer_enabled = 1`)를 다시 적용합니다.
2. **50% 파스텔톤 채도 유지**:
   - 배달앱이나 지도 등을 확인하기 위해 상단 빠른 설정 타일에서 흑백 모드를 잠시 끌 경우, 100% 쨍한 원색이 아닌 **50% 채도(물 빠진 파스텔톤)** 로 화면이 표시되어 눈의 피로와 도파민 자극을 최소화합니다.
3. **초경량 & 무광고**:
   - UI/불필요한 종속성 없는 네이티브 단일 백그라운드 서비스 구조 (메모리 점유율 극소).
4. **부팅 자동 시작**:
   - 기기 재부팅(`BOOT_COMPLETED`) 시 자동으로 서비스가 시작됩니다.

---

## 🚀 설치 및 설정 방법

### 1. APK 설치
GitHub Releases에서 `app-debug.apk`를 다운로드하거나 빌드 후 설치합니다.

```bash
adb install -r app-debug.apk
```

### 2. 권한 부여 (최초 1회 필수)
시스템 설정을 변경하기 위한 보안 권한을 부여합니다.

```bash
adb shell pm grant com.custom.grayscaleauto android.permission.WRITE_SECURE_SETTINGS
```

### 3. 시스템 채도 50% 설정 (옵션/권장)
흑백을 해제했을 때 50% 채도로 유지되도록 설정합니다.

```bash
adb shell cmd color_display set-saturation 50
```

### 4. 앱 실행
```bash
adb shell am start -n com.custom.grayscaleauto/.MainActivity
```
