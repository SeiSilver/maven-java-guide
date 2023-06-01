package com.spring.template.silver.app.infrastructure.jpa;

import com.spring.template.silver.app.infrastructure.entity.ProductEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

  @EntityGraph(attributePaths = {ProductEntity.Fields.category})
  List<ProductEntity> findAll();

}
