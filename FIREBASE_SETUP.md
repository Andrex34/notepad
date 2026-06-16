# Firebase Configuration

## Android

1. Создайте проект в Firebase Console: https://console.firebase.google.com
2. Добавьте Android приложение с package name: `com.notepad.app`
3. Скачайте `google-services.json` и поместите в `composeApp/androidMain/`
4. Включите Authentication > Email/Password
5. Включите Cloud Firestore

## iOS

1. Добавьте iOS приложение с Bundle ID: `com.notepad.app`
2. Скачайте `GoogleService-Info.plist` и поместите в `iosApp/`
3. Включите Authentication > Email/Password
4. Включите Cloud Firestore

## Desktop (JVM)

Для десктопного приложения используйте Firebase Admin SDK или REST API.
В данном приложении используется GitLive Firebase SDK, который работает через REST.

## Web

Для веб-версии потребуется дополнительная настройка Firebase JS SDK.
