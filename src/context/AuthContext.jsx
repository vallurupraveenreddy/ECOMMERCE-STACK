import {createContext,useContext,useEffect,useState} from 'react';
import {authApi} from '../api/client';
const AuthContext=createContext(null);
const stored=()=>{try{return JSON.parse(sessionStorage.getItem('auth_user')||'null')}catch{return null}};
export function AuthProvider({children}){
 useEffect(()=>{['auth_token','auth_user','shop_cart','shop_orders','shop_wishlist'].forEach(k=>localStorage.removeItem(k))},[]);
 const [user,setUser]=useState(stored);
 const [loading,setLoading]=useState(Boolean(sessionStorage.getItem('auth_token')));
 useEffect(()=>{let active=true; if(!sessionStorage.getItem('auth_token')){setLoading(false);return} authApi.me().then(data=>{if(active){setUser(data);sessionStorage.setItem('auth_user',JSON.stringify(data))}}).catch(()=>{if(active){authApi.logout();setUser(null)}}).finally(()=>{if(active)setLoading(false)});return()=>{active=false}},[]);
 const login=(result)=>{window.dispatchEvent(new Event('shop:session-ended'));sessionStorage.setItem('auth_token',result.token);sessionStorage.setItem('auth_user',JSON.stringify(result.user));setUser(result.user)};
 const logout=()=>{authApi.logout();setUser(null);window.dispatchEvent(new Event('shop:session-ended'))};
 const refresh=async()=>{const data=await authApi.me();sessionStorage.setItem('auth_user',JSON.stringify(data));setUser(data);return data};
 return <AuthContext.Provider value={{user,loading,login,logout,refresh,setUser}}>{children}</AuthContext.Provider>;
}
export const useAuth=()=>useContext(AuthContext);
