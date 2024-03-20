@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%"=="" @echo off
@rem ##########################################################################
@rem
@rem  Home_Automation startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%"=="" set DIRNAME=.
@rem This is normally unused
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and HOME_AUTOMATION_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if %ERRORLEVEL% equ 0 goto execute

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\Home_Automation-0.1.jar;%APP_HOME%\lib\pi4micronaut-utils-v1.0-all.jar;%APP_HOME%\lib\micronaut-http-client-3.10.1.jar;%APP_HOME%\lib\micronaut-http-server-netty-3.10.1.jar;%APP_HOME%\lib\micronaut-http-netty-3.10.1.jar;%APP_HOME%\lib\micronaut-http-server-3.10.1.jar;%APP_HOME%\lib\micronaut-websocket-3.10.1.jar;%APP_HOME%\lib\micronaut-http-client-core-3.10.1.jar;%APP_HOME%\lib\micronaut-runtime-3.10.1.jar;%APP_HOME%\lib\micronaut-jackson-databind-3.10.1.jar;%APP_HOME%\lib\micronaut-validation-3.10.1.jar;%APP_HOME%\lib\micronaut-jackson-core-3.10.1.jar;%APP_HOME%\lib\micronaut-json-core-3.10.1.jar;%APP_HOME%\lib\micronaut-router-3.10.1.jar;%APP_HOME%\lib\micronaut-http-3.10.1.jar;%APP_HOME%\lib\micronaut-buffer-netty-3.10.1.jar;%APP_HOME%\lib\micronaut-context-3.10.1.jar;%APP_HOME%\lib\micronaut-aop-3.10.1.jar;%APP_HOME%\lib\micronaut-inject-3.10.1.jar;%APP_HOME%\lib\jakarta.annotation-api-2.1.1.jar;%APP_HOME%\lib\logback-classic-1.2.11.jar;%APP_HOME%\lib\jackson-datatype-jdk8-2.15.0.jar;%APP_HOME%\lib\jackson-datatype-jsr310-2.15.0.jar;%APP_HOME%\lib\jackson-databind-2.15.0.jar;%APP_HOME%\lib\jackson-core-2.15.0.jar;%APP_HOME%\lib\jackson-annotations-2.15.0.jar;%APP_HOME%\lib\javax.annotation-api-1.3.2.jar;%APP_HOME%\lib\logback-core-1.2.11.jar;%APP_HOME%\lib\netty-handler-proxy-4.1.94.Final.jar;%APP_HOME%\lib\netty-codec-http2-4.1.94.Final.jar;%APP_HOME%\lib\netty-codec-http-4.1.94.Final.jar;%APP_HOME%\lib\netty-handler-4.1.94.Final.jar;%APP_HOME%\lib\netty-codec-socks-4.1.94.Final.jar;%APP_HOME%\lib\netty-codec-4.1.94.Final.jar;%APP_HOME%\lib\netty-transport-native-unix-common-4.1.94.Final.jar;%APP_HOME%\lib\netty-transport-4.1.94.Final.jar;%APP_HOME%\lib\netty-buffer-4.1.94.Final.jar;%APP_HOME%\lib\reactor-core-3.5.0.jar;%APP_HOME%\lib\micronaut-core-reactive-3.10.1.jar;%APP_HOME%\lib\reactive-streams-1.0.4.jar;%APP_HOME%\lib\pi4j-plugin-raspberrypi-2.4.0.jar;%APP_HOME%\lib\pi4j-plugin-pigpio-2.4.0.jar;%APP_HOME%\lib\pi4j-library-pigpio-2.4.0.jar;%APP_HOME%\lib\pi4j-core-2.4.0.jar;%APP_HOME%\lib\micronaut-core-3.10.1.jar;%APP_HOME%\lib\slf4j-api-1.7.36.jar;%APP_HOME%\lib\snakeyaml-2.0.jar;%APP_HOME%\lib\validation-api-2.0.1.Final.jar;%APP_HOME%\lib\jakarta.inject-api-2.0.1.jar;%APP_HOME%\lib\netty-resolver-4.1.94.Final.jar;%APP_HOME%\lib\netty-common-4.1.94.Final.jar;%APP_HOME%\lib\fastdoubleparser-0.8.0.jar


@rem Execute Home_Automation
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %HOME_AUTOMATION_OPTS%  -classpath "%CLASSPATH%" com.example.pi4micronaut.Application %*

:end
@rem End local scope for the variables with windows NT shell
if %ERRORLEVEL% equ 0 goto mainEnd

:fail
rem Set variable HOME_AUTOMATION_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
set EXIT_CODE=%ERRORLEVEL%
if %EXIT_CODE% equ 0 set EXIT_CODE=1
if not ""=="%HOME_AUTOMATION_EXIT_CONSOLE%" exit %EXIT_CODE%
exit /b %EXIT_CODE%

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
