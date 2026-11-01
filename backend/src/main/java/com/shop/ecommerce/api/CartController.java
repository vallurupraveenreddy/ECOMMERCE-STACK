package com.shop.ecommerce.api;
import com.shop.ecommerce.model.*;import com.shop.ecommerce.repo.*;import org.springframework.web.bind.annotation.*;import org.springframework.transaction.annotation.Transactional;import java.util.*;
@RestController @RequestMapping("/api/cart") public class CartController {
 private final ApiSupport api;private final CartRepository carts;private final ProductRepository products;
 public CartController(ApiSupport a,CartRepository c,ProductRepository p){api=a;carts=c;products=p;}
 @GetMapping public List<CartItem> get(){return carts.findByUserId(api.user().id);}
 public record Input(Long productId,String size,int quantity){}
 @PutMapping @Transactional public List<CartItem> put(@RequestBody Input input){User u=api.user();Product p=products.findById(input.productId()).filter(x->x.active).orElseThrow(ApiSupport::missing);if(input.size()==null||input.size().isBlank()||!Arrays.asList(p.sizes==null?new String[0]:p.sizes.split(",")).contains(input.size()))throw ApiSupport.bad("Invalid size");if(input.quantity()<0||input.quantity()>99)throw ApiSupport.bad("Quantity must be between 0 and 99");var found=carts.findByUserIdAndProductIdAndSize(u.id,p.id,input.size());if(input.quantity()==0)found.ifPresent(carts::delete);else{if(input.quantity()>p.stock)throw ApiSupport.bad("Insufficient stock");CartItem item=found.orElseGet(()->{CartItem x=new CartItem();x.user=u;x.product=p;x.size=input.size();return x;});item.quantity=input.quantity();carts.save(item);}return get();}
 @DeleteMapping("/{id}") public void delete(@PathVariable Long id){CartItem item=carts.findById(id).orElseThrow(ApiSupport::missing);if(!item.user.id.equals(api.user().id))throw ApiSupport.missing();carts.delete(item);}
 @DeleteMapping @Transactional public void clear(){carts.deleteByUserId(api.user().id);}
}
