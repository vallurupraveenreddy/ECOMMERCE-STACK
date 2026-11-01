package com.shop.ecommerce.model;
import jakarta.persistence.*;import com.fasterxml.jackson.annotation.JsonIgnoreProperties;import java.time.Instant;
@Entity @Table(uniqueConstraints=@UniqueConstraint(columnNames={"user_id","product_id"})) public class Review {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @JsonIgnoreProperties({"email","passwordHash","enabled","role"}) @ManyToOne(optional=false) public User user;
 @ManyToOne(optional=false) public Product product;
 public int rating;@Column(length=2000) public String comment;public Instant createdAt=Instant.now();
}
