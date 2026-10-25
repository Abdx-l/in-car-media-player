# In-Car Media Player UI

Kotlin/Compose playlist player with Media3 foreground playback, audio focus/noisy-audio handling, Room-backed track/position restoration, large controls, a moving-mode text lock, and a StateFlow ViewModel test. Moving mode is a demo input rather than a vehicle integration.

## Run

Open in Android Studio with Android SDK 35, Java 17 and Gradle 8.9. Run `gradle testDebugUnitTest assembleDebug`. Install the debug APK on an Android 8+ emulator/device. CI builds and retains the debug APK.
