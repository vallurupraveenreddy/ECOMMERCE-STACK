package com.shop.ecommerce.model;
import jakarta.persistence.*;import com.fasterxml.jackson.annotation.JsonIgnore;import java.math.BigDecimal;import java.time.Instant;import java.util.*;
@Entity @Table(name="shop_orders") public class ShopOrder {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @JsonIgnore @ManyToOne(optional=false) public User user;
 @Column(nullable=false) public Instant createdAt=Instant.now();
 public String status="PLACED";public String paymentMethod="COD";public String paymentStatus="PENDING";
 public BigDecimal subtotal;public BigDecimal discount;public BigDecimal shipping;public BigDecimal total;
 @Column(length=2000) public String shippingAddress;
 @OneToMany(mappedBy="order",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.EAGER) public List<OrderLine> items=new ArrayList<>();
}
