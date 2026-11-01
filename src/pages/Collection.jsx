import {useContext,useMemo,useState} from 'react';
import {Link,useSearchParams} from 'react-router-dom';
import {ShopContext} from '../context/ShopContext';
import ProductItem from '../components/ProductItem';

const categories=['Men','Women','Kids'];
const types=['Topwear','Bottomwear','Winterwear'];
export default function Collection(){
 const {products,search,setSearch}=useContext(ShopContext);
 const [params,setParams]=useSearchParams();
 const [showFilter,setShowFilter]=useState(false);
 const category=params.getAll('category'),type=params.getAll('type');
 const sort=params.get('sort')||'relevant',max=useMemo(()=>Math.ceil(Math.max(...products.map(p=>p.price),100)),[products]);
 const low=Math.max(0,Number(params.get('min'))||0),high=Math.min(max,Number(params.get('max'))||max);
 const update=(key,val,multi=false)=>{const next=new URLSearchParams(params);if(multi){const values=next.getAll(key);next.delete(key);values.filter(v=>v!==val).concat(values.includes(val)?[]:[val]).forEach(v=>next.append(key,v));}else if(val==null||val===''||val==='relevant')next.delete(key);else next.set(key,String(val));setParams(next)};
 const filtered=useMemo(()=>{const list=products.filter(p=>(!search||p.name.toLowerCase().includes(search.toLowerCase()))&&(!category.length||category.includes(p.category))&&(!type.length||type.includes(p.subCategory))&&p.price>=low&&p.price<=high);if(sort==='low-high')list.sort((a,b)=>a.price-b.price);if(sort==='high-low')list.sort((a,b)=>b.price-a.price);if(sort==='newest')list.reverse();return list},[products,search,params.toString()]);
 const clear=()=>{setParams({});setSearch('')};
 return <main className="border-t border-zinc-200 dark:border-zinc-800 pt-8 pb-16">
  <div className="mb-7 flex flex-col gap-3 sm:flex-row sm:items-end sm:justify-between"><div><span className="text-xs font-bold uppercase tracking-[.22em] text-amber-700 dark:text-amber-300">Curated for you</span><h1 className="text-3xl sm:text-4xl font-semibold mt-2">Shop the collection</h1><p className="mt-2 text-zinc-600 dark:text-zinc-300">Discover pieces you'll love · {filtered.length} products</p></div><Link className="text-sm font-semibold underline underline-offset-4" to="/deals">See current deals ↗</Link></div>
  <div className="flex flex-col lg:flex-row gap-6 lg:gap-9">
   <aside className="lg:w-64 shrink-0"><button type="button" onClick={()=>setShowFilter(v=>!v)} aria-expanded={showFilter} className="shop-control w-full flex justify-between items-center lg:hidden font-semibold mb-3">☷ Filters <span>{showFilter?'−':'+'}</span></button>
    <div className={`${showFilter?'block':'hidden'} lg:block rounded-2xl border border-zinc-200 dark:border-zinc-700 p-5 bg-white dark:bg-zinc-900`}><div className="flex justify-between items-center mb-5"><h2 className="font-semibold">Filters</h2><button onClick={clear} className="text-sm underline text-amber-800 dark:text-amber-300">Clear all</button></div>
    {[['Category',categories,'category',category],['Type',types,'type',type]].map(([label,values,key,selected])=><fieldset key={key} className="mb-6"><legend className="font-semibold text-sm mb-3">{label}</legend><div className="grid gap-3">{values.map(v=><label key={v} className="flex items-center gap-3 text-sm cursor-pointer"><input type="checkbox" className="h-4 w-4 accent-zinc-900 dark:accent-amber-400" checked={selected.includes(v)} onChange={()=>update(key,v,true)}/><span>{v}</span></label>)}</div></fieldset>)}
    <fieldset className="border-t border-zinc-200 dark:border-zinc-700 pt-4"><legend className="font-semibold text-sm">Price range</legend><div className="flex gap-3 mt-3"><label className="text-xs flex-1">Min<input className="shop-control mt-1 w-full" type="number" min="0" max={high} value={low} onChange={e=>update('min',e.target.value)}/></label><label className="text-xs flex-1">Max<input className="shop-control mt-1 w-full" type="number" min={low} value={high} onChange={e=>update('max',e.target.value)}/></label></div></fieldset></div>
   </aside>
   <section className="min-w-0 flex-1"><div className="flex flex-col sm:flex-row gap-3 sm:items-center mb-5"><label className="flex-1"><span className="sr-only">Search products</span><input className="shop-control w-full" placeholder="Search styles, outfits and more…" value={search} onChange={e=>setSearch(e.target.value)}/></label><label className="flex items-center gap-2 text-sm font-semibold"><span className="whitespace-nowrap">Sort by</span><select className="shop-control min-w-44 flex-1" aria-label="Sort products" value={sort} onChange={e=>update('sort',e.target.value)}><option value="relevant">Featured</option><option value="low-high">Price: low to high</option><option value="high-low">Price: high to low</option><option value="newest">Latest added</option></select></label></div>
   <div className="flex flex-wrap gap-2 mb-5">{[...category,...type].map(v=><span key={v} className="rounded-full border border-zinc-300 dark:border-zinc-600 px-3 py-1 text-xs">{v} <button aria-label={`Remove ${v} filter`} onClick={()=>update(category.includes(v)?'category':'type',v,true)}>×</button></span>)}{(params.toString()||search)&&<button className="text-xs underline px-2" onClick={clear}>Reset filters</button>}</div>
   {filtered.length?<div className="grid grid-cols-2 sm:grid-cols-3 xl:grid-cols-4 gap-3 sm:gap-5 gap-y-8">{filtered.map(p=><ProductItem key={p._id} id={p._id} image={p.image} name={p.name} price={p.price}/>)}</div>:<div className="text-center rounded-2xl bg-zinc-50 dark:bg-zinc-900 p-14"><h3 className="text-xl font-semibold">No matches found</h3><p className="text-zinc-600 dark:text-zinc-300 my-3">Try different keywords or filters.</p><button className="shop-control" onClick={clear}>Reset all filters</button></div>}
   </section>
  </div>
 </main>
}
