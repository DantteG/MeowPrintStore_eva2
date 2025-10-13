MeowPrint Store - Android project (skeleton)

Structure:
- app/src/main/java/com/meowprint/store/api       -> Retrofit, TokenManager, APIs
- app/src/main/java/com/meowprint/store/model     -> Data classes (Login, Product)
- app/src/main/java/com/meowprint/store/ui        -> Activities & Fragments
- app/src/main/res/layout                         -> XML layouts

How to use:
1. Open Android Studio Narwhal 2025.1.1 Patch 1.
2. Import the project folder 'meowprint_store_project' or create a new project and replace app module.
3. Sync Gradle. Add kapt plugin if using Glide (apply plugin: 'kotlin-kapt').
4. Replace placeholders (icons, app id) as needed.
5. Run on device/emulator. Use valid Xano credentials to login.

Endpoints used:
- Auth base: https://x8ki-letl-twmt.n7.xano.io/api:eg9IqgHd/
- Store base: https://x8ki-letl-twmt.n7.xano.io/api:Bcv1qHHX/

Notes:
- The code is a working skeleton adapted to your Xano endpoints.
- If the login response field for token differs, update Token extraction in MainActivity.
