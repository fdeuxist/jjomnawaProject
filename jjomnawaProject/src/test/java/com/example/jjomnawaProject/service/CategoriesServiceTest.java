package com.example.jjomnawaProject.service;

import com.example.jjomnawaProject.model.entity.Categories;
import com.example.jjomnawaProject.reposiroty.CategoriesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest
@Transactional // 테스트가 끝난 후 롤백하여 데이터베이스를 원래 상태로 유지
class CategoriesServiceTest {

    @Autowired
    private CategoriesService categoriesService;
    @Autowired
    private CategoriesRepository categoriesRepository;

//    @InjectMocks    // 모킹된 객체를 주입하는 데 사용
//    private CategoriesService categoriesService;
    //@InjectMocks가 붙은 필드는 Mockito가 자동으로 해당 클래스의 인스턴스를 생성하고,
    // 이 인스턴스의 필드에 @Mock으로 정의된 모의 객체들을 주입합니다.
    // 이 과정을 통해, 테스트할 클래스(CategoriesService)가 실제로 사용할 수 있는 의존성(mocked dependencies)을 갖게 됩니다.

//    @Mock   //테스트할 클래스의 의존성을 모킹(mocking)하는 데 사용
//    private CategoriesRepository categoriesRepository;
    //@Mock이 붙은 필드는 Mockito가 테스트 중에 사용할 모의 객체(mock object)를 자동으로 생성합니다.
    // 이 객체는 실제 데이터베이스나 외부 시스템에 연결되지 않고, 원하는 동작을 정의할 수 있습니다.
    // 이를 통해 특정 동작을 테스트할 수 있으며, 실제 구현과는 분리된 상태에서 테스트를 진행할 수 있습니다.
/*
    테스트 흐름
    모의 객체 생성: @Mock이 붙은 categoriesRepository는 실제 구현이 아니라 모의 객체로 생성됩니다.
    주입: @InjectMocks가 붙은 categoriesService는 Mockito에 의해 생성되며, 이때 categoriesRepository가 주입됩니다.
    테스트 실행: 이제 categoriesService의 메서드를 테스트할 때, categoriesRepository에 대한 호출은 모의 객체를 통해 이루어집니다.
    이렇게 설정함으로써, 실제 데이터베이스와의 상호작용 없이도 CategoriesService의 동작을 테스트할 수 있습니다.
    @Autowired
    용도: Spring의 의존성 주입 기능을 사용하여 객체를 자동으로 주입합니다.
    설명:
    @Autowired가 붙은 필드는 Spring 컨테이너가 관리하는 빈(bean)으로, 애플리케이션의 실행 중에 해당 빈이 생성되고 주입됩니다.
    실제 구현체가 주입되므로, CategoriesService와 CategoriesRepository는 실제 데이터베이스와의 상호작용을 포함하여 동작하게 됩니다.
    이 방식은 통합 테스트(integration test)에 적합합니다.
    @InjectMocks와 @Mock
    용도: Mockito를 사용하여 모의 객체를 생성하고 주입합니다.
    설명:
    @InjectMocks로 주입된 클래스는 Mockito가 생성하며, @Mock으로 정의된 필드는 모의 객체로 대체됩니다.
    실제 데이터베이스와의 상호작용 없이, 원하는 동작을 정의할 수 있는 테스트 환경을 만듭니다.
    이 방식은 단위 테스트(unit test)에 적합하며, 특정 메서드의 동작을 독립적으로 테스트할 수 있습니다.
    주요 차이점
    테스트의 범위:

    @Autowired: 통합 테스트에 적합. 전체 애플리케이션의 동작을 테스트하는 데 사용.
    @InjectMocks와 @Mock: 단위 테스트에 적합. 특정 클래스의 동작을 독립적으로 테스트하는 데 사용.
    의존성 관리:

    @Autowired: Spring 컨테이너가 관리하므로, 모든 빈이 실제 인스턴스로 주입됨.
    @InjectMocks: Mockito가 관리하므로, 모의 객체로 주입되어 실제 구현과는 분리된 테스트가 가능.
    데이터베이스와의 상호작용:

    @Autowired: 실제 데이터베이스와의 상호작용이 발생.
    @InjectMocks: 모의 객체를 통해 데이터베이스와의 상호작용 없이 테스트 가능.
    결론
    선택 기준:
    실제 환경을 반영한 테스트가 필요하다면 @Autowired를 사용하고,
    특정 메서드나 클래스의 동작을 독립적으로 검증하고자 한다면 Mockito의 @InjectMocks와 @Mock을 사용하는 것이 좋습니다.
*/
    //모의객체를 사용하면 db를 사용하지 않는다



