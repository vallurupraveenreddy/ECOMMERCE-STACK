package com.shop.ecommerce.model;
import jakarta.persistence.*;import java.time.Instant;
@Entity public class ContactMessage {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 public String name;public String email;@Column(length=4000) public String message;
 public Instant createdAt=Instant.now();public boolean resolved=false;
}
