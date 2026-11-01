package com.shop.ecommerce.api;
import com.shop.ecommerce.model.User;import com.shop.ecommerce.repo.UserRepository;import com.shop.ecommerce.security.JwtService;
import jakarta.validation.Valid;import jakarta.validation.constraints.*;
import org.springframework.security.crypto.password.PasswordEncoder;import org.springframework.web.bind.annotation.*;
import java.util.Map;import java.util.LinkedHashMap;
@RestController @RequestMapping("/api/auth") public class AuthController {
 private final UserRepository users;private final PasswordEncoder passwords;private final JwtService jwt;private final ApiSupport api;
 public AuthController(UserRepository users,PasswordEncoder passwords,JwtService jwt,ApiSupport api){this.users=users;this.passwords=passwords;this.jwt=jwt;this.api=api;}
 public record Credentials(@NotBlank @Email String email,@NotBlank String password){}
 public record Signup(@NotBlank String name,@Email @NotBlank String email,@Size(min=8) String password){}
 private Map<String,Object> token(User u){return Map.of("token",jwt.issue(u.email),"user",Map.of("id",u.id,"name",u.name,"email",u.email,"role",u.role));}
 @PostMapping("/register") public Map<String,Object> register(@Valid @RequestBody Signup r){if(users.findByEmailIgnoreCase(r.email()).isPresent())throw ApiSupport.bad("Email already registered");User u=new User();u.name=r.name();u.email=r.email().trim().toLowerCase();u.passwordHash=passwords.encode(r.password());return token(users.save(u));}
 @PostMapping("/login") public Map<String,Object> login(@Valid @RequestBody Credentials r){User u=users.findByEmailIgnoreCase(r.email()).orElseThrow(()->ApiSupport.bad("Invalid credentials"));if(!u.enabled||!passwords.matches(r.password(),u.passwordHash))throw ApiSupport.bad("Invalid credentials");return token(u);}
 @GetMapping("/me") public Map<String,Object> me(){User u=api.user();Map<String,Object> result=new LinkedHashMap<>();result.put("id",u.id);result.put("name",u.name);result.put("email",u.email);result.put("role",u.role);result.put("phone",u.phone);result.put("address",u.address);result.put("city",u.city);result.put("state",u.state);result.put("zipcode",u.zipcode);result.put("country",u.country);result.put("dob",u.dob);result.put("gender",u.gender);return result;}
 @PutMapping("/me") public Map<String,Object> update(@RequestBody Map<String,String> payload){User u=api.user();String name=payload.getOrDefault("name","").trim();if(name.isBlank())throw ApiSupport.bad("Name required");u.name=name;u.phone=clean(payload.get("phone"),30);u.address=clean(payload.get("address"),255);u.city=clean(payload.get("city"),100);u.state=clean(payload.get("state"),100);u.zipcode=clean(payload.get("zipcode"),30);u.country=clean(payload.get("country"),100);u.dob=clean(payload.get("dob"),20);u.gender=clean(payload.get("gender"),30);users.save(u);return me();}
 private static String clean(String val,int max){if(val==null)return null;val=val.trim();if(val.length()>max)throw ApiSupport.bad("Field too long");return val;}
 @PutMapping("/password") public Map<String,String> password(@RequestBody Map<String,String> body){User u=api.user();if(!passwords.matches(body.getOrDefault("currentPassword",""),u.passwordHash))throw ApiSupport.bad("Wrong current password");String next=body.getOrDefault("newPassword","");if(next.length()<8)throw ApiSupport.bad("Password must contain at least 8 characters");u.passwordHash=passwords.encode(next);users.save(u);return Map.of("message","Password changed");}
}
