FROM openjdk:11-jre-slim
RUN apt-get update \
    && apt-get install -y iputils-ping \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*
VOLUME /tmp
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
EXPOSE 8082
ENTRYPOINT ["java","-cp","app:app/lib/*","cn.albumenj.switchmonitor.SwitchMonitorApplication"]