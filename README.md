# Microservicios - Usuarios y Pedidos

Proyecto académico basado en el proyecto Spring Boot entregado como punto de partida. Fue reorganizado para demostrar una arquitectura sencilla orientada a microservicios.

## Arquitectura

- **servicio-usuarios**: administra usuarios y expone una API REST en el puerto **8081**.
- **servicio-pedidos**: administra pedidos y expone una API REST en el puerto **8082**.
- **servicio-pedidos** consume `GET http://localhost:8081/usuarios/{id}` antes de crear un pedido. Así se evidencia la comunicación entre microservicios mediante REST.
- Cada servicio tiene su propia base de datos H2 en memoria, mostrando el principio de separación de datos.

```text
                    ┌───────────────────────┐
                    │   Cliente / Postman    │
                    └───────────┬───────────┘
                                │
                         HTTP / REST
                                │
                 ┌──────────────▼──────────────┐
                 │    servicio-pedidos :8082   │
                 │  API de pedidos + H2 local  │
                 └──────────────┬──────────────┘
                                │
                      HTTP GET /usuarios/{id}
                                │
                 ┌──────────────▼──────────────┐
                 │    servicio-usuarios :8081  │
                 │  API de usuarios + H2 local  │
                 └──────────────────────────────┘
```

## Requisitos

- JDK 17 o superior.
- Maven 3.9+ instalado y disponible como `mvn`.
- Visual Studio Code con Extension Pack for Java recomendado.

> El proyecto fue configurado con Java 17 como versión objetivo. También puede ejecutarse con JDK más reciente siempre que Maven/VS Code estén configurados correctamente.

## Ejecutar desde Visual Studio Code

1. Descomprime el ZIP.
2. Abre la carpeta `microservicios-usuarios-pedidos` en Visual Studio Code.
3. Espera a que Java/Maven cargue los dos módulos.
4. Ejecuta primero `ServicioUsuariosApplication`.
5. Ejecuta después `ServicioPedidosApplication`.
6. Comprueba que cada servicio esté activo en su puerto.

También puedes usar la configuración de ejecución incluida en `.vscode/launch.json`, llamada **Ejecutar microservicios**.

## Ejecutar con Maven

Desde la carpeta raíz:

```bash
mvn clean install
```

Servicio de usuarios:

```bash
mvn -pl servicio-usuarios spring-boot:run
```

Servicio de pedidos:

```bash
mvn -pl servicio-pedidos spring-boot:run
```

## Pruebas rápidas

### 1. Consultar usuarios

```http
GET http://localhost:8081/usuarios
```

### 2. Consultar un usuario

```http
GET http://localhost:8081/usuarios/1
```

### 3. Crear un usuario

```http
POST http://localhost:8081/usuarios
Content-Type: application/json

{
  "nombre": "Carlos López",
  "email": "carlos@correo.com"
}
```

### 4. Consultar pedidos

```http
GET http://localhost:8082/pedidos
```

### 5. Crear un pedido y comprobar la comunicación entre servicios

```http
POST http://localhost:8082/pedidos
Content-Type: application/json

{
  "usuarioId": 1,
  "producto": "Teclado mecánico",
  "cantidad": 2
}
```

El servicio de pedidos consulta internamente `http://localhost:8081/usuarios/1`. Si el usuario existe, crea el pedido. Si no existe, devuelve **404** y no crea el pedido.

### 6. Consultar un pedido

```http
GET http://localhost:8082/pedidos/1
```

## Datos iniciales

Al arrancar `servicio-usuarios`, se crean automáticamente dos usuarios de demostración:

- ID 1: Ana Torres - ana@correo.com
- ID 2: Luis Gómez - luis@correo.com

La base de datos es H2 en memoria; los datos se reinician al detener el servicio.

## Endpoints

### Servicio de Usuarios — 8081

| Método | Endpoint | Función |
|---|---|---|
| GET | `/usuarios` | Lista usuarios |
| GET | `/usuarios/{id}` | Consulta un usuario |
| POST | `/usuarios` | Crea un usuario |
| DELETE | `/usuarios/{id}` | Elimina un usuario |

### Servicio de Pedidos — 8082

| Método | Endpoint | Función |
|---|---|---|
| GET | `/pedidos` | Lista pedidos |
| GET | `/pedidos/{id}` | Consulta un pedido |
| POST | `/pedidos` | Crea un pedido validando el usuario en el otro microservicio |
| DELETE | `/pedidos/{id}` | Elimina un pedido |

## Evidencia para la actividad

Para el video de sustentación se recomienda mostrar:

1. Las dos aplicaciones iniciando en puertos diferentes.
2. La estructura de carpetas de ambos microservicios.
3. `GET /usuarios` para demostrar el primer servicio.
4. `POST /pedidos` para demostrar el segundo.
5. Explicar que `servicio-pedidos` llama a `servicio-usuarios` por HTTP/REST.
6. Mostrar el diagrama de arquitectura del README.
7. Probar un `usuarioId` inexistente para mostrar cómo el segundo servicio depende de la respuesta del primero.

## Interfaz visual

El proyecto incluye una interfaz web sencilla para hacer la sustentación más visual:

- **Usuarios:** `http://localhost:8081/`
- **Pedidos:** `http://localhost:8082/`

Desde estas pantallas puedes crear registros, actualizar las listas y evidenciar la comunicación REST entre ambos microservicios. El diseño usa HTML, CSS y JavaScript sin dependencias externas.
