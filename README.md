# Minimarket Backend - Backend II Sumativa S6

Proyecto backend desarrollado con **Java** y **Spring Boot** para la gestión de un minimarket.  
Incluye funcionalidades relacionadas con:

- Productos
- Categorías
- Inventario
- Ventas
- Detalle de ventas
- Usuarios
- Seguridad y autenticación
- Pruebas unitarias en la capa de controladores y servicios
- Integración con Jenkins para automatización

---

## Descripción del proyecto

Este sistema corresponde a un backend REST orientado a la administración de un minimarket.  
Su objetivo es exponer servicios para la gestión de entidades principales del negocio, manteniendo una estructura por capas:

- **Controller**
- **Service**
- **Repository**
- **Entity**
- **Security**
- **Test**

El proyecto está implementado en **Spring Boot** y organiza la lógica mediante APIs REST para consumo desde aplicaciones frontend, herramientas de pruebas o integraciones externas.

---

## Tecnologías utilizadas

- **Java**
- **Spring Boot**
- **Spring Web**
- **Spring Security**
- **Spring Data JPA**
- **JUnit 5**
- **Mockito**
- **Maven**
- **Jenkins**

---

## Características principales

- API REST para administración de minimarket
- CRUD para las entidades principales
- Autenticación y configuración de seguridad
- Endpoint público de prueba
- Validaciones en servicios
- Pruebas unitarias para capas clave
- Estructura preparada para integración continua con Jenkins

---

## Estructura general del proyecto

```bash
minimarket_S6/
├── src/
│   ├── main/
│   │   ├── java/com/minimarket/
│   │   │   ├── controller/
│   │   │   ├── entity/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   ├── service/impl/
│   │   │   └── security/
│   │   └── resources/
│   └── test/
│       └── java/com/minimarket/
├── pom.xml
└── README.md
```

---

## Módulos y componentes

### 1. Controladores REST

El proyecto expone endpoints para administrar recursos del sistema.

#### Ejemplos de controladores
- `ProductoController`
- `CategoriaController`
- `InventarioController`
- `VentaController`
- `DetalleVentaController`
- `UsuarioController`
- `HolaMundoController`

#### Endpoint público
- `GET /public/hola`  
  Responde con: `¡Hola Mundo!`

---

### 2. Seguridad

El proyecto incluye una configuración de seguridad basada en Spring Security.

#### Comportamiento principal
- CSRF deshabilitado
- Acceso público a rutas bajo `/public/**`
- El resto de rutas requiere autenticación
- Login mediante formulario
- Logout configurado en `/logout`

#### Ejemplo relevante
- `SecurityConfig`
- `CustomUserDetailsService`
- `JwtUtil` como utilidad preparada para futuras mejoras de autenticación

---

### 3. Servicios

La lógica de negocio se organiza en interfaces y sus implementaciones.

#### Servicios detectados
- `ProductoService`
- `CategoriaService`
- `InventarioService`
- `VentaService`
- `DetalleVentaService`
- `UsuarioService`

#### Funciones destacadas
- Registro de ventas
- Cálculo de total de ventas
- Validación de stock
- Validación de datos obligatorios del usuario
- Verificación de roles permitidos
- Gestión de inventario

---

### 4. Pruebas unitarias

El proyecto incluye pruebas unitarias con foco especial en la capa de servicio y validaciones de dominio.

#### Herramientas
- JUnit 5
- Mockito

#### Ejemplos de pruebas encontradas
- Verificación de carga del contexto Spring Boot
- Pruebas para `InventarioServiceImpl`
- Validación de:
  - movimientos de inventario
  - persistencia correcta
  - eliminación
  - campos obligatorios
  - relación entre producto e inventario

---

## Endpoints principales

> Nota: los endpoints exactos pueden variar según la implementación final de cada controlador, pero la estructura general del proyecto es la siguiente.

### Productos
- `GET /api/productos`
- `GET /api/productos/{id}`
- `POST /api/productos`
- `PUT /api/productos/{id}`
- `DELETE /api/productos/{id}`

