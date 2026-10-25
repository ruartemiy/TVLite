# Apple TV Lite — Android TV launcher prototype

A minimal, remote-friendly Android TV launcher for Android 11. No ads, networking, analytics, or background services. Discovers launchable apps and starts them using PackageManager.

## Build
Open this project in Android Studio (JDK 17), install Android SDK Platform 35, and build `app` with Gradle (`assembleDebug`). The APK will appear under `app/build/outputs/apk/debug/app-debug.apk`.

## Install
Copy the built APK to a FAT32 USB stick, open it with a file manager on the TV, grant install permission for that file manager, and install. Launch Apple TV Lite from Apps. Test D-pad navigation and app launching before making it the default Home app. Do not disable the original Xiaomi launcher.

## Notes
This is a functional v0.1 source prototype, not a prebuilt APK. It is inspired by a clean TV UI; no Apple branding or proprietary assets are used. Some vendor apps may be missing from the app list if they expose no standard launcher intent. On some Xiaomi firmware, changing the default HOME app is restricted. Build uses Android Gradle Plugin 8.5.2 and Android SDK 35. This source archive does not contain a Gradle wrapper.
