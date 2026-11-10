# Sistema de Administración de Hotel - Trabajo Práctico Final

Proyecto final desarrollado para la materia **Programación II** (UTN). Consiste en un sistema de gestión integral para hoteles desarrollado en **Java**, utilizando **IntelliJ IDEA** como entorno de desarrollo y **Git/GitHub** para el versionado de código y trabajo colaborativo.

##  Integrantes del Grupo
* Joaquin Izaguirre
* Pedro Morgade

---

## 🚀 Características y Funcionalidades del Sistema

El sistema permite gestionar de manera eficiente la operativa diaria de un hotel:
* **Gestión de Habitaciones:** Control de habitaciones disponibles, ocupadas, reservadas o en mantenimiento/limpieza.
* **Gestión de Pasajeros:** Registro con datos personales (nombre, DNI, origen, domicilio) e historial de estadías (habitaciones ocupadas).
* **Reservas y Estadías:** Creación de reservas por períodos específicos, validación de disponibilidad y control de solapamientos.
* **Check-in y Check-out:** Registro de ingreso y salida de pasajeros, vinculando el inicio y fin de la ocupación.
* **Roles de Usuario:**
    * *Administrador:* Funciones administrativas, backups, gestión de usuarios y permisos.
    * *Conserje / Recepcionista:* Atención a pasajeros, check-ins, check-outs y reservas.

---

## 🛠️ Requisitos Técnicos e Implementación (POO)

El proyecto cumple estrictamente con los requerimientos académicos solicitados por la cátedra:

1. **Diagrama UML:** Estructura del sistema diseñada previamente (disponible en la carpeta `/docs` o en la wiki del repositorio).
2. **4 Pilares de la POO:** Aplicación de **Herencia**, **Polimorfismo**, **Abstracción** y **Encapsulamiento** en todo el modelo de clases.
3. **Estructura de Clases:** Más de 6 clases principales de dominio (sin contar Main, interfaces ni excepciones).
4. **Estructuras de Datos:** Uso intensivo de **Colecciones** (`ArrayList`) y **Mapas** (`HashMap`) para la administración rápida en memoria.
5. **Componentes Avanzados:**
    * Al menos 1 **Clase Abstracta** (ej. `Persona`).
    * Al menos 1 **Interfaz** (ej. `IPersistible` / `IGestionable`).
    * Al menos 1 **Enumeración** (`Enum` para estados de habitaciones y tipos).
6. **Manejo de Errores:** Excepciones personalizadas desarrolladas para controlar errores de negocio (ej. `HabitacionNoDisponibleException`, `PasajeroNoEncontradoException`).
7. **Clases Genéricas:** Implementación de una clase genérica (ej. `Repositorio<T>` o gestor de archivos) para reutilización de código.
8. **Persistencia de Datos:** Almacenamiento y recuperación de información mediante archivos (texto/binarios/JSON).
