Todo lo que deben saber para crear el proyecto y trabajar en conjunto
---------------------------------------------------------------------

Se realiza el proyecto con la siguiente configuracion de Springboot:
- Se utiliza Gradle - Groovy
- Lenguaje JAVA --version 24
- Version SpringBoot 3.4.5
- Packaing: JAR

Comandos para subir el trabajo a github:
git add .
git commit -m "escribe el cambio"
git push

comando para traer los cambios realizados de otra rama
1. Estar posicionado en la rama que quieres traer otro cambio 
    ej: posicionarse en Main para traer cambio de rama desarrollador
2. Escribir el siguiente comando en la terminal de Main
    git merge *desarrollador*
3. Se actualizan los cambios en el Main pero solo en tu entorno, para subirlo y que los demas lo vean
   se debe de realizar un push
    git push

Comando para descargar los cambios 
git pull

Dependencias al momento de generar el springboot:
- Spring Data JPA
- Spring Web
- Lombok
- MySQL Driver


---------------------------------------------------------------------------------------------------------
## CONFIGURACION MYSQL
- Se debe iniciar MySQL connections con el localhost por defecto
	*user root
	*pass root
- Se crea DATABASE "bdpetsocity" en donde se almacenaran las tablas y sus datos.
- Esta bd ya cuenta con todo los privilegios necesarios para hacer consultas y utilizar metodos CRUD
--------------------------------------------------------------------------------------------------------
## PRUEBAS CON POSTMAN

- Se añade el puerto de enlace 8088,8089 y 8087 para comenzar a hacer pruebas en Postman
asi con sus respectivas pruebas

Todas las pruebas se ven reflejadas en el excel. 
Link para acceso de postman 

https://app.getpostman.com/join-team?invite_code=daed2daf6a234aee8ef359eac65d2e4f5436f344ef05b23c5d0452139294f4e4&target_code=b24842dce38883c27e94da6a3c958560

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
- Se realizaron Pruebas en POSTAMN dando éxito en todas y cada una de las pruebas

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


