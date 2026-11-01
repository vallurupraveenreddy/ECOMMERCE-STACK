package com.shop.ecommerce.model;
import jakarta.persistence.*;import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity public class Address {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @JsonIgnore @ManyToOne(optional=false) public User user;
 public String fullName;public String phone;public String line1;public String line2;public String city;public String state;public String postalCode;public String country="India";public boolean isDefault;
}
