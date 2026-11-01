package com.shop.ecommerce.repo;
import com.shop.ecommerce.model.WishlistItem;import org.springframework.data.jpa.repository.JpaRepository;import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
public interface WishlistRepository extends JpaRepository<WishlistItem,Long> { java.util.List<WishlistItem> findByUserId(Long id);java.util.Optional<WishlistItem> findByUserIdAndProductId(Long id,Long productId); }
