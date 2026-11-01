package com.shop.ecommerce.api;
import com.shop.ecommerce.model.Coupon;import com.shop.ecommerce.repo.CouponRepository;import org.springframework.web.bind.annotation.*;import java.time.Instant;import java.math.BigDecimal;
@RestController @RequestMapping("/api/coupons") public class CouponController {
 private final CouponRepository repo;public CouponController(CouponRepository r){repo=r;}
 @GetMapping("/check") public Coupon check(@RequestParam String code,@RequestParam BigDecimal subtotal){Coupon c=repo.findByCodeIgnoreCase(code).filter(x->x.active&&(x.expiresAt==null||x.expiresAt.isAfter(Instant.now()))).orElseThrow(()->ApiSupport.bad("Invalid or expired coupon"));if(subtotal.compareTo(c.minimumOrder)<0)throw ApiSupport.bad("Minimum cart amount not met");return c;}
 public Coupon valid(String code,BigDecimal subtotal){return check(code,subtotal);}
}
