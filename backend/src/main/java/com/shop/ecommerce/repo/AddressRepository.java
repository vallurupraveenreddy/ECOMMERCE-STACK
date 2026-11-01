package com.shop.ecommerce.repo;
import com.shop.ecommerce.model.Address;import org.springframework.data.jpa.repository.JpaRepository;import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
public interface AddressRepository extends JpaRepository<Address,Long> { java.util.List<Address> findByUserId(Long id);java.util.Optional<Address> findByIdAndUserId(Long id,Long userId); }
