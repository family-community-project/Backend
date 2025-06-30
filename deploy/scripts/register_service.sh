#!/bin/bash

echo "[Register] 애플리케이션 권한을 설정하고 서비스를 등록합니다..."

# 1. 파일 권한 설정
# systemd 서비스 파일에서 User=ubuntu로 지정했으므로,
# ubuntu 사용자가 app 디렉토리와 그 안의 파일(app.jar)을 소유하도록 함
APP_DIR="/home/ubuntu/app"
chown -R ubuntu:ubuntu "$APP_DIR"
echo "'$APP_DIR' 디렉토리의 소유자를 'ubuntu'로 변경했습니다."

# 2. systemd 서비스 파일 이동 및 등록
# CodeDeploy가 복사한 서비스 파일을 systemd가 관리하는 경로로 이동시킴
mv /home/ubuntu/app/ttasup.service /etc/systemd/system/ttasup.service
echo "서비스 파일을 '/etc/systemd/system/' 경로로 이동했습니다."

# systemd가 새로운 서비스 파일을 인식하도록 reload
systemctl daemon-reload
# 서버 부팅 시 서비스가 자동으로 시작되도록 활성화
systemctl enable ttasup
echo "'ttasup' 서비스가 부팅 시 자동으로 시작되도록 활성화되었습니다."