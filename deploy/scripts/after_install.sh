#!/bin/bash
echo "[AfterInstall] 권한 설정 및 기타 작업"

APP_JAR="/home/ubuntu/app/app.jar"

if [ -f "$APP_JAR" ]; then
  chmod +x "$APP_JAR"
  chown ubuntu:ubuntu "$APP_JAR"
  echo "실행 권한 부여 완료"
else
  echo "오류: $APP_JAR 파일이 존재하지 않습니다."
  exit 1
fi