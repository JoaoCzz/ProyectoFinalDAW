#!/usr/bin/env bash
set -euo pipefail

APP_DIR="/opt/proyectofinal/app"
BACKEND_DIR="$APP_DIR/backend"
FRONTEND_DIR="$APP_DIR/frontend"
BACKEND_TARGET_DIR="/opt/proyectofinal/backend"
FRONTEND_TARGET_DIR="/var/www/proyectofinal/browser"
SERVICE_NAME="proyectofinal-backend"
BRANCH="${1:-main}"

echo "[1/7] Updating repository"
cd "$APP_DIR"
git fetch origin
git checkout "$BRANCH"
git pull origin "$BRANCH"

echo "[2/7] Building backend"
cd "$BACKEND_DIR"
chmod +x mvnw
./mvnw clean package -DskipTests

echo "[3/7] Publishing backend jar"
mkdir -p "$BACKEND_TARGET_DIR"
cp target/backend-0.0.1-SNAPSHOT.jar "$BACKEND_TARGET_DIR/backend.jar"

echo "[4/7] Building frontend"
cd "$FRONTEND_DIR"
npm ci
npm run build

echo "[5/7] Publishing frontend"
mkdir -p "$FRONTEND_TARGET_DIR"
rsync -av --delete dist/frontend/browser/ "$FRONTEND_TARGET_DIR/"

echo "[6/7] Restarting backend"
sudo systemctl restart "$SERVICE_NAME"

echo "[7/7] Reloading nginx"
sudo nginx -t
sudo systemctl reload nginx

echo "Deployment completed successfully"
