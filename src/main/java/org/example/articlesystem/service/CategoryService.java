package org.example.articlesystem.service;

import org.example.articlesystem.entity.Category;
import org.example.articlesystem.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public boolean addCategory(Category category) {
        try {
            return categoryMapper.insertCategory(category) > 0;
        } catch (Exception e) {
            throw new RuntimeException("添加分类失败: " + e.getMessage());
        }
    }

    public boolean updateCategory(Category category) {
        try {
            return categoryMapper.updateCategory(category) > 0;
        } catch (Exception e) {
            throw new RuntimeException("更新分类失败: " + e.getMessage());
        }
    }

    public boolean deleteCategory(Integer id) {
        try {
            return categoryMapper.deleteCategoryById(id) > 0;
        } catch (Exception e) {
            throw new RuntimeException("删除分类失败: " + e.getMessage());
        }
    }

    public Category getCategoryById(Integer id) {
        return categoryMapper.selectCategoryById(id);
    }

    public Category getCategoryByAlias(String categoryAlias) {
        return categoryMapper.selectCategoryByAlias(categoryAlias);
    }

    public Map<String, Object> getCategoryList(Integer page, Integer size, String keyword) {
        Map<String, Object> result = new HashMap<>();

        if (page == null || page < 1) page = 1;
        if (size == null || size < 1) size = 10;

        Map<String, Object> params = new HashMap<>();
        params.put("keyword", keyword);
        params.put("start", (page - 1) * size);
        params.put("limit", size);

        List<Category> categories = categoryMapper.selectCategoryList(params);
        int total = categoryMapper.selectCategoryCount(params);

        result.put("categories", categories);
        result.put("total", total);
        result.put("page", page);
        result.put("size", size);
        result.put("totalPages", (int) Math.ceil((double) total / size));

        return result;
    }

    public List<Category> getAllCategories() {
        Map<String, Object> params = new HashMap<>();
        params.put("start", 0);
        params.put("limit", 1000);
        return categoryMapper.selectCategoryList(params);
    }
}