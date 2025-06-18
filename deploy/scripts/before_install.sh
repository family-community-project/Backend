#!/bin/bash
echo "[BeforeInstall] 기존 앱 종료 및 JDK 설치 확인"

# 기존 java 프로세스 종료 (있으면)
pkill -f 'java -jar /home/ubuntu/app/app.jar' || true

# JDK 17 설치 (없는 경우에만 설치 진행)
if ! java -version 2>&1 | grep "17"; then
  sudo apt-get update -y
  sudo apt-get install -y openjdk-17-jdk
fi