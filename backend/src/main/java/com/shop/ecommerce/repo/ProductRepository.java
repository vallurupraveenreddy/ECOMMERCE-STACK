package com.shop.ecommerce.repo;
import com.shop.ecommerce.model.Product;import org.springframework.data.jpa.repository.JpaRepository;import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface ProductRepository extends JpaRepository<Product,Long>,JpaSpecificationExecutor<Product>{}