### Categorías
- `GET /api/categorias`
- `GET /api/categorias/{id}`
- `POST /api/categorias`
- `PUT /api/categorias/{id}`
- `DELETE /api/categorias/{id}`

### Inventario
- `GET /api/inventario`
- `GET /api/inventario/{id}`
- `POST /api/inventario`
- `PUT /api/inventario/{id}`
- `DELETE /api/inventario/{id}`

### Ventas
- `GET /api/ventas`
- `GET /api/ventas/{id}`
- `POST /api/ventas`

### Detalle de ventas
- `GET /api/detalle-ventas`
- `GET /api/detalle-ventas/{id}`
- `POST /api/detalle-ventas`
- `PUT /api/detalle-ventas/{id}`
- `DELETE /api/detalle-ventas/{id}`

### Usuarios
- `GET /api/usuarios`
- `GET /api/usuarios/{id}`
- `POST /api/usuarios`
- `PUT /api/usuarios/{id}`
- `DELETE /api/usuarios/{id}`

### Público
- `GET /public/hola`

---

## Requisitos previos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

- **Java 17** o la versión compatible con el proyecto
- **Maven**
- **Git**
- Un IDE como:
  - IntelliJ IDEA
  - Eclipse
  - VS Code

Si el proyecto usa base de datos, también necesitarás el motor correspondiente configurado en `application.properties` o `application.yml`.

---

## Instalación y ejecución local

### 1. Clonar el repositorio

```bash
git clone https://github.com/Maamartinezr/Backend-II_Sumativa_S6-.git
```

### 2. Entrar al proyecto

```bash
cd Backend-II_Sumativa_S6-
```

### 3. Compilar el proyecto

```bash
mvn clean install
```

### 4. Ejecutar la aplicación

```bash
mvn spring-boot:run
```

O ejecutar directamente la clase principal:

```java
com.minimarket.MinimarketApplication
```

---

## Ejecución de pruebas

Para ejecutar las pruebas unitarias:

```bash
.\mvnw.cmd verify
```

```bash
.\mvnw.cmd clean test
```

---

## Configuración de seguridad

El proyecto dispone de una configuración de seguridad que:

- Permite acceso libre a rutas públicas
- Protege el resto de la API
- Usa autenticación mediante Spring Security

Si deseas ampliar esta funcionalidad, puedes implementar:

- JWT completo
- Roles de usuario más detallados
- Autorización basada en perfiles
- Protección adicional para endpoints críticos

---

## Uso de Jenkins

El repositorio menciona uso de **Jenkins**, por lo que puede integrarse en un flujo de integración continua para:

- Compilar automáticamente el proyecto
- Ejecutar pruebas
- Validar calidad antes de desplegar

### Sugerencia de pipeline básico
Un pipeline en Jenkins podría incluir pasos como:

1. Checkout del código
2. `mvn clean install`
3. `mvn test`
4. Publicación de resultados

---

## Posibles mejoras futuras

- Implementación completa de JWT
- Documentación con Swagger/OpenAPI
- Manejo centralizado de excepciones
- Validaciones más robustas en DTOs
- Separación entre entidades y DTOs
- Cobertura de pruebas más amplia
- Pipeline Jenkins con despliegue automático
- Documentación de la base de datos
- Ejemplos de request/response para cada endpoint

---

## Autor

Proyecto desarrollado por **Maamartinezr**.

---

## Licencia

Este proyecto no incluye una licencia definida actualmente.  
Si deseas compartirlo o reutilizarlo públicamente, se recomienda agregar una licencia como:

- MIT
- Apache 2.0
- GPL-3.0

---

## Contacto

Si deseas usar este proyecto como base para futuras mejoras, puedes adaptarlo a tus necesidades agregando:

- frontend
- autenticación avanzada
- base de datos productiva
- despliegue en servidor o nube

---

## Nota final

Este proyecto está orientado a un entorno académico y de aprendizaje, pero su estructura permite escalarlo hacia una solución más completa de administración de minimarket.
