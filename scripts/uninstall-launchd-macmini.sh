#!/usr/bin/env bash
set -euo pipefail

PLIST_DST="${HOME}/Library/LaunchAgents/com.portfolio.macmini.plist"
launchctl bootout gui/$UID "$PLIST_DST" 2>/dev/null || true
rm -f "$PLIST_DST"
echo "Launchd removed: $PLIST_DST"
