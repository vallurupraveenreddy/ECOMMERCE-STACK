package com.shop.ecommerce.repo;
import com.shop.ecommerce.model.ShopOrder;import org.springframework.data.jpa.repository.JpaRepository;import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
public interface OrderRepository extends JpaRepository<ShopOrder,Long> { java.util.List<ShopOrder> findByUserIdOrderByCreatedAtDesc(Long id);java.util.Optional<ShopOrder> findByIdAndUserId(Long id,Long userId); }
