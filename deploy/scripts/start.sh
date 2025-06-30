#!/bin/bash
echo "[ApplicationStart] 서비스 시작..."
systemctl start ttasup
# 서비스 상태를 확인하여 배포 성공 여부를 명확히 함
systemctl status ttasup