# Notepad - Кроссплатформенное приложение заметок

Приложение заметок на Kotlin Multiplatform + Compose Multiplatform + Firebase.

## Возможности

- Создание, редактирование, удаление заметок
- Поиск по заметкам
- Закрепление важных заметок
- Категории для группировки
- Синхронизация между устройствами (Firebase)
- Оффлайн-режим

## Технологии

- **Kotlin Multiplatform** - общая бизнес-логика
- **Compose Multiplatform** - кроссплатформенный UI
- **Firebase Auth** - аутентификация
- **Cloud Firestore** - облачная БД
- **SQLDelight** - локальная БД
- **Koin** - Dependency Injection

## Требования

- JDK 17+
- Android Studio Hedgehog+ (для Android)
- Xcode 15+ (для iOS)
- Gradle 8.2+

## Запуск

### Android
```bash
./gradlew :composeApp:installDebug
```

### Desktop (JVM)
```bash
./gradlew :composeApp:run
```

### iOS
1. Откройте `iosApp/iosApp.xcodeproj` в Xcode
2. Выберите симулятор или устройство
3. Нажмите Run

## Структура проекта

```
notepad/
├── composeApp/          # UI слой (Compose Multiplatform)
│   ├── commonMain/      # Общий код
│   ├── androidMain/     # Android-specific
│   ├── desktopMain/     # Desktop-specific
│   └── iosMain/         # iOS-specific
├── shared/              # Бизнес-логика (KMP)
│   └── commonMain/      # Доменные модели, репозитории, DAO
└── iosApp/              # iOS обёртка
```

## Настройка Firebase

См. `FIREBASE_SETUP.md` для инструкций по настройке Firebase.
