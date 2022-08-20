#!/bin/sh

# 复制项目的文件到对应docker路径，便于一键生成镜像。
usage() {
	echo "Usage: sh copy.sh"
	exit 1
}


# copy sql
echo "begin copy sql "
cp ../sql/ry_20210908.sql ./mysql/db
cp ../sql/ry_config_20220114.sql ./mysql/db

# copy html
echo "begin copy html "
cp -r ../muzi-ui/dist/** ./nginx/html/dist


# copy jar
echo "begin copy muzi-gateway "
cp ../muzi-gateway/target/muzi-gateway.jar ./muzi/gateway/jar

echo "begin copy muzi-auth "
cp ../muzi-auth/target/muzi-auth.jar ./muzi/auth/jar

echo "begin copy muzi-visual "
cp ../muzi-visual/muzi-monitor/target/muzi-visual-monitor.jar  ./muzi/visual/monitor/jar

echo "begin copy muzi-modules-system "
cp ../muzi-modules/muzi-system/target/muzi-modules-system.jar ./muzi/modules/system/jar

echo "begin copy muzi-modules-file "
cp ../muzi-modules/muzi-file/target/muzi-modules-file.jar ./muzi/modules/file/jar

echo "begin copy muzi-modules-job "
cp ../muzi-modules/muzi-job/target/muzi-modules-job.jar ./muzi/modules/job/jar

echo "begin copy muzi-modules-gen "
cp ../muzi-modules/muzi-gen/target/muzi-modules-gen.jar ./muzi/modules/gen/jar

