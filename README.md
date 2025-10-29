# Follow The Beat

An Android application that provides an interactive musical experience.

## Download

The APK file is available in the [Releases](https://github.com/Rome0812/Nokturno-Game-APP/releases) section of this repository.

## Prerequisites

- Java JDK 11 or 17 (Download from [Eclipse Temurin](https://adoptium.net/))
- Android Studio (Download from [here](https://developer.android.com/studio))
- Android SDK (can be installed through Android Studio)
- Git for version control

## Project Setup

1. Clone the repository:
```bash
git clone [repository-url]
cd Nokturno
```

2. Open the project in Android Studio:
- Launch Android Studio
- Select "Open an Existing Project"
- Navigate to the cloned repository folder and click OK
- Wait for the project to sync and build

## Building the Project

### Using Android Studio (Recommended)
1. Open the project in Android Studio
2. Wait for project sync to complete
3. Select Build > Build Bundle(s) / APK(s) > Build APK(s)
4. The APK will be generated in `app/build/outputs/apk/debug/app-debug.apk`

### Using Command Line
```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease
```

The generated APKs will be located in:
- Debug APK: `app/build/outputs/apk/debug/app-debug.apk`
- Release APK: `app/build/outputs/apk/release/app-release.apk`

## Installing the App

### Method 1: Using Android Studio
1. Connect your Android device via USB or start an Android Emulator
2. Enable USB debugging on your device (for physical devices)
3. Click the 'Run' button (green play icon) in Android Studio
4. Select your device and click OK

### Method 2: Manual Installation
1. Enable "Unknown Sources" in your Android device's settings
2. Transfer the APK file to your Android device
3. Locate the APK on your device and tap it to install

## Development Setup

1. Configure Java Development Kit:
   - Install JDK 11 or 17
   - Set JAVA_HOME environment variable
   - Add Java to your system PATH

2. Android Studio Configuration:
   - Install required Android SDK platforms (minimum SDK version)
   - Install Android SDK Build-Tools
   - Configure Android Virtual Device (AVD) if needed

## Project Structure

```
app/
├── src/main/
│   ├── java/com/example/followthebeat/
│   │   ├── Games/           # Game-related activities
│   │   ├── MediaPlayers/    # Audio and video handlers
│   │   └── ...             # Other Java classes
│   └── res/
│       ├── layout/         # UI layout files
│       ├── values/         # Resource files
│       └── ...            # Other resources
└── build.gradle.kts        # App level build configuration
```

## Version Control

### Pushing Changes
```bash
git add .
git commit -m "Your commit message"
git push origin main
```

### Important: Before Pushing
- Do not commit the `app/build/` directory
- Do not commit local configuration files
- Update .gitignore if needed

## Release Process

1. Update version numbers in `app/build.gradle.kts`
2. Test thoroughly on multiple devices
3. Generate a signed release APK
4. Tag the release version in git
5. Create a GitHub release with the APK attached

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

[Add your license information here]