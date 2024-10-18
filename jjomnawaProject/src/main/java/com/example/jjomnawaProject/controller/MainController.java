package com.example.jjomnawaProject.controller;

import com.example.jjomnawaProject.model.entity.Categories;
import com.example.jjomnawaProject.model.entity.Product;
import com.example.jjomnawaProject.service.CategoriesService;
import com.example.jjomnawaProject.service.ProductService;
import com.example.jjomnawaProject.util.Crawler;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private CategoriesService categoriesService;

    @Autowired
    private ProductService productService;

    @Autowired
    private Crawler crawler;

    @GetMapping("/p/process")
    public String ProductInsert() {
        crawler.process();
        return "ProductInsert";
    }

    @GetMapping("/p/insert")
    public String ProductInsert(Model model, HttpSession session) {
        session.setAttribute("title", "Jjomnawa Project");
        return "ProductInsert";
    }

    @PostMapping("/p/insert")
    public String ProductInsert(Product product) {
        productService.createProduct(product);
        return "ProductList";
    }

    @GetMapping("/")
    public String ProductList2(Model model, HttpSession session) {
        session.setAttribute("title", "Jjomnawa Project");
        return "ProductList";
    }

    @GetMapping("/p/list")
    public String ProductList() {
        return "ProductList";
    }

    @GetMapping("/p/selectone/{productId}")
    public String ProductUpdate(@PathVariable Long productId, Model model) {
        Product product = productService.getProductById(productId).orElse(null);
        model.addAttribute("product", product);
        return "ProductSelectOne";
    }

    @PostMapping("/p/selectone/{productId}")
    public String ProductUpdate(@PathVariable Long productId, Product product) {
        productService.updateProduct(productId, product);
        return "redirect:/p/selectone/" + productId;
    }

    @PostMapping("/p/delete/{productId}")
    public ResponseEntity<Map<String,String>> ProductDelete(@PathVariable Long productId) {
        Product deleteProduct = productService.getProductById(productId).orElse(null);
        logger.info("Deleting product {}", deleteProduct);
        productService.deleteProduct(productId);
        deleteProduct = productService.getProductById(productId).orElse(null);
        logger.info("Deleting product {}", deleteProduct);
        return ResponseEntity.ok().body(Map.of("message", "success"));
        //return "ProductList";
    }

    @GetMapping("/main")
    public String main(Model model){
        //System.out.println("main!!!!!!!!!!");
        logger.info("\nMainController - /main");
        List<Categories> categoriesList = categoriesService.findAll();
        logger.info("\nMainController - /main \n categoriesList : {} " + categoriesList);
        model.addAttribute("categoriesList", categoriesList);
        return "main";
    }

    @GetMapping("/c/insert")
    public String CategoryManagement(Model model){
        logger.info("\nMainController - /CategoryManagement");
        List<Categories> categoriesList = categoriesService.selectAllMainAndMidCategories();
        //findAll 하면 안되고 대분류,중분류만 셀렉해서 넘겨야함
        //소분류에는 다시 소분류를 등록하면 안되기때문에
        //↑ 완료
        logger.info("\nMainController - /CategoryManagement \n categoriesList : {} " + categoriesList);
        model.addAttribute("categoriesList", categoriesList);

        //삭제용으로 findAll 해서 allCategoriesList 이름으로 넘겨야함
        List<Categories> allCategoriesList = categoriesService.findAll();
        logger.info("\nMainController - /CategoryManagement \n allCategoriesList : {} " + allCategoriesList);
        model.addAttribute("allCategoriesList", allCategoriesList);

        return "CategoryManagement";
    }
}
