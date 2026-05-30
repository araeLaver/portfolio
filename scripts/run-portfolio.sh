#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
APP_DIR="${PORTFOLIO_APP_DIR:-$SCRIPT_DIR/..}"
ENV_FILE="${PORTFOLIO_ENV_FILE:-$APP_DIR/.portfolio.env}"

cd "$APP_DIR"

if [ -f "$ENV_FILE" ]; then
  set -a
  # shellcheck disable=SC1090
  . "$ENV_FILE"
  set +a
fi

JAR_NAME="${PORTFOLIO_JAR_NAME:-portfolio-app-0.0.1-SNAPSHOT.jar}"
JAVA_BIN="${PORTFOLIO_JAVA_BIN:-java}"

if [ -n "${PORTFOLIO_JAVA_OPTS:-}" ]; then
  JAVA_OPTS="$PORTFOLIO_JAVA_OPTS"
elif [ "$(uname -s)" = "Darwin" ]; then
  JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom"
else
  JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Djava.security.egd=file:/dev/./urandom"
fi

if [ "$JAVA_BIN" = "java" ] && [ -n "${JAVA_HOME:-}" ] && [ -x "${JAVA_HOME}/bin/java" ]; then
  JAVA_BIN="${JAVA_HOME}/bin/java"
fi

JAR_PATH="$(ls target/$JAR_NAME 2>/dev/null || ls target/*.jar | head -n 1)"
if [ -z "$JAR_PATH" ]; then
  echo "Jar not found in target/. Run build first."
  exit 1
fi

echo "Start portfolio with $JAR_PATH using $JAVA_BIN"
exec "$JAVA_BIN" $JAVA_OPTS -jar "$JAR_PATH"
