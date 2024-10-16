package com.example.jjomnawaProject.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table
public class Product {
    public Product() {}
    public Product(String name, Long categoryId,
                   String cCode, String iCode, String jCode,
                   String cUrl, String iUrl, String jUrl) {
        this.name = name;
        this.categoryId = categoryId;
        this.cCode = cCode;
        this.iCode = iCode;
        this.jCode = jCode;
        this.cUrl = cUrl;
        this.iUrl = iUrl;
        this.jUrl = jUrl;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "c_code", nullable = false, unique = true)
    private String cCode;

    @Column(name = "i_code", nullable = false, unique = true)
    private String iCode;

    @Column(name = "j_code", nullable = false, unique = true)
    private String jCode;

    @Column(name = "c_url", nullable = false, unique = true)
    private String cUrl;

    @Column(name = "i_url", nullable = false, unique = true)
    private String iUrl;

    @Column(name = "j_url", nullable = false, unique = true)
    private String jUrl;

    // 가격 로그와의 관계
    //@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //private List<PriceLog> priceLogs;

    /*
    drop table product;
    create table product (
            product_id number GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
            name VARCHAR2(255) NOT NULL unique,
        category_id number NOT NULL,
        c_code VARCHAR2(255) NOT NULL unique,
        i_code VARCHAR2(255) NOT NULL unique,
        j_code VARCHAR2(255) NOT NULL unique,
        c_url VARCHAR2(255) NOT NULL unique,
        i_url VARCHAR2(255) NOT NULL unique,
        j_url VARCHAR2(255) NOT NULL unique
    );
    */
}
