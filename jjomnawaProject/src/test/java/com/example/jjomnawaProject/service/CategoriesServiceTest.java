package com.example.jjomnawaProject.service;

import com.example.jjomnawaProject.model.entity.Categories;
import com.example.jjomnawaProject.reposiroty.CategoriesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // 테스트가 끝난 후 롤백하여 데이터베이스를 원래 상태로 유지
class CategoriesServiceTest {

    @Autowired
    private CategoriesService categoriesService;
    @Autowired
    private CategoriesRepository categoriesRepository;

    @BeforeEach
    public void setUp() {
        // 테스트 데이터 추가
        Categories mainCategory = new Categories();
        mainCategory.setName("Main Category");
        mainCategory.setP_id(0L);
        mainCategory.setDepth(0L);
        categoriesRepository.save(mainCategory);

        Categories subCategory = new Categories();
        subCategory.setName("Sub Category");
        subCategory.setP_id(mainCategory.getId());
        subCategory.setDepth(1L);
        categoriesRepository.save(subCategory);
    }

    @Test
    public void testDeleteCategory() {
        // 삭제할 카테고리 ID
        Long categoryId = 1L; // main category ID

        // 카테고리 삭제
        categoriesService.deleteCategory(categoryId);

        // 카테고리와 하위 카테고리가 삭제되었는지 확인
        assertFalse(categoriesRepository.findById(categoryId).isPresent(), "Main category should be deleted");

        // Sub category 확인
        Long subCategoryId = 2L; // sub category ID
        assertFalse(categoriesRepository.findById(subCategoryId).isPresent(), "Sub category should be deleted");
    }

    @Test
    void findAll() {
    }

    @Test
    void selectAllCategories() {
    }

    @Test
    void addMainCategory() {
    }

    @Test
    void addSubCategory() {
    }

    @Test
    void findAllMainCategories() {
    }

    @Test
    void findSubCategoriesByParentId() {
    }

    @Test
    void selectAllMainAndMidCategories() {
    }

    @Test
    void deleteCategory() {
    }

    @Test
    void findCategoryById() {
    }

    @Test
    void modifyCategory() {
    }
}