# 构建阶段：使用Node.js安装依赖并构建Vue3项目
FROM node:20-alpine AS builder

WORKDIR /app

# 复制包管理文件（优先复制以利用Docker缓存）
COPY package.json ./
COPY pnpm-lock.yaml ./

# 设置镜像源、安装pnpm并安装项目依赖（合并指令并清理缓存）
RUN npm config set registry https://registry.npmmirror.com \
    && npm install -g pnpm \
    && pnpm config set store-dir /root/.pnpm-store \
    && pnpm config set registry https://registry.npmmirror.com \
    && pnpm install \
    && npm cache clean --force \
    && pnpm store prune

# 复制源代码
COPY . .

# 执行构建（产物通常在 dist/ 目录）
RUN pnpm run build

# 生产阶段：使用Nginx提供静态文件服务
FROM nginx:alpine

# 将构建好的静态文件复制到Nginx的默认服务目录
COPY --from=builder /app/dist /usr/share/nginx/html

# 复制自定义的Nginx配置文件
COPY nginx.conf /etc/nginx/conf.d/default.conf

# 暴露80端口
EXPOSE 80

# 启动Nginx
CMD ["nginx", "-g", "daemon off;"]
