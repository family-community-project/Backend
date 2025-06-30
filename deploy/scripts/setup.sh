#!/bin/bash

echo "[Setup] 서버 환경을 준비합니다..."

# Java 17 (JDK)이 설치되어 있지 않은 경우에만 설치 진행
if ! command -v java &> /dev/null || ! java -version 2>&1 | grep -q "17"; then
  echo "Java 17이 설치되어 있지 않습니다. 설치를 시작합니다."
  # apt-get은 root 권한 필요
  apt-get update -y
  apt-get install -y openjdk-17-jdk
else
  echo "Java 17이 이미 설치되어 있습니다."
fi

# 배포할 애플리케이션 디렉토리를 생성합니다.
APP_DIR="/home/ubuntu/app"
if [ ! -d "$APP_DIR" ]; then
    mkdir -p "$APP_DIR"
fi

echo "서버 환경 준비가 완료되었습니다."