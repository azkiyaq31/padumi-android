# WearWise

A production-oriented, reusable Android project template. It uses modular MVVM, unidirectional state
flow, convention plugins, dependency injection, layered tests, and cross-platform CI.

## Create a project from the template

This repository was generated from [RhymezxCode/android-project-template](https://github.com/RhymezxCode/android-project-template) and initialized as WearWise (`com.padumi`). The application ID and namespace are `com.padumi`; debug builds add `.debug` to the application ID.

## Project map

```text
app
 ├── feature:home
 └── core:data ──implements──> core:domain ──> core:model
      feature:home ───────────> core:domain
      feature:home ───────────> core:designsystem

core:testing ──> shared rules and fakes used only by tests
build-logic  ──> reusable Gradle convention plugins
```

| Module | Responsibility |
|---|---|
| `:app` | Application entry point, dependency graph assembly, and top-level navigation |
| `:feature:home` | Compose screen, ViewModel, state/actions/effects, and feature tests |
| `:core:model` | Framework-free shared models |
| `:core:domain` | Repository contracts and use cases |
| `:core:data` | Repository implementations and data sources |
| `:core:designsystem` | Material 3 theme and shared UI foundations |
| `:core:testing` | Coroutine rules, fakes, and shared test helpers |
| `build-logic` | Android, Compose, Hilt, feature, and JVM Gradle conventions |

The demonstration screen follows this complete path:

`Compose → HomeViewModel → use cases → GreetingRepository → local data source`

## Requirements

- Android Studio with Android SDK 36
- JDK 17 (project convention plugins use a Java 17 toolchain)
- `ANDROID_HOME` or `ANDROID_SDK_ROOT` configured

On this development machine, Android Studio's JDK 21 is configured as the Gradle runtime; JDK 17 is provisioned locally under `.tools` for Gradle's compile toolchain. `.tools` and `local.properties` are machine-local and not committed.

## Start locally

Ubuntu or macOS:

```bash
./scripts/setup.sh
./scripts/verify.sh
./gradlew installDebug
```

Windows PowerShell:

```powershell
.\scripts\setup.ps1
.\scripts\verify.ps1
.\gradlew.bat installDebug
```

`local.properties` is machine-specific and intentionally ignored. Never commit secrets or signing
files. See [CI/CD](docs/CICD.md) for the release secret names.

## Engineering and publishing guides

- [Architecture and module rules](docs/ARCHITECTURE.md)
- [Testing strategy](docs/TESTING.md)
- [CI/CD across Ubuntu, macOS, and Windows](docs/CICD.md)
- [Contribution workflow](CONTRIBUTING.md)
- [Security policy](SECURITY.md)
