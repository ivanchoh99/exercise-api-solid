# Ejercicio prueba conceptos
En este ejercicio se ha desarrollado diferentes conceptos y ejercicios, como el consumo de una API, gestión con una base de datos mediante un ORM y pruebas unitarias de todo lo anterior.
## Requisitos para ejecutar

### Con Docker

#### Requisitos:

- Tener instalador Docker en tu equipo

#### Pasos ejecución

1. Abrir una terminal en la carpeta raíz del proyecto.
2. Utilizar el comando `build -t <nombre_imagen> .`, con este comando se construirá la imagen necesaria para ejecutar la api.
3. Cuando se termine de ejecutar el comando de build, puedes ejecutar `docker run --name <nombre_contenedor> --rm`, con este comando se ejecuta la imagen y
   cuando se detenga la misma esta se eliminara gracias al `--rm`.

#### Interrupción del api

1. en la misma terminal donde ejecutamos nuestros comandos previos, ejecutamos `docker ps` con esto sabemos los contenedores que se están ejecutando
2. Ya sabiendo el ID o el nombre del contenedor podemos `docker stop <container_id>` o `docker stop <container_name>` para detener el contenedor.

### Sin Docker

#### Requisitos

- Tener instalado en tu equipo la JDK 17

#### Pasos ejecución

1. Abrir una terminal en la carpeta raíz del proyecto.
2. Mediante el wrapper de gradle podemos ejecutar nuestro proyecto mediante el comando `./gradlew :bootRun`

#### Interrupción del api

1. Nos ubicamos en la consola donde se está ejecutando el api y usamos el comando `Ctrl + c` para interrumpir su ejecución

## Documentación

Mediante la documentación puede conocer los endpoint de los cuales dispone la aplicación, de esta manera podremos implementar la api correctamente.

### Interfaz grafica

Para acceder a la documentación gráfica de Swagger lo hacemos mediante la url `localhost:8080/ui-doc`.

### API

Para acceder a la documentación de la API en Json lo hacemos mediante la url `localhost:8080/api-doc`.