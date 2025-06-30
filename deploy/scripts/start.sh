#!/bin/bash
# FILE: deploy/scripts/start.sh

echo "[Start] 애플리케이션 서비스를 시작합니다..."

# systemd를 통해 서비스 시작 - root 권한 필요
systemctl start ttasup

sleep 5
echo "서비스 시작 후 현재 상태:"
systemctl status ttasup || true