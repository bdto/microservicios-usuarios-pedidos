Write-Host "=========================================="
Write-Host " INICIANDO MICROSERVICIOS"
Write-Host "=========================================="
Write-Host ""
Write-Host "Este script requiere Maven disponible como 'mvn'."
Write-Host ""

Start-Process powershell -ArgumentList '-NoExit', '-Command', 'mvn -pl servicio-usuarios spring-boot:run' 
Start-Sleep -Seconds 5
Start-Process powershell -ArgumentList '-NoExit', '-Command', 'mvn -pl servicio-pedidos spring-boot:run'

Write-Host ""
Write-Host "Servicio Usuarios -> http://localhost:8081"
Write-Host "Servicio Pedidos  -> http://localhost:8082"
