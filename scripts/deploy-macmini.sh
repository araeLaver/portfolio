#!/usr/bin/env bash
set -euo pipefail

APP_DIR="${PORTFOLIO_APP_DIR:-/opt/portfolio}"
JAR_NAME="${PORTFOLIO_JAR_NAME:-portfolio-app-0.0.1-SNAPSHOT.jar}"
SERVICE_NAME="${PORTFOLIO_SERVICE_NAME:-portfolio}"
LOG_DIR="${PORTFOLIO_LOG_DIR:-$APP_DIR/logs}"
LOG_FILE="$LOG_DIR/$SERVICE_NAME.log"
PID_FILE="$LOG_DIR/$SERVICE_NAME.pid"
ENV_FILE="${PORTFOLIO_ENV_FILE:-$APP_DIR/.portfolio.env}"
JAVA_BIN="${PORTFOLIO_JAVA_BIN:-java}"

mkdir -p "$APP_DIR"
mkdir -p "$LOG_DIR"
cd "$APP_DIR"

if [ -f "$ENV_FILE" ]; then
  # shellcheck disable=SC2046
  set -a
  # shellcheck disable=SC1091
  . "$ENV_FILE"
  set +a
fi

JAR_NAME="${PORTFOLIO_JAR_NAME:-$JAR_NAME}"
SERVICE_NAME="${PORTFOLIO_SERVICE_NAME:-$SERVICE_NAME}"
LOG_DIR="${PORTFOLIO_LOG_DIR:-$LOG_DIR}"
LOG_FILE="$LOG_DIR/$SERVICE_NAME.log"
PID_FILE="$LOG_DIR/$SERVICE_NAME.pid"
JAVA_BIN="${PORTFOLIO_JAVA_BIN:-$JAVA_BIN}"

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

echo "[1/4] Build"
mvn clean package -DskipTests -B

JAR_FILE=$(ls target/$JAR_NAME 2>/dev/null || ls target/*.jar | head -n 1)
if [ -z "$JAR_FILE" ]; then
  echo "Jar file not found in target/"
  exit 1
fi

echo "[2/4] Stop existing process"
if [ -f "$PID_FILE" ]; then
  OLD_PID=$(cat "$PID_FILE" || true)
  if [ -n "$OLD_PID" ] && kill -0 "$OLD_PID" 2>/dev/null; then
    kill -TERM "$OLD_PID" || true
    sleep 2
    kill -0 "$OLD_PID" 2>/dev/null && kill -KILL "$OLD_PID" || true
  fi
  rm -f "$PID_FILE"
fi

pkill -f "$JAR_NAME" || true


echo "[3/4] Start service"
nohup "$JAVA_BIN" $JAVA_OPTS -jar "$JAR_FILE" > "$LOG_FILE" 2>&1 &
echo $! > "$PID_FILE"

echo "[4/4] Health check"
HEALTH_URL="http://127.0.0.1:${PORT:-8080}/actuator/health"
STARTED=0
i=1
while [ "$i" -le 30 ]; do
  SERVICE_PID=$(cat "$PID_FILE")
  if ! kill -0 "$SERVICE_PID" 2>/dev/null; then
    echo "process exited before health check passed. Check: $LOG_FILE"
    tail -n 80 "$LOG_FILE" || true
    exit 1
  fi

  if curl -fsS "$HEALTH_URL"; then
    STARTED=1
    break
  fi

  sleep 1
  i=$((i + 1))
done

if [ "$STARTED" != "1" ]; then
  echo "health check failed: $HEALTH_URL"
  echo "Check: $LOG_FILE"
  tail -n 80 "$LOG_FILE" || true
  exit 1
fi

echo "Portfolio deployed on PID $(cat "$PID_FILE")"
echo "Log: $LOG_FILE"
