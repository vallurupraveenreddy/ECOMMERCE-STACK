package com.shop.ecommerce.api;
import com.shop.ecommerce.model.*;import com.shop.ecommerce.repo.*;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/reviews") public class ReviewController {
 private final ApiSupport api;private final ReviewRepository reviews;private final ProductRepository products;private final OrderRepository orders;
 public ReviewController(ApiSupport a,ReviewRepository r,ProductRepository p,OrderRepository o){api=a;reviews=r;products=p;orders=o;}
 @GetMapping("/product/{id}") public List<Review> all(@PathVariable Long id){return reviews.findByProductId(id);}
 public record Input(int rating,String comment){}
 @PostMapping("/product/{id}") public Review post(@PathVariable Long id,@RequestBody Input body){User user=api.user();if(body.rating()<1||body.rating()>5)throw ApiSupport.bad("Rating must be 1-5");boolean purchased=orders.findByUserIdOrderByCreatedAtDesc(user.id).stream().anyMatch(o->o.items.stream().anyMatch(i->i.productId.equals(id)));if(!purchased)throw ApiSupport.bad("Only customers who ordered this item may review it");Review r=reviews.findByUserIdAndProductId(user.id,id).orElseGet(Review::new);r.user=user;r.product=products.findById(id).orElseThrow(ApiSupport::missing);r.rating=body.rating();r.comment=body.comment();return reviews.save(r);}
}
