#!/bin/bash
echo "[ApplicationStart] Spring Boot 앱 실행"

# 필요한 경로들을 변수로 지정
APP_DIR="/home/ubuntu/app"
APP_JAR="$APP_DIR/app.jar"
LOG_FILE="$APP_DIR/app.log"
PID_FILE="$APP_DIR/app.pid"

# 기존에 실행 중인 애플리케이션이 있다면 종료
if [ -f "$PID_FILE" ]; then
    echo "기존 프로세스 종료 시도: $(cat $PID_FILE)"
    kill -9 $(cat $PID_FILE)
    rm -f "$PID_FILE"
    sleep 5
fi

# 로그 파일 생성 및 권한 설정
touch "$LOG_FILE"
chown ubuntu:ubuntu "$LOG_FILE"

# nohup을 사용하여 백그라운드에서 애플리케이션 실행 (절대 경로 사용)
echo "Spring Boot 애플리케이션 실행..."
nohup java -jar "$APP_JAR" > "$LOG_FILE" 2>&1 &

# 실행된 프로세스의 ID(PID)를 파일에 저장
echo $! > "$PID_FILE"

echo "애플리케이션이 시작되었습니다. 로그는 $LOG_FILE 에서 확인하세요."