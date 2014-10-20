@echo off
setlocal

set GENERATED=%~dp0src\generated

java ^
  -jar "%~dp0dsl-clc.jar" latest ^
  --project-props-path="%GENERATED%\resources\dsl-project.props" ^
  --dsl-path="%~dp0dsl" ^
  --with-active-record ^
  --output-path="%GENERATED%" ^
  %*
