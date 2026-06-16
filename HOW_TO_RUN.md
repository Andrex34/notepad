# How to Run

## Desktop (PC)

```bash
gradlew :composeApp:run
```

## Android

1. Enable Developer Mode on phone (tap "Build Number" 7 times)
2. Enable USB Debugging
3. Connect phone via USB
4. Create `local.properties` in project root with:
   ```
   sdk.dir=C:\\Users\\Andrex\\AppData\\Local\\Android\\Sdk
   ```
5. Run:
   ```bash
   gradlew :composeApp:installDebug
   ```

Or open project in Android Studio and press Run.
