#!/usr/bin/env bash
set -euo pipefail

PLIST_SRC="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)/portfolio-macmini-launchd.plist"
PLIST_DST="${HOME}/Library/LaunchAgents/com.portfolio.macmini.plist"
APP_DIR="${PORTFOLIO_APP_DIR:-/opt/portfolio}"

mkdir -p "${HOME}/Library/LaunchAgents"
sed "s#{{APP_DIR}}#${APP_DIR}#g" "$PLIST_SRC" > "$PLIST_DST"
launchctl bootout gui/$UID "$PLIST_DST" 2>/dev/null || true
launchctl bootstrap gui/$UID "$PLIST_DST"
launchctl enable gui/$UID/com.portfolio.macmini
launchctl kickstart -k gui/$UID/com.portfolio.macmini

echo "Launchd installed: $PLIST_DST"
