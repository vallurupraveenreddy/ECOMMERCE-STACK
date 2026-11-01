package com.shop.ecommerce.api;
import com.shop.ecommerce.model.*;import com.shop.ecommerce.repo.*;
import org.springframework.web.bind.annotation.*;import org.springframework.data.domain.*;
import java.util.*;
@RestController @RequestMapping("/api/products") public class ProductController {
 private final ProductRepository products;private final ReviewRepository reviews;
 public ProductController(ProductRepository p,ReviewRepository r){products=p;reviews=r;}
 @GetMapping public Page<Product> list(@RequestParam(defaultValue="") String q,@RequestParam(defaultValue="") String category,@RequestParam(defaultValue="newest") String sort,@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="12") int size){
  if(page<0||size<1||size>100)throw ApiSupport.bad("Invalid pagination");
  Sort s=switch(sort){case "price-asc"->Sort.by("price").ascending();case "price-desc"->Sort.by("price").descending();default->Sort.by("id").descending();};
  return products.findAll((root,query,cb)->{var predicate=cb.isTrue(root.get("active"));if(!q.isBlank())predicate=cb.and(predicate,cb.like(cb.lower(root.get("name")),"%"+q.toLowerCase()+"%"));if(!category.isBlank())predicate=cb.and(predicate,cb.equal(cb.lower(root.get("category")),category.toLowerCase()));return predicate;},PageRequest.of(page,size,s));
 }
 @GetMapping("/{id}") public Product detail(@PathVariable Long id){Product p=products.findById(id).filter(x->x.active).orElseThrow(ApiSupport::missing);return p;}
 @GetMapping("/{id}/rating") public Map<String,Object> rating(@PathVariable Long id){var list=reviews.findByProductId(id);return Map.of("count",list.size(),"average",list.stream().mapToInt(r->r.rating).average().orElse(0));}
}
