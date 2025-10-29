# Nokturno Game APP

An interactive Android game that combines music, storytelling, and puzzle-solving elements.

## Download

The APK file is available in the [Releases](https://github.com/Rome0812/Nokturno-Game-APP/releases) section of this repository.

## Media Files

Due to GitHub's file size limitations, the video files are not included in this repository. Please download them from [Google Drive (add your link here)] and place them in the following directory:

```
app/src/main/res/raw/
```

Required video files:
- video_game_card_start.mp4
- video_game_card_tutorial.mp4
- video_game_vault_intro.mp4
- video_game_vault_spellbook.mp4
- video_game_vault_start.mp4
- video_playerdeath.mp4
- video_story_basement_dialog.mp4
- video_story_basement_end.mp4
- video_story_basement_start.mp4
- video_story_end.mp4
- video_story_opendoor.mp4
- video_story_outro.mp4
- video_story_shortintro.mp4
- video_story_start.mp4
- video_story_visions.mp4

## Development Setup

### Prerequisites

- Android Studio Hedgehog | 2023.1.1 or newer
- JDK 11 or 17
- Android SDK (minimum API 21)
- Git

### Building from Source

1. Clone the repository:
```bash
git clone https://github.com/Rome0812/Nokturno-Game-APP.git
cd Nokturno-Game-APP
```

2. Download the media files from [Google Drive (add your link here)] and place them in `app/src/main/res/raw/`

3. Open the project in Android Studio

4. Build the project:
   - Click Build > Build Bundle(s) / APK(s) > Build APK(s)
   - The APK will be generated in `app/build/outputs/apk/debug/app-debug.apk`

### Installing

1. Enable "Unknown Sources" in your Android device's settings
2. Transfer the APK to your Android device
3. Tap the APK file on your device to install

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
│       └── raw/           # Media files (download separately)
└── build.gradle.kts        # App level build configuration
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details