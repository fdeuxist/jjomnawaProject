package com.example.jjomnawaProject.controller;

import com.example.jjomnawaProject.model.entity.Categories;
import com.example.jjomnawaProject.service.CategoriesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class MainController {
    //@Autowired
    //private Users2Service users2Service;
    @Autowired
    private CategoriesService categoriesService;

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @GetMapping("/main")
    public String main(Model model){
        //System.out.println("main!!!!!!!!!!");
        logger.info("\nMainController - /main");
        List<Categories> categoriesList = categoriesService.findAll();
        logger.info("\nMainController - /main \n categoriesList : {} " + categoriesList);
        model.addAttribute("categoriesList", categoriesList);
        return "main";
    }

    @GetMapping("/CategoryManagement")
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
