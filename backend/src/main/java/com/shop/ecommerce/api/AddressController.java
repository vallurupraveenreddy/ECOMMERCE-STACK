package com.shop.ecommerce.api;
import com.shop.ecommerce.model.Address;import com.shop.ecommerce.repo.AddressRepository;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/addresses") public class AddressController {
 private final ApiSupport api;private final AddressRepository repo;public AddressController(ApiSupport a,AddressRepository r){api=a;repo=r;}
 @GetMapping public List<Address> all(){return repo.findByUserId(api.user().id);}
 public record Input(String fullName,String phone,String line1,String line2,String city,String state,String postalCode,String country,boolean isDefault){}
 private Address apply(Address a,Input i){if(i.fullName()==null||i.fullName().isBlank()||i.line1()==null||i.line1().isBlank()||i.city()==null||i.city().isBlank()||i.postalCode()==null||i.postalCode().isBlank())throw ApiSupport.bad("Missing address fields");a.fullName=i.fullName();a.phone=i.phone();a.line1=i.line1();a.line2=i.line2();a.city=i.city();a.state=i.state();a.postalCode=i.postalCode();a.country=i.country()==null?"India":i.country();a.isDefault=i.isDefault();return a;}
 @PostMapping public Address create(@RequestBody Input i){Address a=new Address();a.user=api.user();return repo.save(apply(a,i));}
 @PutMapping("/{id}") public Address update(@PathVariable Long id,@RequestBody Input i){return repo.save(apply(repo.findByIdAndUserId(id,api.user().id).orElseThrow(ApiSupport::missing),i));}
 @DeleteMapping("/{id}") public void delete(@PathVariable Long id){repo.delete(repo.findByIdAndUserId(id,api.user().id).orElseThrow(ApiSupport::missing));}
}
