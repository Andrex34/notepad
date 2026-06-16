@echo off
title Notepad
cd /d "C:\Users\Andrex\Desktop\notepad"

set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.0.19.10-hotspot
set PATH=%JAVA_HOME%\bin;%PATH%

echo Запуск Notepad...
call gradlew.bat :composeApp:run --no-daemon --stacktrace
echo.
echo Код возврата: %ERRORLEVEL%
pause
