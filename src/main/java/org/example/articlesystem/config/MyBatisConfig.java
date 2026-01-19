package org.example.articlesystem.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class MyBatisConfig {

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) throws IOException {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        // 设置数据源
        sessionFactory.setDataSource(dataSource);
        // 设置类型别名包（根据您的实体类包修改）
        sessionFactory.setTypeAliasesPackage("org.example.articlesystem.entity");
        // 设置mapper.xml文件的位置（模式匹配）
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/**/*.xml"));
        return sessionFactory;
    }
}