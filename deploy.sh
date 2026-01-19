#!/bin/bash

# 启动后端服务
echo "🚀 启动后端服务..."
ssh $DEPLOY_USER@$DEPLOY_SERVER << 'EOF'
pkill -f "java -jar $JAR_NAME" || true
sleep 3
cd /home/$DEPLOY_USER/
nohup java -jar $JAR_NAME --server.port=8080 > /dev/null 2>&1 &
sleep 5
if pgrep -f "java -jar $JAR_NAME" > /dev/null; then
  echo "✅ 部署成功！访问地址：http://$DEPLOY_SERVER:8080"
else
  echo "❌ 启动失败，请检查日志"
  exit 1
fi
EOF