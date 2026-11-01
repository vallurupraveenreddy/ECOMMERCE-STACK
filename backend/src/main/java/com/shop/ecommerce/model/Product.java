package com.shop.ecommerce.model;
import jakarta.persistence.*;import java.math.BigDecimal;
@Entity public class Product {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @Column(nullable=false) public String name;
 @Column(length=5000) public String description;
 public String category; public String subCategory;
 @Column(nullable=false,precision=12,scale=2) public BigDecimal price;
 public BigDecimal originalPrice;
 public String imageUrl; public String sizes;
 public int stock=0; public boolean active=true;public boolean featured=false;
 @Version public Long version;
}
