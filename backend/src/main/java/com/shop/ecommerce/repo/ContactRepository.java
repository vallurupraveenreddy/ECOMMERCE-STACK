package com.shop.ecommerce.repo;
import com.shop.ecommerce.model.ContactMessage;import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
public interface ContactRepository extends JpaRepository<ContactMessage,Long> {   }
