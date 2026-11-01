package com.shop.ecommerce.repo;
import com.shop.ecommerce.model.Coupon;import org.springframework.data.jpa.repository.JpaRepository;import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
public interface CouponRepository extends JpaRepository<Coupon,Long> { java.util.Optional<Coupon> findByCodeIgnoreCase(String code); }
