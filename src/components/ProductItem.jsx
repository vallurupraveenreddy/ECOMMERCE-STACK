import React, { useContext } from "react";
import { ShopContext } from "../context/ShopContext";
import { Link } from "react-router-dom";

function ProductItem({ id, image, name, price }) {
  const { currency, addToCart } = useContext(ShopContext);

  return (
    <Link className="group text-zinc-800 dark:text-zinc-100 cursor-pointer block min-w-0" to={`/product/${id}`}>
      <div className="overflow-hidden rounded-xl bg-zinc-100 dark:bg-zinc-800 aspect-[3/4]">
        <img
          loading="lazy" className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300"
          src={image[0]}
          alt={name}
        />
      </div>
      <p className="pt-3 pb-1 text-sm">{name}</p>
      <p className="text-sm font-semibold">
        {currency}
        {price}
      </p>
    </Link>
  );
}

export default ProductItem;
