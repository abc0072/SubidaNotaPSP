# Sistema de Gestión de Procesos para una Clínica Veterinaria
## Descripción

Este proyecto consiste en el desarrollo de un sistema que simula el funcionamiento de una clínica veterinaria. La aplicación permite gestionar pacientes, atender consultas de forma concurrente mediante hilos, enviar notificaciones en tiempo real mediante sockets y registrar la información en una base de datos SQLite.

El sistema está diseñado para aplicar conceptos de Programación de Servicios y Procesos, como concurrencia, sincronización, comunicación cliente-servidor y persistencia de datos.

La clínica dispone de dos consultas que trabajan simultáneamente, tres veterinarios y un sistema de prioridad para urgencias.

## Tecnologías utilizadas

- Java
- Programación concurrente con Thread
- Sockets (ServerSocket y Socket)
- SQLite
- JDBC
- Arquitectura por capas (modelos, servicios, db, servidor)

## Funcionamiento del sistema

El sistema simula el funcionamiento de una clínica mediante los siguientes pasos:

1. Se registran pacientes en el sistema.
2. Los pacientes se añaden a diferentes colas dependiendo de su tipo:
  - consultas normales
  - urgencias
3. Las consultas funcionan como hilos independientes que atienden pacientes.
4.  selecciona automáticamente el veterinario con menor carga de trabajo.
5. Se registra en la base de datos el inicio y fin de cada atención.
6. El servidor envía notificaciones en tiempo real a los clientes conectados.

Esto permite simular un entorno concurrente donde varias consultas trabajan al mismo tiempo.

## Estructura del proyecto

El proyecto está dividido en varios paquetes para organizar el código:

**modelos**
- Paciente
- Veterinario
- TipoDeCaso

**servicios**
- Clinica (gestión de colas y veterinarios)
- Consulta (hilos que atienden pacientes)

**servidor**
- Servidor (gestiona conexiones y notificaciones)
- Cliente (recibe notificaciones)

**db**
- ConexionBD (conexión a SQLite)
- CrearTablas (creación de tablas)
- PacienteDAO
- AtencionDAO

**Main**
- Punto de entrada del programa.

## IMPORTANTE — Cómo ejecutar el programa

Para que el sistema funcione correctamente es necesario ejecutar las clases en el siguiente orden:

1. Ejecutar el servidor (ubicada en la carpeta servidor -> Servidor.java)
2. Ejecutar uno o varios clientes (ubicada en la carpeta servidor -> Cliente.java)
3. Ejecutar la clase Main

## Base de datos

El sistema utiliza SQLite para almacenar la información.

Se crean dos tablas principales:

**pacientes**
<br>
Almacena los pacientes registrados en el sistema.

Campos principales:

- id
- nombre
- tipo
- prioridad

**atenciones** 
<br>
Registra las atenciones realizadas por los veterinarios. <br>

Campos principales:

- id
- paciente_id
- veterinario
- consulta
- inicio
- fin

Esto permite mantener un historial completo de la actividad de la clínica.

## Posibles mejoras

El proyecto podría ampliarse en futuras versiones con:

- Interfaz gráfica para gestionar pacientes
- Estadísticas de atención por veterinario
- Historial médico de pacientes
- Sistema de autenticación de usuarios
