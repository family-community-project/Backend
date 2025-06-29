#!/bin/bash
echo "[BeforeInstall] 시작: 기존 앱 종료, JDK 설치, 디렉토리 준비"

# 1. 기존에 실행 중인 애플리케이션 프로세스 종료
# 'pkill'은 프로세스가 없으면 에러를 반환하므로 '|| true'를 붙여 스크립트가 중단되지 않게함
echo "기존 Java 프로세스 종료 시도..."
pkill -f 'app.jar' || true
sleep 5 # 프로세스가 완전히 종료될 때까지 잠시 대기합니다.

# 2. Java 17 (JDK) 설치 (없는 경우에만)
# 'command -v java'는 java 명령어가 존재하면 true를 반환
if ! command -v java &> /dev/null || ! java -version 2>&1 | grep -q "17"; then
  echo "Java 17이 설치되어 있지 않습니다. 설치를 시작합니다."
  apt-get update -y
  apt-get install -y openjdk-17-jdk
else
  echo "Java 17이 이미 설치되어 있습니다."
fi

# 3. 배포 디렉토리 생성 및 권한 설정
APP_DIR="/home/ubuntu/app"
echo "배포 디렉토리($APP_DIR)를 준비합니다."

# 디렉토리가 존재하지 않으면 생성합니다.
if [ ! -d "$APP_DIR" ]; then
    mkdir -p "$APP_DIR"
fi

# 디렉토리 소유권을 ubuntu 사용자로 변경하여, 이후 단계에서 ubuntu 사용자가 파일을 쓸 수 있게 함
chown -R ubuntu:ubuntu "$APP_DIR"

echo "디렉토리 준비 완료."
