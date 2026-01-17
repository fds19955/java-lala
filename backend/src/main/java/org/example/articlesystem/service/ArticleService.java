package org.example.articlesystem.service;

import org.example.articlesystem.entity.Article;
import org.example.articlesystem.entity.Category;
import org.example.articlesystem.entity.User;
import org.example.articlesystem.mapper.ArticleMapper;
import org.example.articlesystem.mapper.CategoryMapper;
import org.example.articlesystem.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private UserMapper userMapper;

    public boolean addArticle(Article article) {
        try {
            return articleMapper.insertArticle(article) > 0;
        } catch (Exception e) {
            throw new RuntimeException("添加文章失败: " + e.getMessage());
        }
    }

    public boolean updateArticle(Article article) {
        try {
            return articleMapper.updateArticle(article) > 0;
        } catch (Exception e) {
            throw new RuntimeException("更新文章失败: " + e.getMessage());
        }
    }

    public boolean deleteArticle(Integer id) {
        try {
            return articleMapper.deleteArticleById(id) > 0;
        } catch (Exception e) {
            throw new RuntimeException("删除文章失败: " + e.getMessage());
        }
    }

    public Article getArticleById(Integer id) {
        return articleMapper.selectArticleById(id);
    }

    public Map<String, Object> getArticleList(Integer page, Integer size,
                                              Integer categoryId, String keyword) {
        Map<String, Object> result = new HashMap<>();

        if (page == null || page < 1) page = 1;
        if (size == null || size < 1) size = 10;

        Map<String, Object> params = new HashMap<>();
        params.put("categoryId", categoryId);
        params.put("keyword", keyword);
        params.put("start", (page - 1) * size);
        params.put("limit", size);

        List<Article> articles = articleMapper.selectArticleList(params);
        int total = articleMapper.selectArticleCount(params);

        result.put("articles", articles);
        result.put("total", total);
        result.put("page", page);
        result.put("size", size);
        result.put("totalPages", (int) Math.ceil((double) total / size));

        return result;
    }
}