# enemy

### What enemy Is

enemy is an audio player and supplemental code sample written by @buggily for personal use. While the primary purpose of enemy is exhibition, @buggily employs enemy as their dedicated audio player in everyday life! enemy draws inspiration from popular audio players.

### What enemy Is Not

enemy is not a universal audio player. enemy tailors itself specifically to how @buggily likes to play audio files. enemy requires comprehensive metadata for each audio file; see the requirements in each respective local query source.

### Overview

enemy provides browsing and playback by album. A media service supports background playback, and the corresponding media notification is available in both the notification tray and on the lock screen. enemy adopts the early beta of Media3 to achieve a familiar playback experience. @buggily plans enhancements to follow a stable Media3 release.

### Implementation

enemy demonstrates the following:

- Dependency injection via [Hilt][hilt]
- Positional paging via [Paging3][paging]
- Audio playback via [Media3][media]
- Declarative layouts via [Jetpack Compose][compose]
- Image loading via [Coil][coil]
- Unidirectional data flow via [MVVM][mvvm]
- Modularization via [versions catalogs][versions] and [convention plugins][plugins]

### Setup

Because enemy is a strictly local client, setup simply involves cloning the repository, provided audio files exist on the device. enemy searches for audio files in the root **Music** directory of the Android device. Run in [Android Studio][android studio] or any Android IDE.

<img src="./res/the_threatened_swan.png" alt="the threatened swan" width=33.333%><img src="./res/enemy_light_swan.png" alt="enemy light" width=33.333%><img src="./res/enemy_dark_swan.png" alt="enemy dark" width=33.333%>

*enemy in dynamic light and dark modes*

<img src="./res/enemy_light.png" alt="enemy light" width=50%><img src="./res/enemy_dark.png" alt="enemy dark" width=50%>

*enemy in static light and dark modes*

[hilt]: https://developer.android.com/training/dependency-injection/hilt-android/
[paging]: https://developer.android.com/topic/libraries/architecture/paging/v3-overview
[media]: https://developer.android.com/guide/topics/media/media3
[compose]: https://developer.android.com/jetpack/compose/
[coil]: https://coil-kt.github.io/coil/
[mvvm]: https://developer.android.com/topic/architecture/
[versions]: https://docs.gradle.org/current/userguide/platforms.html
[plugins]: https://docs.gradle.org/current/samples/sample_convention_plugins.html

[android studio]: https://developer.android.com/studio
