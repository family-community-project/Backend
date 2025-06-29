#!/bin/bash
echo "[AfterInstall] 권한 설정"

# 배포된 JAR 파일의 절대 경로
APP_JAR="/home/ubuntu/app/app.jar"

# 파일이 존재하는지 확인하고 실행 권한 부여
if [ -f "$APP_JAR" ]; then
  chmod +x "$APP_JAR"
  chown ubuntu:ubuntu "$APP_JAR"
  echo "실행 권한 부여 완료: $APP_JAR"
else
  echo "오류: AfterInstall 단계에서 $APP_JAR 파일을 찾을 수 없습니다."
  exit 1
fi