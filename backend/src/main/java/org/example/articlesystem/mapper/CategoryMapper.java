package org.example.articlesystem.mapper;

import org.example.articlesystem.entity.Category;
import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.Map;

@Mapper
public interface CategoryMapper {

    int insertCategory(Category category);

    int deleteCategoryById(Integer id);

    int updateCategory(Category category);

    Category selectCategoryById(Integer id);

    Category selectCategoryByAlias(String categoryAlias);

    List<Category> selectCategoryList(Map<String, Object> params);

    int selectCategoryCount(Map<String, Object> params);
}