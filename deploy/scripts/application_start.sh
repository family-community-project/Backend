# 파일: deploy/scripts/application_start.sh (이전 버전과 동일)
# ubuntu 사용자로 실행되며, 이제 모든 권한을 가지고 앱을 실행합니다.

#!/bin/bash
echo "[ApplicationStart] Spring Boot 앱 실행"

APP_DIR="/home/ubuntu/app"
APP_JAR="$APP_DIR/app.jar"
LOG_FILE="$APP_DIR/app.log"
PID_FILE="$APP_DIR/app.pid"

# 기존에 실행 중인 애플리케이션이 있다면 종료
if [ -f "$PID_FILE" ]; then
    PID=$(cat "$PID_FILE")
    if ps -p $PID > /dev/null; then
        echo "기존 프로세스 종료: $PID"
        kill -9 $PID
    fi
    rm -f "$PID_FILE"
fi

# JAR 파일이 존재하는지 확인
if [ ! -f "$APP_JAR" ]; then
  echo "오류: ApplicationStart 단계에서 $APP_JAR 파일을 찾을 수 없습니다."
  exit 1
fi

# nohup으로 백그라운드에서 애플리케이션 실행
echo "Spring Boot 애플리케이션 실행..."
nohup java -jar "$APP_JAR" > "$LOG_FILE" 2>&1 &

# 실행된 프로세스의 ID(PID)를 파일에 저장
echo $! > "$PID_FILE"

echo "애플리케이션이 시작되었습니다. 로그는 $LOG_FILE 에서 확인하세요."