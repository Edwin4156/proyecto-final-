# Plan de Migración: Sistema de Farmacia
## Quemado → PostgreSQL + Spring Boot REST API

---

## FASE 1: ESTRUCTURA DE DATOS

### Tablas Creadas (SQL)
- ✅ `usuarios` - Gestión de usuarios y autenticación
- ✅ `productos` - Catálogo de fármacos
- ✅ `ventas` - Registro de ventas/transacciones
- ✅ `compras` - Registro de compras a proveedores
- ✅ `kardex` - Historial de movimientos de inventario
- ✅ `permisos_rol` - Control de acceso por rol
- ✅ `logs_auditoria` - Auditoría de acciones

---

## FASE 2: ENDPOINTS REST REQUERIDOS

### 📋 AUTENTICACIÓN
```
POST   /api/auth/login              doLogin()
POST   /api/auth/logout             doLogout()
POST   /api/auth/session            (validar sesión)
```

### 👥 USUARIOS (Permisos: usuarios CRUD)
```
GET    /api/usuarios                 rUsuarios()
POST   /api/usuarios                 saveUser() / showAddUser()
PUT    /api/usuarios/{id}            updateUser() / showEditUser()
DELETE /api/usuarios/{id}            delUser()
PATCH  /api/usuarios/{id}/toggle     toggleUser()
PATCH  /api/usuarios/{id}/password   changePass() / showChangePass()
GET    /api/usuarios/{id}/permisos   showPerms()
```

### 📦 PRODUCTOS (Permisos: productos CRUD)
```
GET    /api/productos                rProductos()
GET    /api/productos/search         (búsqueda y filtros)
GET    /api/productos/{id}           (detalle individual)
POST   /api/productos                showAddP() → saveP()
PUT    /api/productos/{id}           showEditP() → saveP(id)
DELETE /api/productos/{id}           delP()
GET    /api/productos/inventario     rInv() (con stats)
GET    /api/productos/inventario/stats
```

### 💰 VENTAS (Permisos: ventas CRUD)
```
GET    /api/ventas                   rVentas() (con filtros por periodo)
GET    /api/ventas/por-usuario       (filtrar por vendedor)
POST   /api/ventas                   showAddV() → saveV()
DELETE /api/ventas/{id}              delV()
GET    /api/ventas/{id}/ticket       showTicket() (para PDF)
POST   /api/ventas/excel             exportVentasExcel()
POST   /api/ventas/pdf               exportVentasPDF()
POST   /api/ventas/csv               exportVentasCSV()
```

### 🛒 CARRITO POS (Permisos: pos CRUD)
```
POST   /api/pos/finalizar            posFinalizar() (confirmar venta)
GET    /api/pos/productos            renderPOSProducts() (productos disponibles)
GET    /api/pos/calcular             posCalcCambio()
```

### 🛍️ COMPRAS (Permisos: compras CRUD)
```
GET    /api/compras                  rCompras() (con filtros)
POST   /api/compras                  showAddC() → saveC()
DELETE /api/compras/{id}             delC()
POST   /api/compras/pdf              exportComprasPDF()
POST   /api/compras/excel            exportComprasExcel()
```

### 📊 KARDEX (Permisos: kardex READ)
```
GET    /api/kardex                   rKardex()
GET    /api/kardex/movimientos       getKardexData()
POST   /api/kardex/pdf               exportKardexPDF()
POST   /api/kardex/excel             exportKardexExcel()
POST   /api/kardex/csv               exportKardexCSV()
```

### 📈 DASHBOARD (Permisos: dashboard READ)
```
GET    /api/dashboard/stats          rDash() (cards principales)
GET    /api/dashboard/charts-data    buildDashCharts() (datos para gráficos)
GET    /api/alertas                  rAlertas()
GET    /api/alertas/count            alertCount()
```

### 📋 REPORTES (Permisos: reportes READ)
```
GET    /api/reportes                 rReportes()
GET    /api/reportes/charts          buildReportCharts()
POST   /api/reportes/ventas/pdf      exportVentasPDF()
POST   /api/reportes/inventario/pdf  exportInventarioPDF()
POST   /api/reportes/kardex/pdf      exportKardexPDF()
```

### 🤖 ASISTENTE IA (Permisos: ia READ/WRITE)
```
POST   /api/ia/analizar              iaAnalyze() (procesar pregunta)
GET    /api/ia/datos                 (retornar stats para análisis)
```

### 📄 FACTURACIÓN FEL (Permisos: facturacion READ/WRITE)
```
GET    /api/facturacion              rFact()
POST   /api/facturacion/crear        showNewFact() → saveV()
POST   /api/facturacion/{id}/pdf     exportTicketPDF()
GET    /api/facturacion/stats        (contadores)
```

### 📊 LOGS / AUDITORÍA
```
GET    /api/logs                     (mostrar en usuarios)
POST   /api/logs                     addLog() (automático en cada acción)
```

---

## FASE 3: MAPEO FUNCIONES HTML → ENDPOINTS

