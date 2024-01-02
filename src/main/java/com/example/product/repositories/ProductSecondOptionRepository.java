package com.example.product.repositories;

import com.example.product.domains.ProductSecondOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductSecondOptionRepository extends JpaRepository<ProductSecondOption, Long> {

    Optional<ProductSecondOption> findById(Long id);

    List<ProductSecondOption> findByProductFirstOption_Id(Long productFirstOptionId);
}
