@echo off
REM HOLauncher is not used anymore:
REM javaw -classpath . HOLauncher
@start javaw -Xmx512m -jar ho.jar
@if exist extension.bat call extension.bat
@exit


