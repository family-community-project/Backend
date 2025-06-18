#!/bin/bash
echo "[ApplicationStart] Spring Boot 앱 실행"

cd /home/ubuntu/app

nohup java -jar app.jar > app.log 2>&1 &