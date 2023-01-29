package com.ssafy.core.repository;

import com.ssafy.TestConfig;
import com.ssafy.core.entity.Categories;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(TestConfig.class)
class CategoriesRepositoryTest {

    @Autowired
    CategoriesRepository categoriesRepository;

    @Test
    @DisplayName("id값 찾기 성공")
    void findIdTestSuccess() {
        //ginen
        Categories categories = new Categories(null, "우정사진");
        categoriesRepository.save(categories);

        //when
        List<Long> categoryIdList = categoriesRepository.findAllIdByNameContaining("우정");

        //then
        System.out.println(categoryIdList.toString());
    }

    @Test
    @DisplayName("id값 찾기 실패")
    void findIdTestFalse() {
        //ginen
        Categories categories = new Categories(null, "우정사진");
        categoriesRepository.save(categories);

        //when
        List<Long> categoryIdList = categoriesRepository.findAllIdByNameContaining("결혼");

        //then
        Assertions.assertTrue(categoryIdList.isEmpty());
    }

    @Test
    @DisplayName("여러 개 반환")
    void findOneButListTest() {
        //ginen
        Categories categories = new Categories(null, "우정사진");
        categoriesRepository.save(categories);
        Categories category = new Categories(null, "웨딩사진");
        categoriesRepository.save(category);

        //when
        List<Long> categoryIdList = categoriesRepository.findAllIdByNameContaining("사진");

        //then
        Assertions.assertTrue(categoryIdList.size() > 1);
    }

}