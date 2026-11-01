const BASE = import.meta.env.VITE_API_URL || 'http://localhost:8080/api';
export async function request(path, options = {}) {
  const token = sessionStorage.getItem('auth_token');
  const response = await fetch(`${BASE}${path}`, {
    ...options,
    headers: { 'Content-Type': 'application/json', ...(token ? {Authorization: `Bearer ${token}`} : {}), ...options.headers }
  });
  const data = await response.json().catch(() => ({}));
  if (!response.ok) throw new Error(data.error || data.message || `Request failed (${response.status})`);
  return data;
}
export const authApi = {
  login: (email, password) => request('/auth/login', {method:'POST',body:JSON.stringify({email,password})}),
  register: (name,email,password) => request('/auth/register', {method:'POST',body:JSON.stringify({name,email,password})}),
  me: () => request('/auth/me'),
  logout: () => {sessionStorage.removeItem('auth_token');sessionStorage.removeItem('auth_user');['shop_cart','shop_orders','shop_wishlist','auth_user','auth_token'].forEach(k=>localStorage.removeItem(k));}
};
export const productsApi = {list: (query = '') => request('/products'+query),detail: (id) => request(`/products/${id}`)};
export const cartApi = {list: () => request('/cart'),set: (productId,size,quantity) => request('/cart',{method:'PUT',body:JSON.stringify({productId,size,quantity})}),clear: () => request('/cart',{method:'DELETE'})};
export const ordersApi = {list: () => request('/orders'),place: (addressId,couponCode) => request('/orders',{method:'POST',body:JSON.stringify({addressId,couponCode,paymentMethod:'COD'})})};
export const wishlistApi = {list: () => request('/wishlist'),add: (id) => request(`/wishlist/${id}`,{method:'POST'}),remove: (id) => request(`/wishlist/${id}`,{method:'DELETE'})};
