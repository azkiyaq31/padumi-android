# CI/CD

## Workflows

| Workflow | Trigger | Responsibility |
|---|---|---|
| `CI` | Pull requests, pushes to `main`, manual | Runs `scripts/verify.sh`: unit and Robolectric UI tests, Android lint, Detekt, ktlint, Kover coverage, and debug assembly |
| `Instrumentation Tests` | Weekly and manual | Compose/instrumentation tests on an Ubuntu Android emulator |
| `Release Bundle` | `v*` tags and manual | Creates a shrunk, signed Android App Bundle |

`CI` runs one Ubuntu job, `Verify Padumi`, which calls the same `scripts/verify.sh` used locally,
so a green local run should mean a green CI run. Reports are uploaded as the `verification-reports`
artifact, including on failure. The four-tab navigation UI tests run on the JVM with Robolectric, so
they are part of this job and do not need an emulator. Instrumented tests (`src/androidTest`) run
only in the weekly `Instrumentation Tests` workflow, on an Ubuntu Android emulator.

## Release secrets

Configure these GitHub Actions repository secrets:

- `ANDROID_KEYSTORE_BASE64`: base64-encoded upload keystore
- `RELEASE_STORE_PASSWORD`
- `RELEASE_KEY_ALIAS`
- `RELEASE_KEY_PASSWORD`

Create the encoded value without adding a newline:

Ubuntu:

```bash
base64 -w 0 upload.keystore
```

macOS:

```bash
base64 < upload.keystore | tr -d '\n'
```

Windows PowerShell:

```powershell
[Convert]::ToBase64String([IO.File]::ReadAllBytes("upload.keystore"))
```

The workflow restores the keystore only inside the ephemeral runner and passes passwords as Gradle
properties. Keystores and credential files are ignored by Git.

## Branch protection

Protect `main`, require pull requests, require the `Verify Padumi` check, dismiss
stale approvals, and block force pushes. Keep release environments approval-gated when Play Store
publishing is introduced.

## Future deployment

The release workflow deliberately produces an AAB but does not upload it to Google Play. Add Play
publishing only after service-account ownership, environment approvals, track strategy, and rollback
responsibilities have been agreed.
