FROM alpine:3

# Обновить систему
RUN apk update && apk upgrade

# Шрифты
RUN apk add msttcorefonts-installer fontconfig && update-ms-fonts

# Java
RUN apk add openjdk17

# Программы
RUN apk add git openssh-client curl unzip bash ttf-dejavu coreutils tini \
openssl docker

# Файлы
RUN mkdir /root/.jenkins/
COPY --chown=root:root --chmod=754 ./jenkins.war /root/.jenkins/
COPY --chown=root:root --chmod=754 ./jenkins.hash /root/.jenkins/

# Скрипты
RUN mkdir /root/scripts/
COPY --chown=root:root --chmod=754 ./scripts/ /root/scripts/

EXPOSE 80

ENV JAVA_PARAMS=""

ENV JENKINS_PREFIX="/"

# Запуск
ENTRYPOINT ["/root/scripts/start.sh"]

# Готовность
HEALTHCHECK CMD /root/scripts/healthcheck.sh
