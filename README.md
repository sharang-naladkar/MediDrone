# MediHelp - Dronuts

This repository contains the Android client for the MediHelp emergency drone delivery demo (Smart India Hackathon 2026).

This commit scaffolds the project with Kotlin + Jetpack Compose, Hilt DI, Room, and placeholder repositories.

What I added in this iteration:
- Gradle KTS root and app module (basic deps)
- Version catalog (gradle/libs.versions.toml)
- Hilt application class and DI module
- Simple Room database + Incident entity/DAO
- Repository interface + mock implementation
- Navigation host and three screens: Splash, Auth, Home
- README with next steps

Next planned steps (I'll proceed in further commits):
1. Implement Compose UI for all required screens with mock data flows and animated dispatch stepper.
2. Add MQTT client abstraction and mock; implement live tracking screen with map preview using Google Maps Compose.
3. Wire Retrofit networking, JWT auth (EncryptedSharedPreferences) and token refresh flows.
4. Add FCM integration and deep-link handling.
5. Polish animations, error handling, offline queueing (Room), and accessibility.

Setup notes (quick):
- Open in Android Studio Flamingo+ and build.
- Add Google Maps API key in local.properties as MAPS_API_KEY and configure the manifest per README.
- Replace Firebase setup with your project credentials for FCM.

