# Arquitectura visual — Microservicios Usuarios y Pedidos

```text
                         NAVEGADOR / CLIENTE
                                  │
             ┌────────────────────┴────────────────────┐
             │                                         │
             ▼                                         ▼
   ┌───────────────────────┐                ┌───────────────────────┐
   │ SERVICIO USUARIOS     │                │ SERVICIO PEDIDOS      │
   │ Spring Boot :8081     │◄──── REST ────│ Spring Boot :8082     │
   │                       │                │                       │
   │ UI + Controller       │                │ UI + Controller       │
   │ Service + Repository  │                │ Service + REST Client │
   │ JPA + H2              │                │ JPA + H2              │
   └──────────┬────────────┘                └──────────┬────────────┘
              │                                        │
              ▼                                        ▼
        H2 usuariosdb                            H2 pedidosdb
```

La comunicación clave para la demostración es **Pedidos → Usuarios**, mediante una llamada HTTP `GET /usuarios/{id}`.
