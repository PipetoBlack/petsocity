# 🐾 Petsocity – Microservicios

> Toda la información que necesitas para levantar el proyecto, colaborar en equipo y probar los servicios.

---

## 📑 Índice

- [🔧 Tecnologías y Configuración](#-tecnologías-y-configuración)  
- [🚀 Tips para Git](#-tips-para-git)  
- [💾 Configuración MySQL](#-configuración-mysql)  
- [📬 Pruebas con Postman](#-pruebas-con-postman)  
- [👥 Contribuidores](#-contribuidores)  

---


## 🔧 Tecnologías y Configuración

**Stack**  
- 🛠️ **Build**: Gradle (Groovy)  
- ☕ **Java**: versión 24  
- 🌱 **Spring Boot**: 3.4.5  
- 📦 **Packaging**: JAR  

**Dependencias principales**  
- ✅ Spring Data JPA  
- 🌐 Spring Web  
- 🧩 Lombok  
- 🐬 MySQL Driver  

---
## 🚀 Tips para Git

### Comandos básicos 

**Comandos para subir el trabajo a github:**
1. Guarda tus cambios local
<pre> git add . </pre>
2. Agregale un comentario
<pre> git commit -m "escribe el cambio" </pre>
3. Súbelo a tu propia rama local
<pre> git push </pre>


**Comando para traer los cambios realizados de otra rama**
1. Múevete a la rama destino, ej. main
<pre> git checkout main  </pre>
2. Fusiona la rama desarrollador
<pre> git merge desarrollador </pre>
3. Empuja tus cambios
   se debe de realizar
<pre> git push  </pre>


**Comando para descargar los cambios y actualizar ramas de los demás**
<pre> git pull  </pre>
<pre> git fetch  </pre>


**Crear y cambiar rama**
<pre> git branch NOMBRE_DE_LA_RAMA  </pre>
<pre> git checkout NOMBRE_DE_LA_RAMA </pre>

---------------------------------------------------------------------------------------------------------
## 💾 Configuración MySQL
1. Inicia tu servidor MySQL (localhost):
	- *user: root*
	- *pass: root*
2. Crea la base de datos:
<pre> CREATE DATABASE bdpetsocity; </pre>
3. Asegúrate de que application.properties apunte a:
<pre> spring.datasource.url=jdbc:mysql://localhost:3306/bdpetsocity
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
 </pre>
--------------------------------------------------------------------------------------------------------
## 📬 Pruebas con Postman

- 🔌 Puertos:

<pre> 8087  – Carrito</pre>
<pre> 8088  – Usuarios</pre> 
<pre> 8089  – Inventario/Categoría</pre>

Todas las pruebas se ven reflejadas en el excel. 
 - Link para acceso de postman se da acceso correspondientes
*Todas las peticiones (GET, POST, PUT, DELETE) están documentadas en la colección y reflejadas en el Excel de pruebas.*

--------------------------------------------------------------------------------------------------------
## FELIPE

Durante todo el proceso se hicieron varios cambios dentro de código tanto para optimizar y simplificar el proyecto así borrando clases y mejorando las demás para mejor manejo de ellas. Eliminando código obsoleto y mejorar el desempeño al momento de trabajar con postman y la base de datos.

Creacion archivo bdpetsocity
- Se agrega el script sql

Se añade el puerto de enlace 8087,8088 y 8089 para comenzar a hacer pruebas en Postman
- 8087 para el microservicio de carrito
- 8088 para el microservicio de usuarios
- 8089 para el microservicio de inventario / categoria

*Durante la correccion de errores, nos percatamos que teniamos problemas en el codigo en general, con los nombres, secuencias e instancias.*
- ya que basicamente, al momento de que springboot intentaba correr el repositorio este no encontraba la logica detras.

*Ultimas actualizaciones: Después de una gran batalla con la unificación de ciertas clases con entidad relacional, pudimos dar con éxito el levantamiento el servidor 
para comenzar a hacer pruebas desde postam. BD y backend funcionan correctamente con éxito!!*

Optimización
- Eliminación de clases innecesarias, ya que se mantienen en carritoController y carritoService
- Se elimina Rol
- Se modifica el scrip sql
- se modifica el modelo usuario para una mejor interaccion con el usuario

---------------------------------------------------------------------------------------------------------------------
## VANIA

Creacion de carpetas
- controller
- model
- repository
- service

Creacion de clases
- Se añaden por primera vez las clases

Configuracion de archivo application.propierties
- Se configura para almacenar de manera local
- Creacion de las clases en modelo
- Se corrigen errores en las clases para un mejor flujo de trabajo

- Se agrega en Usuariocontroller una validación para ver al momento cuando se borra desde el postman
- Se realizan mejoras y testeos de categoríaController,inventarioController, también se mejora los endpoint y el delete de categoríaController
- Se migra a carpetas nuevas y mejorando el proyecto así moviendo sus archivos a sus respectivos microservicios
- Se realiza copias de archivos
- Se realizaron Pruebas en POSTAMAN dando éxito en todas y cada una de las pruebas

----------------------------------------------------------------------------------------

## ALAN

Solución de conflicto en código
Creación de rama + Creación de métodos en carrito.
También se realizo modificaciones en detalle carrito, se modifico nombres mal escritos y se realizan querys para este, también se optimiza código en carritoRepository
Se completa el informe.

---------------------------------------------------------------------------------------

## ALEXIS

Creación de Rama + creación de métodos y cambios en sección de DETALLECARRITO, y corrección de errores en ellos. También se comento en ellas para saber su uso
Mejora en Detalle Carrito ya que unos de los métodos estaba mal escrito.
Se completa la documentación para un mejor entendimiento de lo que se hizo durante el proceso del código 

-------------------------------------------------------------------------------


