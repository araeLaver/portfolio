#!/usr/bin/env bash
set -euo pipefail

BASE_DIR="${PORTFOLIO_APP_DIR:-$(cd "$(dirname "$0")" && pwd)/..}"
ENV_FILE="${PORTFOLIO_ENV_FILE:-$BASE_DIR/.portfolio.env}"
AUTO_LAUNCHD="${PORTFOLIO_AUTO_LAUNCHD:-1}"

cd "$BASE_DIR"

if [ ! -f "$ENV_FILE" ]; then
  echo "Missing environment file: $ENV_FILE"
  echo "Copy scripts/portfolio.env.example to .portfolio.env and fill values first."
  exit 1
fi

set -a
# shellcheck disable=SC1090
. "$ENV_FILE"
set +a

if [ -z "${PORTFOLIO_BASE_URL:-}" ]; then
  echo "PORTFOLIO_BASE_URL is required in $ENV_FILE"
  exit 1
fi

if [ -z "${DATABASE_URL:-}" ]; then
  echo "DATABASE_URL is required in $ENV_FILE"
  exit 1
fi

if [ -z "${ADMIN_PASSWORD:-}" ]; then
  echo "ADMIN_PASSWORD is required in $ENV_FILE"
  exit 1
fi

./scripts/deploy-macmini.sh

./scripts/healthcheck-macmini.sh "$PORTFOLIO_BASE_URL"

if [ "$AUTO_LAUNCHD" = "1" ]; then
  ./scripts/install-launchd-macmini.sh
fi

echo "Cutover complete."
echo "Application base URL: $PORTFOLIO_BASE_URL"
echo "Health: $PORTFOLIO_BASE_URL/actuator/health"
