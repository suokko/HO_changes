@echo off
javaw -classpath . HOLauncher
@start javaw -Xmx512m -jar hocoded.jar
@if exist extension.bat call extension.bat
@exit


