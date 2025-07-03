
# 🐾 PetSocity - Sistema de Microservicios para Gestión de Mascotas

**PetSocity** es una aplicación distribuida bajo arquitectura de microservicios orientada a tiendas de mascotas. El sistema se compone de tres servicios principales:

- 🧑‍💼 `usUsuario`: Gestión de usuarios, autenticación y roles (microservicio más completo).
- 🛒 `usCarrito`: Manejo de carritos de compra.
- 📦 `usInventario`: Control de productos disponibles.

> Proyecto desarrollado en entorno **Visual Studio Code** sobre **Windows 11**, utilizando **Java 24**, **Spring Boot 3.4.5**, y el sistema de construcción **Gradle (Groovy DSL)**. Se empaqueta como un **JAR ejecutable**.

---

## 🛠️ Tecnologías y herramientas utilizadas

- **Java 24 (2025-03-18)**
- **Spring Boot 3.4.5**
  - Spring Web
  - Spring Security
  - Spring Data JPA
  - Spring HATEOAS
- **Gradle** (con Groovy DSL)
- **MySQL** (archivo `bdpetsocity.sql`)
- **Swagger OpenAPI (springdoc-openapi)** para documentación interactiva
- **Faker (net.datafaker.Faker)** para generación de datos falsos
- **JUnit 5 + SpringBootTest** para pruebas
- **Visual Studio Code** como IDE principal
- **Sistema operativo**: Windows 11

---

## 📁 Estructura del repositorio

```plaintext
petsocity/
│
├── usUsuario/       → Microservicio de usuarios (principal)
├── usCarrito/       → Microservicio de carritos
├── usInventario/    → Microservicio de inventario
├── bdpetsocity.sql  → Script SQL para base de datos
```

---

## 📌 Requisitos previos

- [Java JDK 24](https://jdk.java.net/)
- [Gradle](https://gradle.org/install/)
- [MySQL Server](https://dev.mysql.com/downloads/mysql/)
- [Visual Studio Code](https://code.visualstudio.com/) con extensiones Java
- (Opcional) Postman o navegador para probar endpoints

---

## ▶️ Instrucciones de ejecución (usUsuario)

### 1. Clonar el repositorio

```bash
git clone https://github.com/PipetoBlack/petsocity.git
cd petsocity/usUsuario
```

### 2. Crear la base de datos

Importa el archivo `bdpetsocity.sql` en tu servidor MySQL:

```sql
CREATE DATABASE petsocity;
-- Luego importar el contenido desde el archivo .sql
```

### 3. Configurar la conexión en `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/petsocity
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_CONTRASEÑA
spring.jpa.hibernate.ddl-auto=update
```

### 4. Ejecutar el microservicio

Desde terminal:

```bash
./gradlew bootRun
```

Desde VS Code:
- Abre la carpeta `usUsuario`
- Ejecuta la clase `PetsocityApplication.java`

---

## 📚 Documentación interactiva con Swagger

Este microservicio incluye documentación de su API mediante **Swagger**:

- Endpoint disponible en:
  ```
  http://localhost:8088/doc/swagger-ui/index.html
  ```
- Se genera automáticamente usando la dependencia `springdoc-openapi`.

> Swagger permite explorar y probar los endpoints directamente desde el navegador.

---

## 🔗 Soporte HATEOAS

El servicio `usUsuario` implementa **Spring HATEOAS**, permitiendo enriquecer las respuestas con enlaces relacionados.

Ejemplo de respuesta con HATEOAS:

```json
{
  "id": 1,
  "nombre": "Felipe",
  "correo": "felipe@example.com",
  "_links": {
    "self": {
      "href": "http://localhost:8088/api/usuarios/1"
    },
    "all": {
      "href": "http://localhost:8088/api/usuarios"
    }
  }
}
```

Esto se logra gracias al ensamblador `UsuarioModelAssembler.java`, que transforma entidades en `EntityModel` enriquecido.

---

## 🧪 Pruebas

Puedes probar la API usando:

- **Swagger UI** para visualizar los endpoints.
- **Postman** para enviar peticiones manuales (CRUD de usuarios).

El microservicio implementa pruebas automatizadas con:
- **@SpringBootTest** para pruebas de integración
- **net.datafaker.Faker** para crear datos falsos y simular usuarios rápidamente
---

## 📦 Otros servicios

Sigue pasos similares para ejecutar `usCarrito` y `usInventario`.  
Recuerda configurar diferentes puertos si los corres simultáneamente (con `server.port=xxxx`).
(En desarrollo...)
---

## 📝 Licencia

Proyecto académico desarrollado por **Felipe Navarro**, Alan Astudillo, Alexis Figueroa, Vania Vargas, como parte de evaluación de desarrollo FullStack.

---
