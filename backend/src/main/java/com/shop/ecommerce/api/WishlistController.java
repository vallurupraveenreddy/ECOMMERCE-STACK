package com.shop.ecommerce.api;
import com.shop.ecommerce.model.*;import com.shop.ecommerce.repo.*;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/wishlist") public class WishlistController {
 private final ApiSupport api;private final WishlistRepository repo;private final ProductRepository products;
 public WishlistController(ApiSupport a,WishlistRepository w,ProductRepository p){api=a;repo=w;products=p;}
 @GetMapping public List<WishlistItem> all(){return repo.findByUserId(api.user().id);}
 @PostMapping("/{id}") public List<WishlistItem> add(@PathVariable Long id){User u=api.user();Product p=products.findById(id).orElseThrow(ApiSupport::missing);if(repo.findByUserIdAndProductId(u.id,id).isEmpty()){WishlistItem w=new WishlistItem();w.user=u;w.product=p;repo.save(w);}return all();}
 @DeleteMapping("/{id}") public List<WishlistItem> delete(@PathVariable Long id){repo.findByUserIdAndProductId(api.user().id,id).ifPresent(repo::delete);return all();}
}
