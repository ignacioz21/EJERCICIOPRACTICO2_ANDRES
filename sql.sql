-- Base de datos para sistema de gestión de cine/teatro
-- Ejercicio Práctico 2

CREATE DATABASE IF NOT EXISTS cinema_teatro_db;
USE cinema_teatro_db;

-- Tabla de roles
CREATE TABLE roles (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre_rol VARCHAR(50) NOT NULL UNIQUE,
    descripcion TEXT,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de usuarios
CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL, -- Para almacenar hash de contraseña
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    telefono VARCHAR(15),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE
);

-- Tabla intermedia para relacionar usuarios con roles (muchos a muchos)
CREATE TABLE usuario_roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_rol INT NOT NULL,
    fecha_asignacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_rol) REFERENCES roles(id_rol) ON DELETE CASCADE,
    UNIQUE KEY unique_user_role (id_usuario, id_rol)
);

-- Tabla de películas/obras
CREATE TABLE peliculas (
    id_pelicula INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    descripcion TEXT,
    duracion INT NOT NULL, -- duración en minutos
    genero VARCHAR(100),
    director VARCHAR(100),
    clasificacion VARCHAR(10), -- G, PG, PG-13, R, etc.
    imagen_url VARCHAR(500),
    trailer_url VARCHAR(500),
    fecha_estreno DATE,
    activa BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de salas
CREATE TABLE salas (
    id_sala INT AUTO_INCREMENT PRIMARY KEY,
    nombre_sala VARCHAR(50) NOT NULL,
    capacidad INT NOT NULL,
    tipo_sala VARCHAR(50), -- 2D, 3D, IMAX, etc.
    activa BOOLEAN DEFAULT TRUE
);

-- Tabla de funciones (horarios de proyección)
CREATE TABLE funciones (
    id_funcion INT AUTO_INCREMENT PRIMARY KEY,
    id_pelicula INT NOT NULL,
    id_sala INT NOT NULL,
    fecha_funcion DATE NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    precio DECIMAL(8,2) NOT NULL,
    asientos_disponibles INT NOT NULL,
    activa BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_pelicula) REFERENCES peliculas(id_pelicula) ON DELETE CASCADE,
    FOREIGN KEY (id_sala) REFERENCES salas(id_sala) ON DELETE CASCADE
);

-- Tabla de reservas
CREATE TABLE reservas (
    id_reserva INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_funcion INT NOT NULL,
    cantidad_boletos INT NOT NULL,
    precio_total DECIMAL(10,2) NOT NULL,
    estado_reserva ENUM('CONFIRMADA', 'CANCELADA', 'PENDIENTE') DEFAULT 'CONFIRMADA',
    fecha_reserva TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_cancelacion TIMESTAMP NULL,
    codigo_reserva VARCHAR(20) UNIQUE NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_funcion) REFERENCES funciones(id_funcion) ON DELETE CASCADE
);

-- Tabla de asientos (opcional, para control más detallado)
CREATE TABLE asientos (
    id_asiento INT AUTO_INCREMENT PRIMARY KEY,
    id_sala INT NOT NULL,
    numero_fila CHAR(2) NOT NULL,
    numero_asiento INT NOT NULL,
    tipo_asiento ENUM('NORMAL', 'VIP', 'DISCAPACITADO') DEFAULT 'NORMAL',
    FOREIGN KEY (id_sala) REFERENCES salas(id_sala) ON DELETE CASCADE,
    UNIQUE KEY unique_seat (id_sala, numero_fila, numero_asiento)
);

-- Tabla de reserva_asientos (para asientos específicos reservados)
CREATE TABLE reserva_asientos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_reserva INT NOT NULL,
    id_asiento INT NOT NULL,
    FOREIGN KEY (id_reserva) REFERENCES reservas(id_reserva) ON DELETE CASCADE,
    FOREIGN KEY (id_asiento) REFERENCES asientos(id_asiento) ON DELETE CASCADE,
    UNIQUE KEY unique_reservation_seat (id_reserva, id_asiento)
);

-- Insertar roles por defecto
INSERT INTO roles (nombre_rol, descripcion) VALUES 
('ADMIN', 'Administrador del sistema con acceso completo'),
('USER', 'Usuario regular que puede realizar reservas');

-- Insertar usuario administrador por defecto
INSERT INTO usuarios (username, password, nombre, apellido, email, telefono) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8ioctKltBg7kX6rdKat5Ra5jj3ixe', 'Admin', 'Sistema', 'admin@cinema.com', '1234567890'); -- password: admin123

-- Insertar usuario regular de ejemplo
INSERT INTO usuarios (username, password, nombre, apellido, email, telefono) VALUES
('user', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'Usuario', 'Demo', 'user@gmail.com', '0987654321'); -- password: password

-- Asignar roles a usuarios
INSERT INTO usuario_roles (id_usuario, id_rol) VALUES
(1, 1), -- admin tiene rol ADMIN
(2, 2); -- user tiene rol USER

-- Insertar salas de ejemplo
INSERT INTO salas (nombre_sala, capacidad, tipo_sala) VALUES
('Sala 1', 100, '2D'),
('Sala 2', 80, '3D'),
('Sala VIP', 50, 'VIP');

-- Insertar películas de ejemplo
INSERT INTO peliculas (titulo, descripcion, duracion, genero, director, clasificacion, fecha_estreno) VALUES
('Avatar: El Camino del Agua', 'Secuela de la exitosa película Avatar', 192, 'Ciencia Ficción', 'James Cameron', 'PG-13', '2022-12-16'),
('Top Gun: Maverick', 'Pete "Maverick" Mitchell regresa después de más de 30 años de servicio', 131, 'Acción', 'Joseph Kosinski', 'PG-13', '2022-05-27'),
('Black Panther: Wakanda Forever', 'Wakanda Forever honra el legado del Rey T\'Challa', 161, 'Acción', 'Ryan Coogler', 'PG-13', '2022-11-11');

-- Insertar funciones de ejemplo
INSERT INTO funciones (id_pelicula, id_sala, fecha_funcion, hora_inicio, hora_fin, precio, asientos_disponibles) VALUES
(1, 1, '2025-08-15', '14:00:00', '17:12:00', 8.50, 100),
(1, 1, '2025-08-15', '18:00:00', '21:12:00', 10.00, 100),
(2, 2, '2025-08-15', '15:30:00', '17:41:00', 9.00, 80),
(3, 3, '2025-08-16', '19:00:00', '21:41:00', 12.00, 50);

-- Índices para optimización
CREATE INDEX idx_usuario_roles_usuario ON usuario_roles(id_usuario);
CREATE INDEX idx_usuario_roles_rol ON usuario_roles(id_rol);
CREATE INDEX idx_funciones_fecha ON funciones(fecha_funcion);
CREATE INDEX idx_reservas_usuario ON reservas(id_usuario);
CREATE INDEX idx_reservas_codigo ON reservas(codigo_reserva);

-- Vista para obtener usuarios con sus roles (útil para consultas frecuentes)
CREATE VIEW vista_usuarios_roles AS
SELECT 
    u.id_usuario,
    u.username,
    u.nombre,
    u.apellido,
    u.email,
    r.nombre_rol,
    ur.fecha_asignacion
FROM usuarios u
JOIN usuario_roles ur ON u.id_usuario = ur.id_usuario
JOIN roles r ON ur.id_rol = r.id_rol
WHERE u.activo = TRUE;