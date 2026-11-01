package com.shop.ecommerce.api;
import com.shop.ecommerce.model.User;import com.shop.ecommerce.repo.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;import org.springframework.http.HttpStatus;import org.springframework.web.server.ResponseStatusException;
import org.springframework.stereotype.Component;
@Component public class ApiSupport {
 private final UserRepository users;public ApiSupport(UserRepository users){this.users=users;}
 public User user(){var a=SecurityContextHolder.getContext().getAuthentication();if(a==null||!a.isAuthenticated())throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);return users.findByEmailIgnoreCase(a.getName()).filter(u->u.enabled).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED));}
 public static ResponseStatusException bad(String msg){return new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);}
 public static ResponseStatusException missing(){return new ResponseStatusException(HttpStatus.NOT_FOUND,"Not found");}
}