    private Long targetCategoryId;

    @BeforeEach
    public void setUp() {
        // 각 테스트 메서드가 실행되기 전에 호출되어 테스트 데이터를 준비합니다.
        // 테스트 데이터 추가
        Categories mainCategory0 = new Categories("Main Category 0", 0L, 0L);
        categoriesRepository.save(mainCategory0);

        Categories subCategory00 = new Categories("Sub Category 0-0", mainCategory0.getId(),1L);
        categoriesRepository.save(subCategory00);
        Categories subCategory01 = new Categories("Sub Category 0-1", mainCategory0.getId(),1L);
        categoriesRepository.save(subCategory01);
        Categories subCategory02 = new Categories("Sub Category 0-2", mainCategory0.getId(),1L);
        categoriesRepository.save(subCategory02);

        Categories subSubCategory000 = new Categories("Sub Sub Category 0-0-0",subCategory00.getId(),2L);
        categoriesRepository.save(subSubCategory000);
        Categories subSubCategory001 = new Categories("Sub Sub Category 0-0-1",subCategory00.getId(),2L);
        categoriesRepository.save(subSubCategory001);
        Categories subSubCategory002 = new Categories("Sub Sub Category 0-0-2",subCategory00.getId(),2L);
        categoriesRepository.save(subSubCategory002);

        Categories subSubCategory010 = new Categories("Sub Sub Category 0-1-0",subCategory01.getId(),2L);
        categoriesRepository.save(subSubCategory010);
        Categories subSubCategory011 = new Categories("Sub Sub Category 0-1-1",subCategory01.getId(),2L);
        categoriesRepository.save(subSubCategory011);
        Categories subSubCategory012 = new Categories("Sub Sub Category 0-1-2",subCategory01.getId(),2L);
        categoriesRepository.save(subSubCategory012);

        Categories subSubCategory020 = new Categories("Sub Sub Category 0-2-0",subCategory02.getId(),2L);
        categoriesRepository.save(subSubCategory020);
        Categories subSubCategory021 = new Categories("Sub Sub Category 0-2-1",subCategory02.getId(),2L);
        categoriesRepository.save(subSubCategory021);
        Categories subSubCategory022 = new Categories("Sub Sub Category 0-2-2",subCategory02.getId(),2L);
        categoriesRepository.save(subSubCategory022);


        Categories mainCategory1 = new Categories("Main Category 1", 0L, 0L);
        categoriesRepository.save(mainCategory1);

        Categories subCategory10 = new Categories("Sub Category 1-0", mainCategory1.getId(),1L);
        categoriesRepository.save(subCategory10);
        Categories subCategory11 = new Categories("Sub Category 1-1", mainCategory1.getId(),1L);
        categoriesRepository.save(subCategory11);
        Categories subCategory12 = new Categories("Sub Category 1-2", mainCategory1.getId(),1L);
        categoriesRepository.save(subCategory12);

        Categories subSubCategory100 = new Categories("Sub Sub Category 1-0-0",subCategory10.getId(),2L);
        categoriesRepository.save(subSubCategory100);
        Categories subSubCategory101 = new Categories("Sub Sub Category 1-0-1",subCategory10.getId(),2L);
        categoriesRepository.save(subSubCategory101);
        Categories subSubCategory102 = new Categories("Sub Sub Category 1-0-2",subCategory10.getId(),2L);
        categoriesRepository.save(subSubCategory102);

        //삭제할 카테고리 지정
        targetCategoryId = subCategory01.getId();
        //targetCategoryId = mainCategory0.getId();
        //targetCategoryId = mainCategory1.getId();



    }

    @Test
    public void testDeleteCategory() {
        // 삭제하기 전 현재 카테고리 상태 확인
        List<Categories> currentCategories = categoriesService.findAll();
        System.out.println("=========Current Categories before deletion:=========");
        for (Categories category : currentCategories) {
            System.out.println(category);
        }

        // 카테고리 삭제
        categoriesService.deleteCategory(targetCategoryId);

        // 삭제 후 현재 카테고리 상태 확인
        currentCategories = categoriesService.findAll();
        System.out.println("=========Current Categories atfer deletion:=========");
        for (Categories category : currentCategories) {
            System.out.println(category);
        }

    }

}