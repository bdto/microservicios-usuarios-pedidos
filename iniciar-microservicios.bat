@echo off
title Microservicios - Usuarios y Pedidos

echo ==========================================
echo    INICIANDO MICROservicios
echo ==========================================
echo.
echo Este script requiere Maven instalado como "mvn".
echo.

start "SERVICIO-USUARIOS 8081" cmd /k "mvn -pl servicio-usuarios spring-boot:run"
timeout /t 5 /nobreak >nul
start "SERVICIO-PEDIDOS 8082" cmd /k "mvn -pl servicio-pedidos spring-boot:run"

echo.
echo Se abrieron dos ventanas:
echo   Servicio Usuarios -> http://localhost:8081
echo   Servicio Pedidos  -> http://localhost:8082
echo.
pause