### CRUD PRODUCTOS
| Función HTML | Endpoint | Método | Datos |
|---|---|---|---|
| `showAddP()` | GET /api/productos | POST form | nombre, categoria, precio, etc |
| `saveP(id)` | /api/productos | POST/PUT | (id=nuevo → POST, id!=null → PUT) |
| `showEditP(id)` | /api/productos/{id} | GET | retorna producto |
| `delP(id)` | /api/productos/{id} | DELETE | - |
| `rProductos()` | /api/productos?q=busqueda | GET | lista con filtros |

### CRUD VENTAS
| Función HTML | Endpoint | Método | Datos |
|---|---|---|---|
| `showAddV()` | /api/ventas | POST form | productoId, cantidad, precio, etc |
| `saveV()` | /api/ventas | POST | validar stock, restar stock |
| `delV(id)` | /api/ventas/{id} | DELETE | - |
| `rVentas()` | /api/ventas?filtro=dia/semana/mes | GET | lista filtrada |
| `showTicket(id)` | /api/ventas/{id}/ticket | GET | retorna JSON para modal |
| `filterByPer()` | /api/ventas?periodo=todo/dia/semana/mes | GET | - |

### CRUD COMPRAS
| Función HTML | Endpoint | Método | Datos |
|---|---|---|---|
| `showAddC()` | /api/compras | POST form | productoId, cantidad, costo, etc |
| `saveC()` | /api/compras | POST | sumar stock |
| `delC(id)` | /api/compras/{id} | DELETE | - |
| `rCompras()` | /api/compras?filtro | GET | lista filtrada |

### POS
| Función HTML | Endpoint | Método | Datos |
|---|---|---|---|
| `renderPOSProducts()` | /api/pos/productos?stock>0 | GET | productos con stock |
| `posAdd(id)` | - | LOCAL | agregar al carrito |
| `posQty(i,d)` | - | LOCAL | modificar cantidad local |
| `posFinalizar()` | /api/pos/finalizar | POST | carrito completo |

### USUARIOS
| Función HTML | Endpoint | Método | Datos |
|---|---|---|---|
| `saveUser()` | /api/usuarios | POST | nombre, usuario, pass, rol, email |
| `updateUser(id)` | /api/usuarios/{id} | PUT | nombre, email, usuario, rol |
| `changePass(id)` | /api/usuarios/{id}/password | PATCH | newPassword |
| `toggleUser(id,v)` | /api/usuarios/{id}/toggle | PATCH | activo: true/false |
| `delUser(id)` | /api/usuarios/{id} | DELETE | - |
| `rUsuarios()` | /api/usuarios?q=busqueda | GET | lista de usuarios |

### REPORTES / EXPORTACIÓN
| Función HTML | Endpoint | Método | Response |
|---|---|---|---|
| `exportVentasPDF()` | /api/reportes/ventas/pdf | POST | PDF file |
| `exportVentasExcel()` | /api/reportes/ventas/excel | POST | XLSX file |
| `exportVentasCSV()` | /api/reportes/ventas/csv | POST | CSV file |
| `exportInventarioPDF()` | /api/reportes/inventario/pdf | POST | PDF file |
| `exportInventarioExcel()` | /api/reportes/inventario/excel | POST | XLSX file |
| `exportComprasPDF()` | /api/reportes/compras/pdf | POST | PDF file |
| `exportKardexPDF()` | /api/reportes/kardex/pdf | POST | PDF file |
| `exportKardexExcel()` | /api/reportes/kardex/excel | POST | XLSX file |
| `exportKardexCSV()` | /api/reportes/kardex/csv | POST | CSV file |

### DASHBOARD
| Función HTML | Endpoint | Método | Response |
|---|---|---|---|
| `rDash()` | /api/dashboard/stats | GET | JSON con stats |
| `buildDashCharts()` | /api/dashboard/charts/ventas-14d | GET | datos para gráfico |
| `buildDashCharts()` | /api/dashboard/charts/top-productos | GET | datos para gráfico |
| `buildDashCharts()` | /api/dashboard/charts/metodos-pago | GET | datos para gráfico |
| `rAlertas()` | /api/alertas | GET | productos caducados, stock bajo |
| `alertCount()` | /api/alertas/count | GET | número de alertas |

### IA
| Función HTML | Endpoint | Método | Response |
|---|---|---|---|
| `iaAnalyze(q)` | /api/ia/analizar | POST | respuesta de análisis |

---

## FASE 4: ARQUITECTURA SPRING BOOT

