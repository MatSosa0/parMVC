CREATE TABLE producto (
	id int auto_increment PRIMARY KEY,
	nombre varchar(50),
	descripcion varchar(50),
	unidades int,
	costo decimal(10, 2),
	precio decimal (10, 2),
	categoria varchar(50)
);

CREATE TABLE usuario (
	id int auto_increment PRIMARY KEY,
	nombre varchar(50),
	contrasenia varchar(50),
	administrador tinyint(1)
);

CREATE TABLE auditoria (
	idAuditoria int auto_increment PRIMARY KEY,
	nombreProducto varchar(50),
	descripcionProducto varchar(50),
	unidadesProducto int,
	costoProducto decimal(10, 2),
	PrecioProducto decimal (10, 2),
	categoriaProducto varchar(50),
	idUsuario int,
	nombreUsuario varchar(50),
	descripcionAccion varchar(20)
);

CREATE TABLE cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    cedula VARCHAR(20),
    correo VARCHAR(100),
    telefono VARCHAR(20),
    direccion TEXT
);

CREATE TABLE proveedor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    ruc VARCHAR(20),
    correo VARCHAR(100),
    telefono VARCHAR(20),
    direccion TEXT
);

CREATE TABLE compra (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero_factura VARCHAR(50),
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    proveedor_id INT,
    forma_pago VARCHAR(50),
    total_factura DECIMAL(10,2),
    FOREIGN KEY (proveedor_id) REFERENCES proveedor(id)
);

CREATE TABLE detalle_compra (
    id INT AUTO_INCREMENT PRIMARY KEY,
    compra_id INT,
    producto_id INT,
    cantidad INT,
    precio_unitario DECIMAL(10,2),
    total_articulo DECIMAL(10,2),
    FOREIGN KEY (compra_id) REFERENCES compra(id),
    FOREIGN KEY (producto_id) REFERENCES producto(id)
);

CREATE TABLE venta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero_factura VARCHAR(50),
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    cliente_id INT,
    forma_pago VARCHAR(50),
    total_factura DECIMAL(10,2),
    anulado BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);


CREATE TABLE detalle_venta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    venta_id INT,
    producto_id INT,
    cantidad INT,
    precio_unitario DECIMAL(10,2),
    total_articulo DECIMAL(10,2),
    FOREIGN KEY (venta_id) REFERENCES ventas(id),
    FOREIGN KEY (producto_id) REFERENCES producto(id)
);
