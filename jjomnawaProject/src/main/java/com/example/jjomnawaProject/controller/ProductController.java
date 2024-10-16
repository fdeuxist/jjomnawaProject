package com.example.jjomnawaProject.controller;

import com.example.jjomnawaProject.model.entity.Product;
import com.example.jjomnawaProject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    // Create
    @PostMapping("/p")
    public ResponseEntity<Product> createProduct(@ModelAttribute Product product) {
        Product createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    // Read
    @GetMapping("/p")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/p/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> optionalProduct = productService.getProductById(id);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            return ResponseEntity.ok(product); // 200 OK 응답
        } else {
            return ResponseEntity.notFound().build(); // 404 NOT FOUND 응답
        }
    }


    // Update
    @PutMapping("/p/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return new ResponseEntity<>(productService.updateProduct(id, product), HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/p/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/p/init")
    public void initProducts() {
        System.out.println("initProducts");
        productService.initProducts();
    }

}
