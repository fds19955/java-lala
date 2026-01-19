package org.example.articlesystem.mapper;

import org.example.articlesystem.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Mapper
public interface ArticleMapper {
    int insertArticle(Article article);
    int deleteArticleById(Integer id);
    int updateArticle(Article article);
    Article selectArticleById(Integer id);
    List<Article> selectArticleList(Map<String, Object> params);
    int selectArticleCount(Map<String, Object> params);
}