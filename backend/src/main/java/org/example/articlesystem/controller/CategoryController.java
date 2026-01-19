package org.example.articlesystem.controller;

import org.example.articlesystem.common.Result;
import org.example.articlesystem.entity.Category;
import org.example.articlesystem.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public Result<Map<String, Object>> getCategoryList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword) {

        try {
            Map<String, Object> result = categoryService.getCategoryList(page, size, keyword);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error("获取分类列表失败: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public Result<List<Category>> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return Result.success(categories);
        } catch (Exception e) {
            return Result.error("获取全部分类失败: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Result<Category> getCategoryDetail(@PathVariable Integer id) {
        try {
            Category category = categoryService.getCategoryById(id);
            if (category == null) {
                return Result.error(404, "分类不存在");
            }
            return Result.success(category);
        } catch (Exception e) {
            return Result.error("获取分类详情失败: " + e.getMessage());
        }
    }

    @GetMapping("/alias/{alias}")
    public Result<Category> getCategoryByAlias(@PathVariable String alias) {
        try {
            Category category = categoryService.getCategoryByAlias(alias);
            if (category == null) {
                return Result.error(404, "分类不存在");
            }
            return Result.success(category);
        } catch (Exception e) {
            return Result.error("获取分类失败: " + e.getMessage());
        }
    }

    @PostMapping
    public Result<String> createCategory(@RequestBody Category category) {
        try {
            category.setCreateUserId(1);
            category.setStatus(1);

            boolean success = categoryService.addCategory(category);
            if (success) {
                return Result.success("分类创建成功");
            } else {
                return Result.error("分类创建失败");
            }
        } catch (Exception e) {
            return Result.error("创建分类失败: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Result<String> updateCategory(@PathVariable Integer id, @RequestBody Category category) {
        try {
            category.setId(id);
            boolean success = categoryService.updateCategory(category);
            if (success) {
                return Result.success("分类更新成功");
            } else {
                return Result.error("分类更新失败");
            }
        } catch (Exception e) {
            return Result.error("更新分类失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteCategory(@PathVariable Integer id) {
        try {
            boolean success = categoryService.deleteCategory(id);
            if (success) {
                return Result.success("分类删除成功");
            } else {
                return Result.error("分类删除失败");
            }
        } catch (Exception e) {
            return Result.error("删除分类失败: " + e.getMessage());
        }
    }
}