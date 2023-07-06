#!/bin/bash
APP_PATH="build/libs/application-0.0.1-SNAPSHOT.jar"

# application-0.0.1-SNAPSHOT.jar 가 실행중이라면 프로세서를 종료
ps -ef | grep "application-0.0.1-SNAPSHOT.jar" | grep -v grep | awk '{print $2}' | xargs kill -9
echo "application Stop Success"

# 빌드
./gradlew build

# application-0.0.1-SNAPSHOT.jar를 다시 실행하기 위해 진행을 진행합니다.
# 배경에서 application 0.0.1-SNAPSHOT.jar을 실행
if nohup java -jar "$APP_PATH" --spring.profiles.active=dev > /dev/null 2>&1 & then
  echo "application Start Success"
  sleep 5s
  PORT=$(sudo lsof -t -i :8080)
  echo "application is running on port $PORT"
else
  echo "애플리케이션이 동작하지 안습니다."
fi