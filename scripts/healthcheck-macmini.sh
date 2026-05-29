#!/usr/bin/env bash
set -euo pipefail

BASE_URL="${1:-${PORTFOLIO_BASE_URL:-http://localhost:8080}}"
echo "health check: $BASE_URL/actuator/health"
curl -fsS "$BASE_URL/actuator/health"
echo "health ok"
