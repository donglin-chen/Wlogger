FROM openjdk:7u131-jre-alpine

MAINTAINER chendl <chendl@youzu.com>

WORKDIR /data/app

COPY ./wlogger-apiserver.jar wlogger-apiserver.jar

EXPOSE 6888

CMD nohup java  -jar wlogger-apiserver.jar && tail -f *