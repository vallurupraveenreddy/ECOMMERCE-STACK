import React,{useState,useEffect,useRef,useContext} from 'react';
import {assets} from '../assets/frontend_assets/assets';
import {Link,NavLink,useNavigate} from 'react-router-dom';
import {ShopContext} from '../context/ShopContext';
import {useAuth} from '../context/AuthContext';
export default function Navbar(){
 const [mobile,setMobile]=useState(false),[open,setOpen]=useState(false);
 const menuRef=useRef(null),nav=useNavigate();
 const {setShowSearch,getCartCount,getWishlistCount}=useContext(ShopContext);
 const {user,logout}=useAuth();
 useEffect(()=>{const close=(e)=>{if(menuRef.current&&!menuRef.current.contains(e.target))setOpen(false)};const esc=e=>{if(e.key==='Escape')setOpen(false)};document.addEventListener('pointerdown',close);document.addEventListener('keydown',esc);return()=>{document.removeEventListener('pointerdown',close);document.removeEventListener('keydown',esc)}},[]);
 const links=[['/','HOME'],['/collection','COLLECTION'],['/deals','DEALS 🔥'],['/about','ABOUT'],['/wishlist','WISHLIST ♡']];
 const go=(path)=>{setOpen(false);setMobile(false);nav(path)};
 return <header className="flex items-center justify-between gap-3 py-4 sm:py-6 font-medium relative z-30 border-b border-slate-100">
  <Link to="/" aria-label="Go home"><img src={assets.logo} className="w-28 sm:w-32" alt="Store"/></Link>
  <nav className="hidden md:flex items-center gap-4 lg:gap-7 text-xs lg:text-sm tracking-wide">{links.map(([path,label])=><NavLink key={path} to={path} end={path==='/'} className={({isActive})=>`border-b-2 py-1 ${isActive?'border-zinc-950 font-semibold':'border-transparent hover:border-gray-400'}`}>{label}</NavLink>)}</nav>
  <div className="flex items-center gap-3 sm:gap-5">
   <button onClick={()=>{setShowSearch(true);go('/collection')}} aria-label="Search"><img className="w-5" src={assets.search_icon} alt=""/></button>
   <Link to="/cart" className="relative" aria-label="Shopping cart"><img src={assets.cart_icon} className="w-5" alt=""/><span className="absolute -right-2 -bottom-2 bg-black text-white rounded-full px-1 text-[10px]">{getCartCount()}</span></Link>
   <div className="relative" ref={menuRef}>
    <button className="flex items-center gap-1 rounded-lg p-2 hover:bg-slate-100" onClick={()=>setOpen(v=>!v)} aria-expanded={open} aria-label="Account options"><img className="w-5" src={assets.profile_icon} alt=""/>{user&&<span className="hidden lg:inline text-xs max-w-24 truncate">{user.name?.split(' ')[0]}</span>}</button>
    {open&&<div className="absolute right-0 top-full mt-3 w-64 rounded-2xl border border-slate-200 bg-white shadow-xl p-2 text-sm z-50">
     {user?<><div className="px-3 py-3 border-b"><p className="font-semibold truncate">{user.name}</p><p className="text-xs text-gray-500 truncate">{user.email}</p></div>
      <button className="account-item" onClick={()=>go('/orders')}>📦 My Orders</button>
      <button className="account-item" onClick={()=>go('/profile')}>👤 My Profile</button>
      <button className="account-item" onClick={()=>go('/wishlist')}>♡ Wishlist</button></>:<><button className="account-item font-semibold" onClick={()=>go('/login')}>Sign in / Create account</button></>}
     {user&&<button className="account-item text-red-600" onClick={()=>{logout();go('/')}}>↪ Logout</button>}
    </div>}
   </div>
   <button onClick={()=>setMobile(v=>!v)} className="md:hidden" aria-label="Toggle navigation">☰</button>
  </div>
  {mobile&&<div className="absolute right-0 top-full w-full bg-white border border-slate-200 shadow-lg md:hidden p-4 flex flex-col gap-3">{links.map(([path,label])=><button key={path} className="text-left" onClick={()=>go(path)}>{label}</button>)}{user?<><button className="text-left" onClick={()=>go('/orders')}>My orders</button><button className="text-left" onClick={()=>go('/profile')}>My profile</button></>:<button className="text-left" onClick={()=>go('/login')}>Sign in</button>}</div>}
 </header>
}
