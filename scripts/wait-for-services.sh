#!/usr/bin/env bash
set -euo pipefail

# Wait for app and selenium to be ready. Usage: wait-for-services.sh <app-url> <selenium-url> <timeout-secs>
APP_URL=${1:-http://app:8080/actuator/health}
SELENIUM_URL=${2:-http://selenium-hub:4444/status}
TIMEOUT=${3:-120}

echo "Waiting for APP ($APP_URL) and SELENIUM ($SELENIUM_URL) to be ready (timeout=${TIMEOUT}s)..."

start_ts=$(date +%s)

until curl -sSf "$APP_URL" >/dev/null 2>&1 || curl -sSf "${APP_URL%/}" >/dev/null 2>&1; do
  now_ts=$(date +%s)
  if (( now_ts - start_ts > TIMEOUT )); then
    echo "Timed out waiting for APP at $APP_URL"
    exit 1
  fi
  sleep 2
done

echo "APP is ready. Waiting for Selenium..."

start_ts=$(date +%s)

until curl -sSf "$SELENIUM_URL" | grep -q '"ready":true' >/dev/null 2>&1; do
  now_ts=$(date +%s)
  if (( now_ts - start_ts > TIMEOUT )); then
    echo "Timed out waiting for Selenium at $SELENIUM_URL"
    exit 1
  fi
  sleep 2
done

echo "Selenium is ready."
exit 0
