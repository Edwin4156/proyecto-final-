-- =====================================================
-- ESTRUCTURA INICIAL PARA SISTEMA DE FARMACIA
-- Base de datos: farmacia_db (PostgreSQL)
-- =====================================================

-- Tabla: usuarios
CREATE TABLE usuarios (
  id SERIAL PRIMARY KEY,
  nombre VARCHAR(150) NOT NULL,
  usuario VARCHAR(50) UNIQUE NOT NULL,
  pass VARCHAR(255) NOT NULL,
  email VARCHAR(100),
  rol VARCHAR(50) NOT NULL DEFAULT 'vendedor',
  activo BOOLEAN DEFAULT true,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla: productos
CREATE TABLE productos (
  id SERIAL PRIMARY KEY,
  nombre VARCHAR(200) NOT NULL,
  categoria VARCHAR(100) NOT NULL,
  precio DECIMAL(10,2) NOT NULL,
  costo DECIMAL(10,2) NOT NULL,
  stock INT NOT NULL DEFAULT 0,
  stock_min INT NOT NULL DEFAULT 10,
  lote VARCHAR(50),
  codigo_barras VARCHAR(100),
  proveedor VARCHAR(150),
  fecha_ingreso DATE,
  fecha_caducidad DATE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla: ventas
CREATE TABLE ventas (
  id SERIAL PRIMARY KEY,
  fecha DATE NOT NULL,
  producto_id INT NOT NULL,
  producto_nombre VARCHAR(200) NOT NULL,
  cantidad INT NOT NULL,
  precio DECIMAL(10,2) NOT NULL,
  total DECIMAL(10,2) NOT NULL,
  cliente VARCHAR(150),
  nit VARCHAR(50),
  descuento DECIMAL(5,2) DEFAULT 0,
  metodo_pago VARCHAR(50),
  usuario_id INT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (producto_id) REFERENCES productos(id),
  FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- Tabla: compras
CREATE TABLE compras (
  id SERIAL PRIMARY KEY,
  fecha DATE NOT NULL,
  producto_id INT NOT NULL,
  producto_nombre VARCHAR(200) NOT NULL,
  cantidad INT NOT NULL,
  costo DECIMAL(10,2) NOT NULL,
  total DECIMAL(10,2) NOT NULL,
  proveedor VARCHAR(150),
  usuario_id INT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (producto_id) REFERENCES productos(id),
  FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- Tabla: permisos_rol
CREATE TABLE permisos_rol (
  id SERIAL PRIMARY KEY,
  rol VARCHAR(50) NOT NULL UNIQUE,
  dashboard_r BOOLEAN DEFAULT false,
  dashboard_w BOOLEAN DEFAULT false,
  dashboard_d BOOLEAN DEFAULT false,
  productos_r BOOLEAN DEFAULT false,
  productos_w BOOLEAN DEFAULT false,
  productos_d BOOLEAN DEFAULT false,
  inventario_r BOOLEAN DEFAULT false,
  inventario_w BOOLEAN DEFAULT false,
  inventario_d BOOLEAN DEFAULT false,
  ventas_r BOOLEAN DEFAULT false,
  ventas_w BOOLEAN DEFAULT false,
  ventas_d BOOLEAN DEFAULT false,
  compras_r BOOLEAN DEFAULT false,
  compras_w BOOLEAN DEFAULT false,
  compras_d BOOLEAN DEFAULT false,
  alertas_r BOOLEAN DEFAULT false,
  alertas_w BOOLEAN DEFAULT false,
  alertas_d BOOLEAN DEFAULT false,
  usuarios_r BOOLEAN DEFAULT false,
  usuarios_w BOOLEAN DEFAULT false,
  usuarios_d BOOLEAN DEFAULT false,
  reportes_r BOOLEAN DEFAULT false,
  reportes_w BOOLEAN DEFAULT false,
  reportes_d BOOLEAN DEFAULT false,
  facturacion_r BOOLEAN DEFAULT false,
  facturacion_w BOOLEAN DEFAULT false,
  facturacion_d BOOLEAN DEFAULT false,
  pos_r BOOLEAN DEFAULT false,
  pos_w BOOLEAN DEFAULT false,
  pos_d BOOLEAN DEFAULT false,
  kardex_r BOOLEAN DEFAULT false,
  kardex_w BOOLEAN DEFAULT false,
  kardex_d BOOLEAN DEFAULT false,
  ia_r BOOLEAN DEFAULT false,
  ia_w BOOLEAN DEFAULT false,
  ia_d BOOLEAN DEFAULT false
);

-- Tabla: logs_auditoria
CREATE TABLE logs_auditoria (
  id SERIAL PRIMARY KEY,
  fecha_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  usuario VARCHAR(50),
  accion VARCHAR(100),
  detalle VARCHAR(500),
  modulo VARCHAR(50)
);

-- Tabla: kardex (movimientos de inventario)
CREATE TABLE kardex (
  id SERIAL PRIMARY KEY,
  fecha DATE NOT NULL,
  tipo VARCHAR(20) NOT NULL, -- 'ENTRADA' o 'SALIDA'
  producto_id INT NOT NULL,
  producto_nombre VARCHAR(200) NOT NULL,
  cantidad INT NOT NULL,
  referencia VARCHAR(200), -- Venta #001, Compra - Proveedor
  usuario_id INT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (producto_id) REFERENCES productos(id),
  FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- Índices para optimización
CREATE INDEX idx_ventas_fecha ON ventas(fecha);
CREATE INDEX idx_ventas_usuario ON ventas(usuario_id);
CREATE INDEX idx_compras_fecha ON compras(fecha);
CREATE INDEX idx_kardex_fecha ON kardex(fecha);
CREATE INDEX idx_kardex_producto ON kardex(producto_id);
CREATE INDEX idx_productos_categoria ON productos(categoria);
CREATE INDEX idx_logs_fecha ON logs_auditoria(fecha_hora);

-- Insertar datos iniciales

-- Usuarios por defecto
INSERT INTO usuarios (nombre, usuario, pass, email, rol, activo) VALUES
('Carlos Méndez', 'admin1', 'admin123', 'admin1@nexus.gt', 'administrador', true),
('Luisa Fuentes', 'admin2', 'admin456', 'admin2@nexus.gt', 'administrador', true),
('Pedro Ríos', 'vendedor1', 'vend123', 'vendedor1@nexus.gt', 'vendedor', true);

-- Permisos por rol
INSERT INTO permisos_rol (rol,
  dashboard_r, dashboard_w, dashboard_d,
  productos_r, productos_w, productos_d,
  inventario_r, inventario_w, inventario_d,
  ventas_r, ventas_w, ventas_d,
  compras_r, compras_w, compras_d,
  alertas_r, alertas_w, alertas_d,
  usuarios_r, usuarios_w, usuarios_d,
  reportes_r, reportes_w, reportes_d,
  facturacion_r, facturacion_w, facturacion_d,
  pos_r, pos_w, pos_d,
  kardex_r, kardex_w, kardex_d,
  ia_r, ia_w, ia_d)
VALUES
('administrador',
  true, true, true,
  true, true, true,
  true, true, true,
  true, true, true,
  true, true, true,
  true, false, false,
  true, true, true,
  true, false, false,
  true, true, false,
  true, true, false,
  true, false, false,
  true, true, false),
('vendedor',
  true, false, false,
  true, false, false,
  true, false, false,
  true, true, false,
  false, false, false,
  true, false, false,
  false, false, false,
  false, false, false,
  true, true, false,
  true, true, false,
  false, false, false,
  true, true, false);

-- Productos de prueba
INSERT INTO productos (nombre, categoria, precio, costo, stock, stock_min, lote, codigo_barras, proveedor, fecha_ingreso, fecha_caducidad) VALUES
('Paracetamol 500mg', 'Analgésico', 25.00, 12.00, 150, 30, 'L-2024-001', '7501050351002', 'DistribuFarma S.A.', CURRENT_DATE - INTERVAL '60 days', CURRENT_DATE + INTERVAL '365 days'),
('Amoxicilina 500mg', 'Antibiótico', 85.00, 45.00, 60, 20, 'L-2024-012', '7501050351003', 'MedGroup GT', CURRENT_DATE - INTERVAL '45 days', CURRENT_DATE + INTERVAL '180 days'),
('Metformina 850mg', 'Antidiabético', 45.00, 20.00, 8, 25, 'L-2023-098', '7501050351004', 'FarmaPlus', CURRENT_DATE - INTERVAL '90 days', CURRENT_DATE + INTERVAL '28 days'),
('Loratadina 10mg', 'Antihistamínico', 35.00, 15.00, 90, 20, 'L-2024-034', '7501050351005', 'DistribuFarma S.A.', CURRENT_DATE - INTERVAL '5 days', CURRENT_DATE + INTERVAL '500 days'),
('Omeprazol 20mg', 'Gastro', 55.00, 25.00, 40, 15, 'L-2024-021', '7501050351006', 'MedGroup GT', CURRENT_DATE - INTERVAL '30 days', CURRENT_DATE + INTERVAL '14 days'),
('Ibuprofeno 400mg', 'Analgésico', 30.00, 14.00, 120, 30, 'L-2024-045', '7501050351007', 'FarmaPlus', CURRENT_DATE - INTERVAL '3 days', CURRENT_DATE + INTERVAL '730 days'),
('Vitamina C 1000mg', 'Vitaminas', 40.00, 18.00, 200, 50, 'L-2024-060', '7501050351008', 'NutriMed GT', CURRENT_DATE - INTERVAL '10 days', CURRENT_DATE + INTERVAL '400 days'),
('Atorvastatina 20mg', 'Cardiovascular', 95.00, 55.00, 35, 15, 'L-2024-071', '7501050351009', 'FarmaPlus', CURRENT_DATE - INTERVAL '20 days', CURRENT_DATE + INTERVAL '300 days');
