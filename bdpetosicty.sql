CREATE TABLE usuarios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    pnombre VARCHAR(100) NOT NULL,
	snombre VARCHAR(100) NOT NULL,
	papellido VARCHAR(100) NOT NULL,
	sapellido VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    contrasenia VARCHAR(255) NOT NULL,
    direccion TEXT NOT NULL,
    rol ENUM('cliente', 'administrador', 'trabajador') NOT NULL DEFAULT 'cliente',
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE categorias (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE inventario (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre_producto VARCHAR(100) NOT NULL,
    descripcion TEXT NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    stock_actual INT NOT NULL,
    categoria_id INT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id)
);

CREATE TABLE carrito_compras (
    id INT PRIMARY KEY AUTO_INCREMENT,
    usuario_id INT NOT NULL,
    estado ENUM('activo', 'finalizado') NOT NULL DEFAULT 'activo',
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE detalle_carrito (
    id INT PRIMARY KEY AUTO_INCREMENT,
    carrito_id INT NOT NULL,
    producto_id INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (carrito_id) REFERENCES carrito_compras(id),
    FOREIGN KEY (producto_id) REFERENCES inventario(id)
);

