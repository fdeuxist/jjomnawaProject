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
        logger.info("\ndeleteCategory()  Deleted Category : " + categoryToDelete);
    }

    public Categories findCategoryById(Long categoryId) {
        return categoriesRepository.findById(categoryId).orElse(null);
    }

    public void modifyCategory(Long categoryId, String name) {
        Categories category = findCategoryById(categoryId);
        category.setName(name);
        categoriesRepository.save(category);
    }

    public void defaultCategoryInit(){
        Categories mainCategory0 = new Categories("대분류 1", 0L, 0L);
        categoriesRepository.save(mainCategory0);

        Categories subCategory00 = new Categories("대1의 중1", mainCategory0.getId(),1L);
        categoriesRepository.save(subCategory00);
        Categories subCategory01 = new Categories("대1의 중2", mainCategory0.getId(),1L);
        categoriesRepository.save(subCategory01);
        Categories subCategory02 = new Categories("대1의 중3", mainCategory0.getId(),1L);
        categoriesRepository.save(subCategory02);

        Categories subSubCategory000 = new Categories("대1중1의 소1",subCategory00.getId(),2L);
        categoriesRepository.save(subSubCategory000);
        Categories subSubCategory001 = new Categories("대1중1의 소2",subCategory00.getId(),2L);
        categoriesRepository.save(subSubCategory001);
        Categories subSubCategory002 = new Categories("대1중1의 소3",subCategory00.getId(),2L);
        categoriesRepository.save(subSubCategory002);

        Categories subSubCategory010 = new Categories("대1중2의 소1",subCategory01.getId(),2L);
        categoriesRepository.save(subSubCategory010);
        Categories subSubCategory011 = new Categories("대1중2의 소2",subCategory01.getId(),2L);
        categoriesRepository.save(subSubCategory011);
        Categories subSubCategory012 = new Categories("대1중2의 소3",subCategory01.getId(),2L);
        categoriesRepository.save(subSubCategory012);

        Categories subSubCategory020 = new Categories("대1중3의 소1",subCategory02.getId(),2L);
        categoriesRepository.save(subSubCategory020);
        Categories subSubCategory021 = new Categories("대1중3의 소2",subCategory02.getId(),2L);
        categoriesRepository.save(subSubCategory021);
        Categories subSubCategory022 = new Categories("대1중3의 소3",subCategory02.getId(),2L);
        categoriesRepository.save(subSubCategory022);

        Categories mainCategory1 = new Categories("대분류 2", 0L, 0L);
        categoriesRepository.save(mainCategory1);

        Categories subCategory10 = new Categories("대2의 중1", mainCategory1.getId(),1L);
        categoriesRepository.save(subCategory10);
        Categories subCategory11 = new Categories("대2의 중2", mainCategory1.getId(),1L);
        categoriesRepository.save(subCategory11);
        Categories subCategory12 = new Categories("대2의 중3", mainCategory1.getId(),1L);
        categoriesRepository.save(subCategory12);

        Categories subSubCategory100 = new Categories("대2중1의 소1",subCategory10.getId(),2L);
        categoriesRepository.save(subSubCategory100);
        Categories subSubCategory101 = new Categories("대2중1의 소2",subCategory10.getId(),2L);
        categoriesRepository.save(subSubCategory101);
        Categories subSubCategory102 = new Categories("대2중1의 소3",subCategory10.getId(),2L);
        categoriesRepository.save(subSubCategory102);

        Categories mainCategory2 = new Categories("대분류 3", 0L, 0L);
        categoriesRepository.save(mainCategory2);

    }



}
