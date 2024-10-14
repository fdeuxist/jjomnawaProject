package com.example.jjomnawaProject.service;

import com.example.jjomnawaProject.model.entity.Categories;
import com.example.jjomnawaProject.reposiroty.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesService {
    @Autowired
    private CategoriesRepository categoriesRepository;

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
}
