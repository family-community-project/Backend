#!/bin/bash
# FILE: deploy/scripts/stop.sh

echo "[Stop] 기존 애플리케이션 서비스를 중지합니다..."
# systemctl은 서비스가 존재하지 않거나 이미 중지된 상태여도 오류를 발생시키지 않음
systemctl stop ttasup || true