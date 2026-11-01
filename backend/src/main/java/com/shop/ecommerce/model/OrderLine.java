package com.shop.ecommerce.model;
import jakarta.persistence.*;import com.fasterxml.jackson.annotation.JsonIgnore;import java.math.BigDecimal;
@Entity public class OrderLine {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @JsonIgnore @ManyToOne(optional=false) public ShopOrder order;
 public Long productId;public String productName;public String size;public int quantity;public BigDecimal unitPrice;public BigDecimal lineTotal;
}
