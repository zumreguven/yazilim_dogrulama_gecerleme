#!/usr/bin/env bash
set -euo pipefail

# Local helper to run full CI flow using docker-compose.ci.yml
# Usage: ./scripts/ci_local.sh

COMPOSE_FILE=docker-compose.ci.yml

echo "Building and starting services..."
docker compose -f $COMPOSE_FILE up -d --build

echo "Waiting for application to become healthy (inside compose network)..."
docker compose -f $COMPOSE_FILE run --rm maven bash -c 'until curl -sSf http://app:8080/actuator/health >/dev/null 2>&1 || curl -sSf http://app:8080/ >/dev/null 2>&1; do sleep 2; done'

echo "Running unit tests..."
docker compose -f $COMPOSE_FILE run --rm -e DB_HOST=postgres maven mvn -B -DskipITs=true test -Dselenium.headless=true -Dapp.baseUrl=http://app:8080 -Dselenium.remote.url=http://selenium-hub:4444/wd/hub

echo "Running integration tests..."
docker compose -f $COMPOSE_FILE run --rm -e DB_HOST=postgres maven mvn -B -DskipTests=true verify -Dselenium.headless=true -Dapp.baseUrl=http://app:8080 -Dselenium.remote.url=http://selenium-hub:4444/wd/hub

echo "Running Selenium tests (login)..."
docker compose -f $COMPOSE_FILE run --rm -e DB_HOST=postgres maven mvn -Dtest=GirisTesti test -Dselenium.remote.url=http://selenium-hub:4444/wd/hub -Dapp.baseUrl=http://app:8080 -Dselenium.headless=true

echo "Running Selenium tests (create ad)..."
docker compose -f $COMPOSE_FILE run --rm -e DB_HOST=postgres maven mvn -Dtest=IlanTesti test -Dselenium.remote.url=http://selenium-hub:4444/wd/hub -Dapp.baseUrl=http://app:8080 -Dselenium.headless=true

echo "Tearing down..."
docker compose -f $COMPOSE_FILE down --volumes --remove-orphans

echo "CI local flow completed."