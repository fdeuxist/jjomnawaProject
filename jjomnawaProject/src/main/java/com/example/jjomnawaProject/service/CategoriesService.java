package com.example.jjomnawaProject.service;

import com.example.jjomnawaProject.model.entity.Categories;
import com.example.jjomnawaProject.reposiroty.CategoriesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesService {
    @Autowired
    private CategoriesRepository categoriesRepository;

    private static final Logger logger = LoggerFactory.getLogger(CategoriesService.class);

    public List<Categories> findAll() {
        return categoriesRepository.findAll();
    }

    //메인 카테고리 추가
    public Categories addMainCategory(String name) {
        categoriesRepository.addMainCategory(name);
        return categoriesRepository.findByName(name);
    }

    //서브(중,소) 카테고리 추가
    public Categories addSubCategory(String name, String parentName) {
        categoriesRepository.addSubCategory(name, parentName);
        return categoriesRepository.findByName(name);
    }

    // 모든 대분류 가져오기
    public List<Categories> findAllMainCategories() {
        return categoriesRepository.findAllMainCategories();
    }

    // 특정 분류의 서브분류 가져오기 (대~중)/(중~소)
    public List<Categories> findSubCategoriesByParentId(Long parentId) {
        return categoriesRepository.findSubCategoriesByParentId(parentId);
    }

    //대분류와 중분류만 가져오기 (서브카테고리 등록을 위한 소분류 제외 selectAll)
    public List<Categories> selectAllMainAndMidCategories(){
        return categoriesRepository.selectAllMainAndMidCategories();
    }


    public void deleteCategory(Long categoryId) {
        // 현재 카테고리 삭제
        List<Categories> subCategories = categoriesRepository.findSubCategoriesByParentId(categoryId);

        // 하위 카테고리 삭제
        for (Categories subCategory : subCategories) {
            deleteCategory(subCategory.getId());  // 재귀 호출
        }

        // 현재 카테고리 삭제
        categoriesRepository.deleteById(categoryId);
        logger.info("Deleted Category: " + categoryId);
    }




}
