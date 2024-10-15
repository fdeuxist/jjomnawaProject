package com.example.jjomnawaProject.reposiroty;

import com.example.jjomnawaProject.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
