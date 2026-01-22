## 多阶段构建：构建阶段
#FROM maven:3.8-openjdk-17 AS builder
#WORKDIR /app
#COPY pom.xml .
#RUN mvn dependency:go-offline -B # 此步骤仅在 pom.xml 变化时才会重新运行
#COPY src ./src
#RUN mvn clean package -DskipTests  # 跳过测试以加快构建
#RUN ls -la /app/target/

# 运行阶段：使用 Eclipse Temurin JRE 镜像替代 OpenJDK
FROM eclipse-temurin:17-jre
WORKDIR /app
#下面这个路径很容易搞错，一定要是/root/article-app/backend/target/app.jar下的，之前是..../app.jar
COPY target/app.jar app.jar

# 设置时区
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
