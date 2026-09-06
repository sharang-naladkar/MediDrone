# MediDrone (MediHelp Android)

## Build verification commands

From repository root:

```bash
./gradlew clean assembleDemoDebug assembleProdDebug
./gradlew lintDemoDebug lintProdDebug
```

## Run demo flavor (default offline smoke flow)

1. Open project in Android Studio.
2. Select Build Variant: `demoDebug`.
3. Run the app on an emulator/device.
4. Demo flow:
   - Sign in with **Demo Sign In**
   - Tap **SOS** and confirm
   - Dispatch progress animation runs
   - Live tracking renders telemetry path from `MockMqttClient` on canvas fallback

### Demo wiring guarantees

- `MockIncidentRepository` is bound in DI for incident flow.
- `MockMqttClient` is bound in DI for telemetry flow.
- Demo does **not** require Google Maps API key or Firebase setup.

## Switch to prod flavor

1. Select Build Variant: `prodDebug` (or `prodRelease`).
2. Set `BASE_API_URL` in `app/build.gradle.kts` product flavor `prod`.
3. Add your Google Maps API key:
   - Add `MAPS_API_KEY=YOUR_KEY` in local configuration and wire manifest metadata when enabling Maps SDK.
4. Add Firebase config for FCM:
   - Place `google-services.json` at `/home/runner/work/MediDrone/MediDrone/app/google-services.json` (do not commit).

## Token store notes

- Auth/network token storage should use encrypted local storage for prod (for example EncryptedSharedPreferences).
- Never commit tokens, API keys, or `google-services.json`.

## Manual setup notes

- This repository intentionally keeps demo mode runnable without external cloud credentials.
- If enabling real backend/MQTT implementations, keep demo flavor on mock bindings to preserve offline demos.
