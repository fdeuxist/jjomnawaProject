package com.example.jjomnawaProject.controller;

import com.example.jjomnawaProject.model.entity.Categories;
import com.example.jjomnawaProject.service.CategoriesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;

    private static final Logger logger = LoggerFactory.getLogger(CategoriesController.class);

    @GetMapping("/c/init")
    public void init(){
        logger.info("init");
        categoriesService.defaultCategoryInit();
    }

    @GetMapping("/test")
    public String test(){
        return "test!";
    }

    //selectAll
    @GetMapping("/c")
    public ResponseEntity<Map<String, Object>> listCategories(Model model) {
        List<Categories> categoriesList = categoriesService.findAll();
        Map<String, Object> response = new HashMap<>();
        //model.addAttribute("categories", categoriesList);
        response.put("categoriesList", categoriesList);
        response.put("msg", "success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
/*
    //대분류 생성
    @PostMapping("/c/addMain")
    public String addMainCategory(@RequestParam String name, Model model) {
        categoriesService.addMainCategory(name);
        return "redirect:/categories";
    }
    //서브분류 생성
    @PostMapping("/c/addSub")
    public String adSubCategory(
            @RequestParam String name,
            @RequestParam String parentName,
            @RequestParam(required = false) Long p_id) {
        categoriesService.addSubCategory(name, parentName);
        return "redirect:/categories";
    }

*/
    //대분류 서브분류 공통 생성
    @PostMapping("/c/addCategory")
    public ResponseEntity<Map<String, Object>> addCategory(
            @RequestParam String name,
            @RequestParam String parentName,
            @RequestParam(required = false) Long p_id) {
        Map<String, Object> response = new HashMap<>();
        if(parentName.equals("")) { //대분류로 등록하기
            categoriesService.addMainCategory(name);
        }else{
            categoriesService.addSubCategory(name, parentName);
        }
        //List<Categories> categoriesList = categoriesService.findAll();
        //response.put("categoriesList", categoriesList);
        response.put("msg", "success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
/*
    //모든 대분류
    @GetMapping("/c/findSubCategoriesByParentId")
    public ResponseEntity<Map<String, Object>> findAllMainCategories(Model model) {
        List<Categories> mainCategoriesList = categoriesService.findAllMainCategories();
        Map<String, Object> response = new HashMap<>();
        response.put("mainCategoriesList", mainCategoriesList); //대분류
        response.put("depth", 1L);
        response.put("msg", "success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //모든 서브분류
    @GetMapping("/c/findSubCategoriesByParentId/{parentId}")
    public ResponseEntity<Map<String, Object>> findSubCategoriesByParentId(@PathVariable Long parentId) {
        logger.info("\nCategoriesController - /findSubCategoriesByParentId/{}",parentId);
        List<Categories> mainCategoriesList = categoriesService.findSubCategoriesByParentId(parentId);
        logger.info("\nCategoriesController - /findSubCategoriesByParentId/{}",parentId , "   mainCategoriesList : {}" ,mainCategoriesList );
        Map<String, Object> response = new HashMap<>();
        //중분류인지 소분류인지 판단해서 이름 바꿔서 넘겨야함
        Long depth = mainCategoriesList.get(0).getDepth();
        logger.info("\nCategoriesController - /findSubCategoriesByParentId/{}",parentId , " depth : {} " , depth);
        if(depth==2L){
            response.put("midiumCategoriesList", mainCategoriesList);   //중분류
        }else if(depth==3L){
            response.put("subCategoriesList", mainCategoriesList);  //소분류
        }
        response.put("depth",depth);
        response.put("msg", "success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
*/

    //대분류 서브분류 공통 사용 가능
    @GetMapping("/c/getCategories")
    public ResponseEntity<Map<String, Object>> getCategories(@RequestParam Long parentId) {
        logger.info("\nCategoriesController - /getCategories?parentId={}",parentId);
        Map<String, Object> response = new HashMap<>();
        List<Categories> categoriesList = null;
        Long depth = null;

        categoriesList = categoriesService.findSubCategoriesByParentId(parentId);
        logger.info("\nCategoriesController - findSubCategoriesByParentId({})",parentId);

        if(!categoriesList.isEmpty()){
            depth = categoriesList.get(0).getDepth();
        }
        response.put("categoriesList", categoriesList);
        logger.info("\nCategoriesController - categoriesList : {}, depth : {}", categoriesList, depth);  //null 처리해야함

        response.put("depth", depth);
        response.put("msg", "success");
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    /*
    //대분류 서브분류 공통 사용 가능
    @GetMapping("/c/getCategories")
    public ResponseEntity<Map<String, Object>> getCategories(@RequestParam(required = false) Long parentId) {
        logger.info("\nCategoriesController - /getCategories?parentId={}",parentId);
        Map<String, Object> response = new HashMap<>();
        List<Categories> categoriesList = null;
        Long depth = null;

        if (parentId != null) { //중소분류
            categoriesList = categoriesService.findSubCategoriesByParentId(parentId);
            logger.info("\nCategoriesController - findSubCategoriesByParentId({})",parentId);
        } else {    //대분류
            categoriesList = categoriesService.findAllMainCategories();
            logger.info("\nCategoriesController - findAllMainCategories()");
        }
        if(!categoriesList.isEmpty()){
            depth = categoriesList.get(0).getDepth();
        }
        response.put("categoriesList", categoriesList);
        logger.info("\nCategoriesController - categoriesList : {}",categoriesList , "depth : {} " ,depth);
        response.put("depth",depth);
        response.put("msg", "success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    */

    @DeleteMapping("/c/{id}")
    public ResponseEntity<Map<String, Object>> deleteCategory(@PathVariable Long id) {
        logger.info("\nCategoriesController - /deleteCategory?id={}",id);
        Map<String, Object> response = new HashMap<>();
        categoriesService.deleteCategory(id);
        response.put("msg", "success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/c/{id}")
    public ResponseEntity<Map<String, Object>> updateCategory(
            @PathVariable Long id, @RequestBody Map<String, String> categoryData) {
        String name = categoryData.get("name");
        logger.info("\nCategoriesController - /updateCategory?id={}, name={}", id, name);
        Map<String, Object> response = new HashMap<>();
        categoriesService.modifyCategory(id, name);
        response.put("msg", "success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }








}
