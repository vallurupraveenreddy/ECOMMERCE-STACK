package com.shop.ecommerce.security;
import com.shop.ecommerce.repo.UserRepository;import jakarta.servlet.*;import jakarta.servlet.http.*;
import org.springframework.context.annotation.*;import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.*;import org.springframework.web.filter.OncePerRequestFilter;
import java.util.List;
@Configuration public class SecurityConfig {
 @Bean PasswordEncoder encoder(){return new BCryptPasswordEncoder();}
 @Bean SecurityFilterChain filters(HttpSecurity http,JwtService jwt,UserRepository users,@Value("${app.cors.origin}") String origin) throws Exception {
  OncePerRequestFilter auth=new OncePerRequestFilter(){@Override protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain chain)throws java.io.IOException,ServletException{
    String h=request.getHeader("Authorization");if(h!=null&&h.startsWith("Bearer "))try{String email=jwt.subject(h.substring(7));users.findByEmailIgnoreCase(email).filter(u->u.enabled).ifPresent(u->SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(u.email,null,List.of(new SimpleGrantedAuthority("ROLE_"+u.role)))));}catch(Exception ignored){}chain.doFilter(request,response);
  }};
  CorsConfiguration cors=new CorsConfiguration();cors.setAllowedOrigins(List.of(origin));cors.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));cors.setAllowedHeaders(List.of("*"));
  return http.csrf(c->c.disable()).cors(c->c.configurationSource(req->cors)).sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).authorizeHttpRequests(a->a.requestMatchers("/api/auth/**","/api/products/**","/api/reviews/product/**","/api/coupons/check","/api/contact","/actuator/health","/error").permitAll().requestMatchers("/api/admin/**").hasRole("ADMIN").anyRequest().authenticated()).addFilterBefore(auth,UsernamePasswordAuthenticationFilter.class).build();
 }
}
