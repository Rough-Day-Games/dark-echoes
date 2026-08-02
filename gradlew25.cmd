@echo off
setlocal
for /d %%D in ("%USERPROFILE%\.gradle\jdks\dark-echoes-jdk-25\jdk-*") do set "DARK_ECHOES_JAVA=%%~fD"
for /d %%D in ("%USERPROFILE%\.gradle\jdks\dark-echoes-jdk-21\jdk-*") do set "DARK_ECHOES_JAVA_21=%%~fD"
if not defined DARK_ECHOES_JAVA (
    echo Dark Echoes requires Java 25. Install it or provision it under the Gradle JDK cache.
    exit /b 1
)
if not exist "%DARK_ECHOES_JAVA%\bin\java.exe" (
    echo Java executable not found under %DARK_ECHOES_JAVA%.
    exit /b 1
)
set "JAVA_HOME=%DARK_ECHOES_JAVA%"
set "PATH=%JAVA_HOME%\bin;%PATH%"
if defined DARK_ECHOES_JAVA_21 set "ORG_GRADLE_PROJECT_org.gradle.java.installations.paths=%DARK_ECHOES_JAVA_21%,%DARK_ECHOES_JAVA%"
call "%~dp0gradlew.bat" %*
exit /b %ERRORLEVEL%
