package org.example.articlesystem.entity;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

@Data
public class Article {
    private Integer id;
    private String title;
    private Integer categoryId;
    private String coverImage;
    private Integer authorId;
    private Integer status = 1;

    // 添加日期格式化注解
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime updateTime;

    // 关联字段
    private String categoryName;
    private String authorName;
    private String categoryAlias;
}