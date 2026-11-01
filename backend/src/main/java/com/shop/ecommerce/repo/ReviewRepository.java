package com.shop.ecommerce.repo;
import com.shop.ecommerce.model.Review;import org.springframework.data.jpa.repository.JpaRepository;import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
public interface ReviewRepository extends JpaRepository<Review,Long> { java.util.List<Review> findByProductId(Long id);java.util.Optional<Review> findByUserIdAndProductId(Long userId,Long productId); }
