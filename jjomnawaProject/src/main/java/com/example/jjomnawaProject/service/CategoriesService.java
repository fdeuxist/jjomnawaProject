package com.example.jjomnawaProject.service;

import com.example.jjomnawaProject.model.entity.Categories;
import com.example.jjomnawaProject.reposiroty.CategoriesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesService {
    @Autowired
    private CategoriesRepository categoriesRepository;

    private static final Logger logger = LoggerFactory.getLogger(CategoriesService.class);

    public List<Categories> findAll() {
        return categoriesRepository.findAll(Sort.by(Sort.Direction.ASC,"id"));
    }   // JPA에서 findAll 메서드를 재정의하여 결과를 id 기준으로 오름차순으로 정렬하려면,
        // JpaRepository의 메서드에 Sort 객체를 추가하거나, JPQL 쿼리를 사용할 수 있습니다.

    public List<Categories> selectAllCategories(){
        return categoriesRepository.selectAllCategories();
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
        // 현재 카테고리의 하위 리스트
        List<Categories> subCategories = categoriesRepository.findSubCategoriesByParentId(categoryId);

        // 하위 카테고리 삭제
        for (Categories subCategory : subCategories) {
            deleteCategory(subCategory.getId());  // 재귀 호출
        }

        // 현재 카테고리 삭제
        Categories categoryToDelete = findCategoryById(categoryId);
        categoriesRepository.deleteById(categoryId);    //삭제
        logger.info("Deleted Category : " + categoryToDelete);
    }

    public Categories findCategoryById(Long categoryId) {
        return categoriesRepository.findById(categoryId).orElse(null);
    }

    public void modifyCategory(Long categoryId, String name) {
        Categories category = findCategoryById(categoryId);
        category.setName(name);
        categoriesRepository.save(category);
    }

}
