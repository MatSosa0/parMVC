INSERT INTO producto (nombre, descripcion, unidades, costo, precio, categoria)
VALUES
	('Yerba Mate Pajarito', 'Menta 1kg', 5, 5000, 7000, 'Alimentos'),
	('Azucar Morena', '1kg', 5, 6500, 9000, 'Alimentos'),
	('Leche Entera Trebol', '1lt', 7, 8000, 10000, 'Alimentos');

INSERT INTO usuario (nombre, contrasenia, administrador)
VALUES
	('admin', 'admin', 1),
	('cpalacios', '2305', 1),
	('user1', '1234', 0),
	('user2', '1234', 1),
	('user3', '1234', 0);


INSERT INTO cliente (nombre, cedula, correo, telefono, direccion) VALUES
('Ana María López', '12345678', 'ana.lopez@email.com', '0981-234567', 'Av. Siempre Viva 123, Asunción'),
('Carlos Fernández', '87654321', 'carlos.fernandez@email.com', '0982-345678', 'Calle Falsa 456, Ciudad del Este'),
('María Gómez', '11223344', 'maria.gomez@email.com', '0983-456789', 'Mcal. López 789, Encarnación'),
('Juan Pérez', '44332211', 'juan.perez@email.com', '0984-567890', 'Independencia 101, San Lorenzo'),
('Sofía Martínez', '55667788', 'sofia.martinez@email.com', '0985-678901', 'Presidente Franco 202, Luque'),
('Luis Rodríguez', '88776655', 'luis.rodriguez@email.com', '0986-789012', 'Ypacaraí 303, Caaguazú'),
('Elena Díaz', '33445566', 'elena.diaz@email.com', '0987-890123', 'San Blas 404, Coronel Oviedo'),
('Miguel Torres', '66778899', 'miguel.torres@email.com', '0988-901234', 'Tte. 1ro. Caballero 505, Pedro Juan Caballero'),
('Laura Sánchez', '99887766', 'laura.sanchez@email.com', '0989-012345', 'Ñu Guasu 606, Concepción'),
('Pedro Ramírez', '44556677', 'pedro.ramirez@email.com', '0990-123456', 'Mcal. Estigarribia 707, Caacupé');


INSERT INTO proveedor (nombre, ruc, correo, telefono, direccion)
VALUES
  ('Distribuidora Central SA', '80012345-6', 'contacto@central.com.py', '021 555-123', 'Av. Mcal. Lopez 1234, Asuncion'),
  ('Agropar SRL', '80098765-2', 'ventas@agropar.com.py', '0981 222-345', 'Ruta 2 Km 45, Capiata'),
  ('Industrias Alimenticias Nanduti', '80123456-7', 'info@nanduti.com.py', '0972 444-678', 'Calle Palma 456, Asuncion'),
  ('ElectroFarma SA', '80045678-3', 'soporte@electrofarma.com', '021 123-987', 'Calle Oliva 789, Luque'),
  ('Textiles Guarani', '80234567-8', 'textiles@guarani.com', '0985 333-777', 'Avda. San Martin 2020, San Lorenzo'),
  ('Productos El Ceibo', '80111223-4', 'ventas@elceibo.com.py', '0971 888-321', 'Calle Espana 505, Asuncion'),
  ('Ferreteria El Tornillo', '80345678-9', 'contacto@eltornillo.com', '021 666-111', 'Av. Eusebio Ayala 8000, Fernando de la Mora'),
  ('Distribuidora Nortena', '80432123-5', 'dnorte@proveedores.com', '0982 999-654', 'Ruta 3, Barrio Centro, Limpio'),
  ('Farmacia Vida Nueva', '80567890-1', 'info@vidanueva.com', '021 444-000', 'Av. Acceso Sur, Villa Elisa'),
  ('Alimentos Paraguayos SA', '80678901-2', 'ventas@alipy.com.py', '0983 777-888', 'Ita Enramada 100, Lambare');
