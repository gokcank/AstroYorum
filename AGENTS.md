# AstroYorum

Turkish astrology and tarot app for Android (`com.gokcank.astroyorum`): AI-generated daily horoscopes, tarot spreads,
moon phase; backend on Supabase (Postgres + Edge Functions) with Groq-hosted models.

## Build & test
- JDK 17; Kotlin 2.3.20; compile/target SDK 36, minSdk 26.
- Debug APK: `./gradlew assembleDebug --stacktrace`
- Unit tests: `./gradlew testDebugUnitTest --stacktrace`
- Release AAB: `./gradlew bundleRelease` (R8 + resource shrinking, full native debug symbols). Needs `astroyorum.jks`
  in the repo root and `KEYSTORE_PASSWORD` / `KEY_PASSWORD` in `local.properties`; without them the release is unsigned.
- `app/google-services.json` (Firebase Analytics + Crashlytics) is required and git-ignored. CI writes a mock one.
- Other `local.properties` keys: `SUPABASE_URL`, `SUPABASE_ANON_KEY`, `ADMOB_APP_ID`, `ADMOB_BANNER_ID`,
  `ADMOB_INTERSTITIAL_ID`, `ADMOB_REWARDED_ID`. AdMob falls back to Google test IDs; Supabase values fall back to empty.
- CI: `.github/workflows/android-ci.yml` (testDebugUnitTest + assembleDebug on push/PR to `main`; runs queue).

## Layout
- `app/src/main/java/com/gokcank/astroyorum/`: Navigation 3 (`Navigation.kt`, `NavigationKeys.kt`), `ui/` (one
  package per screen: home, tarot, moon, zodiac, birthchart, profile, onboarding, splash), `data/` (repositories,
  local tarot/zodiac data, remote config), `ads/AdMobHelper.kt` (ad config + master switch `AdConfig.ENABLE_ADS`),
  `scheduler/` + `receiver/` (daily 09:00 reminder).
- `supabase/functions/update-horoscopes/` — Deno Edge Function that generates horoscopes via Groq.
- `supabase/migrations/` — database schema. `supabase/.env` holds server secrets (git-ignored).

## Project rules
- No secrets in Kotlin, Gradle or the manifest: Supabase keys, AdMob IDs and keystore passwords come only from
  `local.properties` via `BuildConfig` / manifest placeholders.
- The Groq API key lives only in the Edge Function environment; the app holds only the Supabase anon key.
  Never call Groq from the client.
- Never commit `google-services.json` or `supabase/.env`; CI uses a mock config file.
- AdMob: be careful when testing with live IDs; register your device as a test device. Prefer Google test IDs during
  development (new ad units return "No Fill" for up to 48 hours).
- Horoscopes have three parts (`general`, `love`, `career`). If today's horoscope is missing, the app triggers
  generation on demand; network errors show a retry button, never an empty screen.
- Notifications need the Android 13+ runtime permission (`POST_NOTIFICATIONS`).
- Use `MaterialTheme.colorScheme` tokens for text and icons (no fixed black/white); use Material 3 only, no Material 2.
- `androidx.fragment` is pinned in `gradle/libs.versions.toml` to silence a Play Console advisory; keep the direct dependency.

## Status (as of 2026-11-07)
- Project status: **paused** (set 2026-11-18; source of truth: the brain page).
- v1.1.3 (`versionCode = 8`, 2026-09-26). Google Play closed test (14 days, 20 testers) completed.

## Open items
- Debug builds serve live ads when live AdMob IDs are in `local.properties`: force Google test IDs in debug.
- Without signing values the release build comes out unsigned; add a debug-signing fallback.
- Production access questionnaire and production release in Play Console.
- Monitor crash rates after production.

## More context
Background, decisions and history are in the second brain: `/home/gokcank/ikinci-beyin/projects/astroyorum.md` (read on demand).
