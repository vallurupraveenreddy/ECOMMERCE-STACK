package com.shop.ecommerce.model;
import jakarta.persistence.*;import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity @Table(name="app_users") public class User {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @Column(nullable=false) public String name;
 @Column(nullable=false,unique=true) public String email;
 @JsonIgnore @Column(nullable=false) public String passwordHash;
 @Column(nullable=false) public String role="USER";
 public boolean enabled=true;
 public String phone; public String address; public String city; public String state; public String zipcode; public String country; public String dob; public String gender;
}
