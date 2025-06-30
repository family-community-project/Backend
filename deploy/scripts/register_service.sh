#!/bin/bash
echo "[AfterInstall] 권한 설정 및 서비스 등록..."

# 1. 파일 권한 설정
APP_DIR="/home/ubuntu/app"
chown -R ubuntu:ubuntu "$APP_DIR"
echo "$APP_DIR 을 'ubuntu' 사용자로 설정..."

# 2. systemd 서비스 파일 이동 및 등록
mv /home/ubuntu/app/ttasup.service /etc/systemd/system/ttasup.service
echo "서비스 파일이 /etc/systemd/system/으로 이동됨..."

# systemd가 새로운 서비스 파일을 인식하도록 reload
systemctl daemon-reload
# 서버 부팅 시 서비스가 자동으로 시작되도록 활성화
systemctl enable ttasup
echo "'ttasup' 서비스 부팅 시작..."