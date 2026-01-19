package org.example.articlesystem.controller;

import org.example.articlesystem.common.Result;
import org.example.articlesystem.entity.Article;
import org.example.articlesystem.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize; // 关键导入
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping
//    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")    未登录，返回403
    public Result<Map<String, Object>> getArticleList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String keyword) {

        try {
            Map<String, Object> result = articleService.getArticleList(page, size, categoryId, keyword);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error("获取文章列表失败: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Result<Article> getArticleDetail(@PathVariable Integer id) {
        try {
            Article article = articleService.getArticleById(id);
            if (article == null) {
                return Result.error(404, "文章不存在");
            }
            return Result.success(article);
        } catch (Exception e) {
            return Result.error("获取文章详情失败: " + e.getMessage());
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> createArticle(@RequestBody Article article) {
        try {
            // 从登录状态获取作者ID
            article.setAuthorId(1);
            article.setStatus(1);

            boolean success = articleService.addArticle(article);
            if (success) {
                return Result.success("文章发布成功");
            } else {
                return Result.error("文章发布失败");
            }
        } catch (Exception e) {
            return Result.error("发布文章失败: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Result<String> updateArticle(@PathVariable Integer id, @RequestBody Article article) {
        try {
            article.setId(id);
            boolean success = articleService.updateArticle(article);
            if (success) {
                return Result.success("文章更新成功");
            } else {
                return Result.error("文章更新失败");
            }
        } catch (Exception e) {
            return Result.error("更新文章失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> deleteArticle(@PathVariable Integer id) {
        try {
            boolean success = articleService.deleteArticle(id);
            if (success) {
                return Result.success("文章删除成功");
            } else {
                return Result.error("文章删除失败");
            }
        } catch (Exception e) {
            return Result.error("删除文章失败: " + e.getMessage());
        }
    }
}