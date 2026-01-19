#!/bin/bash

# 启动后端服务
echo "🚀 启动后端服务..."
ssh $DEPLOY_USER@$DEPLOY_SERVER << 'EOF'
# 停止旧服务
pkill -f "java -jar app.jar" || true
sleep 3

cd /home/ubuntu/
chmod +x app.jar

# 启动新服务，指定日志文件
nohup java -jar app.jar --server.port=8080 > app.log 2>&1 &
sleep 5

# 检查服务是否启动
if curl -s http://localhost:8080 > /dev/null; then
  echo "✅ 部署成功！访问地址：http://49.233.185.174:8080"
else
  echo "❌ 启动失败，查看日志："
  cat app.log || true
  exit 1
fi
EOF