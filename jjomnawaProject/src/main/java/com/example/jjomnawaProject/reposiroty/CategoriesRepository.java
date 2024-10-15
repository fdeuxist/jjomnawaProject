package com.example.jjomnawaProject.reposiroty;

import com.example.jjomnawaProject.model.entity.Categories;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoriesRepository extends JpaRepository<Categories,Long> {

    // 모든 대분류 가져오기
    @Query("SELECT c FROM Categories c WHERE c.p_id = 0")
    List<Categories> findAllMainCategories();

    // 특정 분류의 서브분류 가져오기 (대~중)/(중~소)
    @Query("SELECT c FROM Categories c WHERE c.p_id = :parentId")
    List<Categories> findSubCategoriesByParentId(Long parentId);

    //메인 카테고리 추가
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Categories (name, p_id, depth) VALUES (:name, 0, 0)", nativeQuery = true)
    void addMainCategory(String name);

    //서브(중,소) 카테고리 추가
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Categories (name, p_id, depth) " +
            "VALUES (:name, (SELECT id FROM Categories WHERE name = :parentName), " +
            "(SELECT depth + 1 FROM Categories WHERE name = :parentName))", nativeQuery = true)
    void addSubCategory(String name, String parentName);

    Categories findByName(String name);

    //대분류와 중분류만 가져오기 (서브카테고리 등록을 위한 소분류 제외 selectAll)
    @Modifying
    @Transactional
    @Query(value = "SELECT * FROM categories WHERE depth NOT IN (2)", nativeQuery = true)
    List<Categories> selectAllMainAndMidCategories();


}
