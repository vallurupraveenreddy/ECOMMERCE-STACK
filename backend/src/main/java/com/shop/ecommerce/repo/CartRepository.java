package com.shop.ecommerce.repo;
import com.shop.ecommerce.model.CartItem;import org.springframework.data.jpa.repository.JpaRepository;import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
public interface CartRepository extends JpaRepository<CartItem,Long> { java.util.List<CartItem> findByUserId(Long id); java.util.Optional<CartItem> findByUserIdAndProductIdAndSize(Long id,Long productId,String size);void deleteByUserId(Long userId); }
