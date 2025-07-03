
# 🐾 PetSocity - Plataforma distribuida para gestión de tiendas de mascotas (arquitectura de microservicios)

![Java](https://img.shields.io/badge/Java-24-blue.svg)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.4.5-brightgreen.svg)
<<<<<<< HEAD
![Gradle Groovy](https://img.shields.io/badge/Gradle-Groovy-FF7F00?logo=gradle)
=======
![Gradle](https://img.shields.io/badge/Build-Gradle-Groovy-orange)
>>>>>>> 8d1ee837f8df58d89d54c0f793161877a3720bbb

**PetSocity** es una aplicación distribuida desarrollada con arquitectura de microservicios orientada a tiendas de mascotas. El sistema se compone de tres servicios principales:

- 🧑‍💼 `usUsuario`: Gestión de usuarios, autenticación y perfiles (microservicio más completo).
- 🛒 `usCarrito`: Manejo de carritos de compra.
- 📦 `usInventario`: Control de productos disponibles.

> Proyecto desarrollado en **Visual Studio Code** sobre **Windows 11**, utilizando **Java 24**, **Spring Boot 3.4.5**, y **Gradle (Groovy DSL)**. Empaquetado como un **JAR ejecutable**.

---

## 🛠️ Tecnologías utilizadas

- Java 24 (2025-03-18)
- Spring Boot 3.4.5 (Web, Data JPA, HATEOAS)
- Gradle (Groovy DSL)
- MySQL
- Swagger (springdoc-openapi)
- JUnit 5 + @SpringBootTest
- Faker (`net.datafaker.Faker`) para generación automática de datos
- Visual Studio Code (IDE)
- Sistema operativo: Windows 11

---

## 🐶 Funcionalidades principales

- Registro, edición y eliminación de usuarios
- Autenticación básica con perfil de usuario
- Gestión de carritos por sesión
- Control de inventario y stock de productos
- Respuestas enriquecidas con **Spring HATEOAS**
- Documentación automática con **Swagger UI**

---

## ⚙️ Perfiles de configuración

El proyecto utiliza múltiples archivos de propiedades separados por entorno:

- `application.properties`: base principal del proyecto
- `application-dev.properties`: entorno de desarrollo con Swagger y pruebas reales de endpoints
- `application-test.properties`: entorno de pruebas automatizadas con SpringBootTest
- `application-prod.properties`: entorno para despliegue en producción

El perfil activo se define en `application.properties`:
```properties
spring.profiles.active=dev
```

Puedes cambiarlo a `test` o `prod` según el entorno deseado.

---

## 📁 Estructura del repositorio

```plaintext
petsocity/
├── usUsuario/            → Microservicio de usuarios (principal)
├── usCarrito/            → Microservicio de carritos
├── usInventario/         → Microservicio de inventario
├── bdpetsocity.sql       → Script de base de datos principal
├── bdpetsocity_test.sql  → Script de base de datos para testing
```

---

## 📌 Requisitos previos

- Java JDK 24
- Gradle
- MySQL Server
- Visual Studio Code con extensiones Java
- Postman o navegador con Swagger UI

---

## ▶️ Ejecución del microservicio usUsuario

1. **Clonar el repositorio**

```bash
git clone https://github.com/PipetoBlack/petsocity.git
cd petsocity/usUsuario
```

2. **Crear las bases de datos en MySQL**

```sql
CREATE DATABASE bdpetsocity;
CREATE DATABASE bdpetsocity_test;
```

3. **Verificar el perfil activo y configuración en `application.properties`**

Por defecto:

```properties
spring.profiles.active=dev
```

En `application-dev.properties` configura:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bdpetsocity
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_PASSWORD
```

4. **Ejecutar el servicio**

```bash
./gradlew bootRun
```

---

<<<<<<< HEAD
## 📚 Swagger - Documentación interactiva de la API

La documentación de la API se genera automáticamente gracias a **springdoc-openapi**, y está habilitada en el entorno `dev`.

### 🛠️ Requisitos

- El microservicio `usUsuario` debe estar ejecutándose (`PetsocityApplication.java`)
- Accede a través del navegador local al puerto **8088**

### 🔍 Interfaz Swagger

Visualiza, testea y explora los endpoints disponibles:

[👉 Swagger UI - PetSocity](http://localhost:8088/doc/swagger-ui.html)

### 🟩 Funcionalidades desde Swagger

- Ejecución de operaciones: `GET`, `POST`, `PUT`, `DELETE`
- Visualización de modelos como `Usuario`, `Carrito`, `Producto`
- Pruebas interactivas sin necesidad de Postman
- Exploración de respuestas enriquecidas con **HATEOAS**

💡 _Ideal para validar funcionalidades durante el desarrollo sin levantar clientes externos._
=======
## 📚 Swagger - Documentación API

El entorno `dev` activa automáticamente la documentación Swagger.

📍 Accede a la documentación: [Swagger UI](http://localhost:8088/doc/swagger-ui.html)
>>>>>>> 8d1ee837f8df58d89d54c0f793161877a3720bbb

---

## 🔗 HATEOAS

Las respuestas REST incluyen enlaces utilizando Spring HATEOAS:

```json
{
  "_embedded": {
    "usuarioList": [
      {
        "id": 1,
        "primerNombre": "Calista",
        "segundoNombre": "Jerald",
        "primerApellido": "Bartell",
        "segundoApellido": "Renner",
        "email": "jefferey.goyette@yahoo.com",
        "contrasenia": "w3w732i5s47a",
        "direccion": "516 Cassandra Via, Gleichnerchester, WV 99575",
        "fechaCreacion": "2025-07-03T02:58:46.035358",
        "_links": {
          "self": { "href": "http://localhost:8088/api/v1/usuarios/1" },
          "usuarios": { "href": "http://localhost:8088/api/v1/usuarios" },
          "actualizar": { "href": "http://localhost:8088/api/v1/usuarios/1" },
          "eliminar": { "href": "http://localhost:8088/api/v1/usuarios/1" }
        }
      }
    ]
  },
  "_links": {
    "self": { "href": "http://localhost:8088/api/v1/usuarios" }
  }
}
```

Esto se logra con `EntityModel` y un ensamblador personalizado (`UsuarioModelAssembler.java`).

---

## 🧪 Pruebas automatizadas

- `@SpringBootTest` para pruebas de integración
- `TestRestTemplate` para simular peticiones
- `Faker` para generar usuarios ficticios con datos realistas
- `application-test.properties` configura el entorno de prueba con una base de datos separada (`bdpetsocity_test`)

📦 Ejecuta las pruebas:

```bash
./gradlew test
```

📌 Verifica que el perfil `test` esté activo durante las pruebas (`@ActiveProfiles("test")`).

---

## 📦 Otros microservicios

Sigue pasos similares para `usCarrito` y `usInventario`.

Asegúrate de modificar el puerto en su respectivo archivo `application.properties` para evitar conflictos (`server.port=xxxx`).

---

## 🤝 Contribuciones

Este proyecto se desarrolló como parte de una evaluación académica.  
Si deseas aportar mejoras o extender su funcionalidad, ¡bienvenido/a!

---

## 📝 Licencia

Proyecto académico desarrollado por **Felipe Navarro**, **Vania Vargas**, **Alan Astudillo**, **Alexis Figueroa** como parte de evaluación FullStack.