### Structure recomendada:
```
src/main/java/com/example/Sistema/de/Farmacia/
├── controller/
│   ├── AuthController.java
│   ├── UsuarioController.java
│   ├── ProductoController.java
│   ├── VentaController.java
│   ├── CompraController.java
│   ├── PosController.java
│   ├── KardexController.java
│   ├── ReporteController.java
│   ├── DashboardController.java
│   └── IAController.java
├── service/
│   ├── UsuarioService.java
│   ├── ProductoService.java
│   ├── VentaService.java
│   ├── CompraService.java
│   ├── KardexService.java
│   ├── ReporteService.java
│   └── IAService.java
├── repository/
│   ├── UsuarioRepository.java
│   ├── ProductoRepository.java
│   ├── VentaRepository.java
│   ├── CompraRepository.java
│   ├── KardexRepository.java
│   ├── PermisosRolRepository.java
│   └── LogsAuditoriaRepository.java
├── model/
│   ├── Usuario.java
│   ├── Producto.java
│   ├── Venta.java
│   ├── Compra.java
│   ├── Kardex.java
│   ├── PermisosRol.java
│   └── LogsAuditoria.java
├── dto/
│   ├── VentaDTO.java
│   ├── CompraDTO.java
│   ├── ProductoDTO.java
│   └── DashboardStatsDTO.java
├── util/
│   ├── ExportService.java (PDF, Excel, CSV)
│   ├── PermisosUtil.java
│   └── AuthUtil.java
└── config/
    ├── JwtConfig.java
    ├── CorsConfig.java
    └── DatabaseConfig.java
```

---

## FASE 5: DEPENDENCIAS POM.XML

```xml
<!-- Spring Data JPA -->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- PostgreSQL Driver -->
<dependency>
  <groupId>org.postgresql</groupId>
  <artifactId>postgresql</artifactId>
  <version>42.7.0</version>
</dependency>

<!-- JWT Token -->
<dependency>
  <groupId>io.jsonwebtoken</groupId>
  <artifactId>jjwt-api</artifactId>
  <version>0.11.5</version>
</dependency>

<!-- Apache POI (Excel) -->
<dependency>
  <groupId>org.apache.poi</groupId>
  <artifactId>poi-ooxml</artifactId>
  <version>5.0.0</version>
</dependency>

<!-- iText (PDF) -->
<dependency>
  <groupId>com.itextpdf</groupId>
  <artifactId>itextpdf</artifactId>
  <version>5.5.13.2</version>
</dependency>

<!-- OpenCSV -->
<dependency>
  <groupId>com.opencsv</groupId>
  <artifactId>opencsv</artifactId>
  <version>5.7</version>
</dependency>
```

---

## FASE 6: CAMBIOS EN INDEX.HTML (Consumir API)

### Reemplazar funciones quemadas:
```javascript
// Antes (localStorage):
let db = {productos: [...], ventas: [...], compras: [...]};
saveAll() { localStorage.set(DB_KEY, JSON.stringify(db)); }
loadAll() { db = JSON.parse(localStorage.get(DB_KEY)); }

// Después (API REST):
const API_URL = 'http://localhost:8080/api';

async function loadDatos() {
  const productos = await fetch(`${API_URL}/productos`).then(r => r.json());
  const ventas = await fetch(`${API_URL}/ventas`).then(r => r.json());
  const compras = await fetch(`${API_URL}/compras`).then(r => r.json());
  // Guardar en db local o consumir directamente
}

async function saveP() {
  const payload = { nombre, categoria, precio, ... };
  await fetch(`${API_URL}/productos`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload)
  });
}
```

---

## 📅 CRONOGRAMA RECOMENDADO

| Fase | Tarea | Duración | Orden |
|---|---|---|---|
| 1 | Crear BD PostgreSQL + tablas (SQL) | 1 día | **1º** |
| 2 | Crear entities (modelo JPA) | 1 día | **2º** |
| 3 | Crear repositories | 1 día | **3º** |
| 4 | Crear services | 3 días | **4º** |
| 5 | Crear controllers + endpoints | 3 días | **5º** |
| 6 | Crear modales/forms en HTML | 2 días | **6º** |
| 7 | Conectar fetch() en HTML → API | 3 días | **7º** |
| 8 | Testing + ajustes | 2 días | **8º** |

**Total estimado: 16 días**

---

## 🎯 ORDEN RECOMENDADO DE IMPLEMENTACIÓN

1. **SQL + PostgreSQL** → crear base de datos
2. **JPA Models** → mapear tablas
3. **Repository (CRUD básico)**
4. **AuthController** → login/logout
5. **UsuarioController** → CRUD usuarios
6. **ProductoController** → CRUD productos
7. **VentaController** → CRUD ventas + kardex automático
8. **CompraController** → CRUD compras + kardex automático
9. **DashboardController** → stats
10. **ReporteController** → exportación
11. **HTMLmodificado** → cambiar fetch() por API

---

## 🔍 VALIDACIONES IMPORTANTES

- ✅ Al crear venta: **restar stock** (crear automáticamente registro en kardex)
- ✅ Al crear compra: **sumar stock** (crear automáticamente registro en kardex)
- ✅ Permisos: antes de cada acción, validar con `/api/auth/check-permission`
- ✅ Logs: cada acción CRUD debe crear registro en `logs_auditoria`
- ✅ Filtros de período: `?desde=2024-01-01&hasta=2024-12-31`
- ✅ Búsqueda: `?q=paracetamol` (buscar en nombre, categoría, proveedor)

