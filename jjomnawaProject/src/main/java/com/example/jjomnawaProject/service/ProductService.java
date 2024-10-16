package com.example.jjomnawaProject.service;

import com.example.jjomnawaProject.model.entity.Product;
import com.example.jjomnawaProject.reposiroty.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);


    public Product createProduct(Product product) {
        logger.info("\ncreateProduct() product : {}", product);
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        logger.info("\ngetAllProducts() product list : {}", productRepository.findAll());
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        logger.info("\ngetProductById() product : {}", id);
        return productRepository.findById(id);
    }

    public Product updateProduct(Long id, Product product) {
        logger.info("\nupdateProduct() product : {}", product);
//        Product product1 = productRepository.findById(id).orElse(null);
//        if(product1 != null) {
//        }
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        logger.info("\ndeleteProduct() product : {}", product);
        productRepository.deleteById(id);
    }

    public void initProducts() {
        List<Product> products = new ArrayList<>();
        products.add(0, new Product("코어 i7-14700K 정품박스", 0L, "1078453","1453638","455917",
                        "https://www.compuzone.co.kr/product/product_detail.htm?ProductNo=1078453&BigDivNo=4&MediumDivNo=1012&DivNo=2032",
                        "https://usr.icoda.co.kr/item/view/1453638",
                "https://joyzen.co.kr/product/sInfo.html?fid=1&uid=38&Pnum=455917") );
        products.add(1, new Product("라이젠5 라파엘 7600", 0L, "1102431","1319277","439111",
                "https://www.compuzone.co.kr/product/product_detail.htm?ProductNo=1102431&BigDivNo=&MediumDivNo=1012&DivNo=2033",
                "https://usr.icoda.co.kr/item/view/1319277",
                "https://joyzen.co.kr/product/sInfo.html?fid=1&uid=38&Pnum=439111") );
        products.add(2, new Product("삼성 DDR5 PC5-44800 [16GB] (5600)", 0L, "990235","1365678","441062",
                "https://www.compuzone.co.kr/product/product_detail.htm?ProductNo=990235&BigDivNo=&MediumDivNo=&DivNo=",
                "https://usr.icoda.co.kr/item/view/1365678",
                "https://joyzen.co.kr/product/sInfo.html?fid=1&uid=40&Pnum=441062") );

        for(Product product : products ) {
            productRepository.save(product);
            Product savedProduct = productRepository.findByName(product.getName()).orElse(null);
            logger.info("\ninitProduct() product : {}", savedProduct);
        }
    }

}
