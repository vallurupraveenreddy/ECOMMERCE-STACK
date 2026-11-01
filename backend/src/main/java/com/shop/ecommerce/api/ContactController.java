package com.shop.ecommerce.api;
import com.shop.ecommerce.model.ContactMessage;import com.shop.ecommerce.repo.ContactRepository;import jakarta.validation.Valid;import jakarta.validation.constraints.*;import org.springframework.web.bind.annotation.*;import java.util.Map;
@RestController @RequestMapping("/api/contact") public class ContactController {
 private final ContactRepository repo;public ContactController(ContactRepository r){repo=r;}
 public record Input(@NotBlank String name,@Email @NotBlank String email,@NotBlank String message){}
 @PostMapping public Map<String,String> send(@Valid @RequestBody Input input){ContactMessage m=new ContactMessage();m.name=input.name();m.email=input.email();m.message=input.message();repo.save(m);return Map.of("message","Message received");}
}
