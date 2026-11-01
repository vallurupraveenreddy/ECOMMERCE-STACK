package com.shop.ecommerce.model;
import jakarta.persistence.*;import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity @Table(uniqueConstraints=@UniqueConstraint(columnNames={"user_id","product_id","size"})) public class CartItem {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @JsonIgnore @ManyToOne(optional=false) public User user;
 @ManyToOne(optional=false) public Product product;
 @Column(nullable=false) public String size;
 public int quantity;
}
