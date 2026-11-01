package com.shop.ecommerce.api;
import com.shop.ecommerce.model.*;import com.shop.ecommerce.repo.*;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/admin") public class AdminController {
 private final ProductRepository products;private final OrderRepository orders;private final UserRepository users;private final CouponRepository coupons;private final ContactRepository contacts;
 public AdminController(ProductRepository p,OrderRepository o,UserRepository u,CouponRepository c,ContactRepository m){products=p;orders=o;users=u;coupons=c;contacts=m;}
 @GetMapping("/dashboard") public Map<String,Object> dashboard(){return Map.of("products",products.count(),"orders",orders.count(),"users",users.count(),"messages",contacts.count());}
 @GetMapping("/orders") public List<ShopOrder> orders(){return orders.findAll();}
 @PutMapping("/orders/{id}/status") public ShopOrder status(@PathVariable Long id,@RequestBody Map<String,String> input){ShopOrder o=orders.findById(id).orElseThrow(ApiSupport::missing);String status=input.get("status");if(!List.of("PLACED","CONFIRMED","PACKED","SHIPPED","DELIVERED").contains(status))throw ApiSupport.bad("Invalid order status");if("CANCELLED".equals(o.status))throw ApiSupport.bad("Cancelled order");o.status=status;return orders.save(o);}
 @PostMapping("/products") public Product add(@RequestBody Product p){p.id=null;return validateAndSave(p);}
 @PutMapping("/products/{id}") public Product update(@PathVariable Long id,@RequestBody Product p){Product current=products.findById(id).orElseThrow(ApiSupport::missing);current.name=p.name;current.description=p.description;current.category=p.category;current.subCategory=p.subCategory;current.price=p.price;current.originalPrice=p.originalPrice;current.imageUrl=p.imageUrl;current.sizes=p.sizes;current.stock=p.stock;current.active=p.active;current.featured=p.featured;return validateAndSave(current);}
 private Product validateAndSave(Product p){if(p.name==null||p.name.isBlank()||p.price==null||p.price.signum()<0||p.stock<0)throw ApiSupport.bad("Invalid product");return products.save(p);}
 @DeleteMapping("/products/{id}") public void disable(@PathVariable Long id){Product p=products.findById(id).orElseThrow(ApiSupport::missing);p.active=false;products.save(p);}
 @GetMapping("/coupons") public List<Coupon> coupons(){return coupons.findAll();}
 @PostMapping("/coupons") public Coupon coupon(@RequestBody Coupon coupon){coupon.id=null;if(coupon.code==null||coupon.code.isBlank()||coupon.discountPercent==null||coupon.discountPercent<1||coupon.discountPercent>100)throw ApiSupport.bad("Invalid coupon");return coupons.save(coupon);}
 @GetMapping("/users") public List<Map<String,Object>> users(){return users.findAll().stream().map(u->Map.<String,Object>of("id",u.id,"name",u.name,"email",u.email,"role",u.role,"enabled",u.enabled)).toList();}
 @PutMapping("/users/{id}/enabled") public void toggle(@PathVariable Long id,@RequestBody Map<String,Boolean> body){User u=users.findById(id).orElseThrow(ApiSupport::missing);u.enabled=Boolean.TRUE.equals(body.get("enabled"));users.save(u);}
 @GetMapping("/messages") public List<ContactMessage> messages(){return contacts.findAll();}
}
