-- Crear Base de Datos si no existe
CREATE DATABASE IF NOT EXISTS hotel_db;
USE hotel_db;

-- 1. Tabla de Clientes
CREATE TABLE IF NOT EXISTS cliente (
    dni VARCHAR(20) PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    contrasenia VARCHAR(255) NOT NULL,
    nombre VARCHAR(50),
    apellido VARCHAR(50),
    sexo CHAR(1),
    edad INT,
    domicilio VARCHAR(150),
    origen VARCHAR(100)
    );

-- 2. Tabla de Empleados
CREATE TABLE IF NOT EXISTS empleado (
    id_empleado INT AUTO_INCREMENT PRIMARY KEY,
    dni VARCHAR(20) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    contrasenia VARCHAR(255) NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    rol ENUM('ADMINISTRADOR', 'RECEPCIONISTA') NOT NULL
    );

-- 3. Tabla de Habitaciones
CREATE TABLE IF NOT EXISTS habitacion (
    numero INT PRIMARY KEY,
    tipo ENUM('INDIVIDUAL', 'DOBLE', 'SUITE') NOT NULL,
    precio_por_noche DECIMAL(10, 2) NOT NULL,
    estado ENUM('DISPONIBLE', 'OCUPADA', 'RESERVADA', 'MANTENIMIENTO', 'LIMPIEZA') NOT NULL DEFAULT 'DISPONIBLE',
    motivo_no_disponibilidad VARCHAR(255) DEFAULT NULL
    );

-- 4. Tabla de Reservas
CREATE TABLE IF NOT EXISTS reserva (
    id_reserva INT AUTO_INCREMENT PRIMARY KEY,
    dni_cliente VARCHAR(20) NOT NULL,
    numero_habitacion INT NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    estado_reserva ENUM('PENDIENTE', 'CHECK_IN', 'CHECK_OUT', 'CANCELADA') DEFAULT 'PENDIENTE',
    CONSTRAINT fk_reserva_cliente FOREIGN KEY (dni_cliente) REFERENCES cliente(dni) ON DELETE CASCADE,
    CONSTRAINT fk_reserva_habitacion FOREIGN KEY (numero_habitacion) REFERENCES habitacion(numero) ON DELETE CASCADE
    );

-- 5. Tabla de Estadías / Ocupaciones
CREATE TABLE IF NOT EXISTS estadia (
   id_estadia INT AUTO_INCREMENT PRIMARY KEY,
   id_reserva INT NOT NULL,
   fecha_checkin DATETIME NOT NULL,
   fecha_checkout DATETIME DEFAULT NULL,
   activa BOOLEAN DEFAULT TRUE,
   CONSTRAINT fk_estadia_reserva FOREIGN KEY (id_reserva) REFERENCES reserva(id_reserva)
);

-- Insertar datos iniciales de prueba
INSERT IGNORE INTO empleado (id_empleado, dni, email, contrasenia, nombre, apellido, rol) VALUES
(1, '11111111', 'admin@hotel.com', 'admin123', 'Admin', 'Sistema', 'ADMINISTRADOR'),
(2, '22222222', 'conserje@hotel.com', 'conserje123', 'Juan', 'Pérez', 'RECEPCIONISTA');

INSERT IGNORE INTO habitacion (numero, tipo, precio_por_noche, estado, motivo_no_disponibilidad) VALUES
(101, 'INDIVIDUAL', 5000.00, 'DISPONIBLE', NULL),
(102, 'DOBLE', 8000.00, 'DISPONIBLE', NULL),
(201, 'SUITE', 15000.00, 'LIMPIEZA', 'Limpieza profunda de alfombra'),
(202, 'DOBLE', 8500.00, 'MANTENIMIENTO', 'Reparación de aire acondicionado');