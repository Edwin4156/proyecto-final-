// API Service - Centraliza todas las llamadas a backend
const API_URL = 'http://localhost:8085';
//const API_URL = 'https://edwing.habitak.vip';

// Error validation interceptor
function showValidationError(data) {
  if (data.error === 'Validation failed' && data.fields) {
    const fieldMessages = Object.entries(data.fields)
      .map(([field, msg]) => `• ${msg}`)
      .join('\n');
    showNotification(`Validación:\n${fieldMessages}`, 'error');
    return true;
  }
  return false;
}

// Wrapper para respuestas con validación
async function handleResponse(res) {
  const data = await res.json();
  if (!res.ok && res.status === 400) {
    showValidationError(data);
    throw new Error('Validation error');
  }
  return data;
}

class APIService {
  // ==================== AUTH ====================
  static async login(usuario, pass) {
    const res = await fetch(`${API_URL}/auth/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ usuario, pass })
    });
    return res.json();
  }

  static async logout() {
    return fetch(`${API_URL}/auth/logout`, { method: 'POST' });
  }

  // ==================== USUARIOS ====================
  static async getUsuarios(q = '') {
    const url = q ? `${API_URL}/usuarios?q=${q}` : `${API_URL}/usuarios`;
    const res = await fetch(url);
    return res.json();
  }

  static async getUsuario(id) {
    const res = await fetch(`${API_URL}/usuarios/${id}`);
    return res.json();
  }

  static async createUsuario(usuario) {
    const res = await fetch(`${API_URL}/usuarios`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(usuario)
    });
    return handleResponse(res);
  }

  static async updateUsuario(id, usuario) {
    const res = await fetch(`${API_URL}/usuarios/${id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(usuario)
    });
    return handleResponse(res);
  }

  static async changePassword(id, pass) {
    return fetch(`${API_URL}/usuarios/${id}/password?pass=${pass}`, { method: 'PATCH' });
  }

  static async toggleUsuario(id) {
    return fetch(`${API_URL}/usuarios/${id}/toggle`, { method: 'PATCH' });
  }

  static async deleteUsuario(id) {
    return fetch(`${API_URL}/usuarios/${id}`, { method: 'DELETE' });
  }

  // ==================== PRODUCTOS ====================
  static async getProductos(q = '') {
    const url = q ? `${API_URL}/productos?q=${q}` : `${API_URL}/productos`;
    const res = await fetch(url);
    return res.json();
  }

  static async getProducto(id) {
    const res = await fetch(`${API_URL}/productos/${id}`);
    return res.json();
  }

  static async createProducto(producto) {
    const res = await fetch(`${API_URL}/productos`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(producto)
    });
    return handleResponse(res);
  }

  static async updateProducto(id, producto) {
    const res = await fetch(`${API_URL}/productos/${id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(producto)
    });
    return handleResponse(res);
  }

  static async deleteProducto(id) {
    return fetch(`${API_URL}/productos/${id}`, { method: 'DELETE' });
  }

  static async getProductosStockBajo() {
    const res = await fetch(`${API_URL}/productos/stock-bajo`);
    return res.json();
  }

  static async getProductosPorVencer() {
    const res = await fetch(`${API_URL}/productos/por-vencer`);
    return res.json();
  }

  // ==================== VENTAS ====================
  static async getVentas(filtro = '', q = '') {
    const params = new URLSearchParams();
    if (filtro) params.append('filtro', filtro);
    if (q) params.append('q', q);
    const url = params.toString() ? `${API_URL}/ventas?${params}` : `${API_URL}/ventas`;
    const res = await fetch(url);
    return res.json();
  }

  static async getVenta(id) {
    const res = await fetch(`${API_URL}/ventas/${id}`);
    return res.json();
  }

  static async createVenta(venta) {
    const res = await fetch(`${API_URL}/ventas`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(venta)
    });
    return handleResponse(res);
  }

  static async deleteVenta(id) {
    return fetch(`${API_URL}/ventas/${id}`, { method: 'DELETE' });
  }

  static async getVentasTotal(desde = '', hasta = '') {
    const params = new URLSearchParams();
    if (desde) params.append('desde', desde);
    if (hasta) params.append('hasta', hasta);
    const url = `${API_URL}/ventas/stats/total?${params}`;
    const res = await fetch(url);
    return res.json();
  }

  // ==================== COMPRAS ====================
  static async getCompras(filtro = '', q = '') {
    const params = new URLSearchParams();
    if (filtro) params.append('filtro', filtro);
    if (q) params.append('q', q);
    const url = params.toString() ? `${API_URL}/compras?${params}` : `${API_URL}/compras`;
    const res = await fetch(url);
    return res.json();
  }

  static async getCompra(id) {
    const res = await fetch(`${API_URL}/compras/${id}`);
    return res.json();
  }

  static async createCompra(compra) {
    const res = await fetch(`${API_URL}/compras`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(compra)
    });
    return handleResponse(res);
  }

  static async deleteCompra(id) {
    return fetch(`${API_URL}/compras/${id}`, { method: 'DELETE' });
  }

  static async getComprasTotal(desde = '', hasta = '') {
    const params = new URLSearchParams();
    if (desde) params.append('desde', desde);
    if (hasta) params.append('hasta', hasta);
    const url = `${API_URL}/compras/stats/total?${params}`;
    const res = await fetch(url);
    return res.json();
  }

  // ==================== DASHBOARD ====================
  static async getDashboardStats() {
    const res = await fetch(`${API_URL}/dashboard/stats`);
    return res.json();
  }

  // ==================== ALERTAS ====================
  static async getAlertas() {
    const res = await fetch(`${API_URL}/alertas`);
    return res.json();
  }

  static async getAlertasCount() {
    const res = await fetch(`${API_URL}/alertas/count`);
    return res.text();
  }

  static async getStockBajo() {
    const res = await fetch(`${API_URL}/alertas/stock-bajo`);
    return res.json();
  }

  static async getPorVencer() {
    const res = await fetch(`${API_URL}/alertas/por-vencer`);
    return res.json();
  }

  static async getCaducados() {
    const res = await fetch(`${API_URL}/alertas/caducados`);
    return res.json();
  }

  // ==================== KARDEX ====================
  static async getKardex(q = '') {
    const url = q ? `${API_URL}/kardex?q=${q}` : `${API_URL}/kardex`;
    const res = await fetch(url);
    return res.json();
  }

  static async getKardexProducto(productoId) {
    const res = await fetch(`${API_URL}/kardex/producto/${productoId}`);
    return res.json();
  }

  // ==================== PERMISOS ====================
  static async getPermisosPorRol(rol) {
    const res = await fetch(`${API_URL}/permisos/rol/${rol}`);
    return res.json();
  }

  static async tienePermiso(rol, modulo, tipo) {
    const res = await fetch(`${API_URL}/permisos/check?rol=${rol}&modulo=${modulo}&tipo=${tipo}`);
    return res.json();
  }
}
