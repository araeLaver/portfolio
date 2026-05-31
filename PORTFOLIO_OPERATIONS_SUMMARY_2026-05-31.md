# Portfolio Operations Summary - 2026-05-31

## Current Status

- Portfolio app is running locally on Mac mini through `launchd`.
- Public access is exposed through a Cloudflare quick tunnel.
- PostgreSQL is used as the runtime database.
- Koyeb is no longer the portfolio app hosting target, but one project demo link still points to a Koyeb app because it is the requested TravelMate demo URL.

## Runtime Paths

- Source repo: `/Volumes/WorkDrive/Develop/19_portfolio/portfolio`
- Runtime clone: `/Users/down/portfolio-service`
- Runtime env file: `/Users/down/portfolio-service/.portfolio.env`
- App log: `/Users/down/portfolio-service/logs/portfolio.log`
- Cloudflare tunnel log: `/Users/down/portfolio-service/logs/cloudflared-quick-launchd.log`

## Services

### Portfolio App

- LaunchAgent label: `com.portfolio.macmini`
- Internal app URL: `http://localhost:28080`
- Internal health URL: `http://localhost:28080/actuator/health`
- Last verified app PID: `56162`
- Last verified result:
  - Home: `200`
  - Health: `{"status":"UP"}`

### Cloudflare Quick Tunnel

- LaunchAgent label: `com.portfolio.quicktunnel`
- Tunnel target: `http://localhost:28080`
- Public portfolio URL:
  - `https://livecam-animals-gasoline-launches.trycloudflare.com`
- Last verified cloudflared PID: `48590`
- Last verified result:
  - Home: `200`
  - Health: `{"status":"UP"}`

Note: `trycloudflare.com` quick tunnel URLs are temporary. If `cloudflared` restarts, the generated public URL may change.

## Database

- PostgreSQL database: `portfolio`
- PostgreSQL schema: `java_portfolio`
- Runtime table updated: `java_portfolio.projects`
- Do not store database password or admin password in this document. They are kept in `.portfolio.env`.

## Live Demo URL Mapping

| Project | Project ID | Live Demo URL |
| --- | --- | --- |
| Chat / BEAM | `beam-messenger` | `https://stylish-educated-gmbh-axis.trycloudflare.com` |
| TravelMate | `travelmate` | `https://various-belva-untab-1a59bee2.koyeb.app/` |
| Idea Manager | `idea-manager` | `https://tue-dollars-chris-velocity.trycloudflare.com` |

## Changes Made

- Migrated portfolio deployment assets toward Mac mini self-hosting.
- Added one-command Mac mini deployment flow with `scripts/cutover-macmini.sh`.
- Hardened service startup checks so health checks do not falsely pass against unrelated local port forwards.
- Moved runtime execution from external volume to `/Users/down/portfolio-service` because macOS System Policy blocked LaunchAgent execution from `/Volumes/WorkDrive/...`.
- Registered the portfolio app as a LaunchAgent.
- Registered Cloudflare quick tunnel as a LaunchAgent.
- Updated project Live Demo links directly in PostgreSQL.
- Updated `.portfolio.env` values so demo links persist across app restarts.

## Important Commits

- `1c82c33` - migrate deployment assets to Mac mini self-hosting
- `b44e1f9` - add Mac mini one-command cutover workflow
- `136fa86` - add detailed Koyeb offboarding runbook
- `0e851db` - fix offboarding runbook link in README
- `2bc3adb` - remove Koyeb wording from CI deploy log
- `ab07c68` - preserve cutover app directory defaults
- `d27427e` - harden Mac mini service startup checks

## Verification Commands

```bash
launchctl list com.portfolio.macmini
launchctl list com.portfolio.quicktunnel

curl -fsS http://localhost:28080/actuator/health
curl -fsS -o /dev/null -w '%{http_code}\n' http://localhost:28080/

curl -fsS https://livecam-animals-gasoline-launches.trycloudflare.com/actuator/health
curl -fsS -o /dev/null -w '%{http_code}\n' https://livecam-animals-gasoline-launches.trycloudflare.com/
```

## Restart Commands

```bash
launchctl kickstart -k gui/$UID/com.portfolio.macmini
launchctl kickstart -k gui/$UID/com.portfolio.quicktunnel
```

## Stop Commands

```bash
launchctl bootout gui/$UID ~/Library/LaunchAgents/com.portfolio.macmini.plist
launchctl bootout gui/$UID ~/Library/LaunchAgents/com.portfolio.quicktunnel.plist
```

## Next Operational Step

- For a stable permanent URL, replace the quick tunnel with a named Cloudflare Tunnel or a custom domain.
- If staying with quick tunnel, check `cloudflared-quick-launchd.log` after every restart and update the portfolio public URL if the generated `trycloudflare.com` address changes.
