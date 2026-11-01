package com.shop.ecommerce.model;
import jakarta.persistence.*;import java.math.BigDecimal;import java.time.Instant;
@Entity public class Coupon {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @Column(nullable=false,unique=true) public String code;
 @Column(nullable=false) public Integer discountPercent;
 public BigDecimal minimumOrder=BigDecimal.ZERO;
 public BigDecimal maxDiscount;public Instant expiresAt;public boolean active=true;
}
