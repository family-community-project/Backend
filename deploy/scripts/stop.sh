#!/bin/bash
echo "[BeforeInstall] 서버 환경 설정..."

# 1. Java 17 (JDK) 설치 (없는 경우에만)
if ! command -v java &> /dev/null || ! java -version 2>&1 | grep -q "17"; then
  echo "Java 17을 찾을 수 없습니다. 설치중..."
  apt-get update -y
  apt-get install -y openjdk-17-jdk
else
  echo "Java 17이 이미 설치되어있습니다.."
fi

# 2. 배포 디렉토리 생성
APP_DIR="/home/ubuntu/app"
if [ ! -d "$APP_DIR" ]; then
    mkdir -p "$APP_DIR"
fi