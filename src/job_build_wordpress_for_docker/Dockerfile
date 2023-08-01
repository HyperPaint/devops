FROM centos:7

# Обновить систему
RUN yum -y update && yum -y upgrade

# Репозитории
RUN rpm -ivh https://dl.fedoraproject.org/pub/epel/epel-release-latest-7.noarch.rpm && \
	rpm -ivh http://rpms.famillecollet.com/enterprise/remi-release-7.rpm && yum-config-manager --enable remi-php82

# PHP
RUN yum -y install php php-zip php-dom php-xml php-mbstring php-gd php-mysql php-pgsql php-fileinfo php-bz2 php-intl php-ldap php-smbclient php-ftp php-imap php-bcmath php-gmp php-exif php-apcu php-memcached php-redis php-imagick php-pcntl php-phar php-pcre php-ssh2 php-sockets php-process php-opcache

# Программы
RUN yum -y install httpd wget curl openssl gettext

# Настроить php
RUN sed -i 's/memory_limit = 128M/memory_limit = 512M/g' /etc/php.ini && \
	sed -i 's/post_max_size = 8M/post_max_size = 1G/g' /etc/php.ini && \
	sed -i 's/upload_max_filesize = 2M/upload_max_filesize = 1G/g' /etc/php.ini && \
	sed -i 's/output_buffering = 4096/output_buffering = Off/g' /etc/php.ini && \
	echo "apc.enable_cli = 1" >> /etc/php.ini

# Файлы
COPY --chown=apache:apache --chmod=754 ./wordpress/ /var/www/html/
RUN chown apache:apache /var/www/html/

# Конфигурация
COPY --chown=root:root --chmod=754 ./httpd/ /etc/httpd/

# Скрипты
RUN mkdir /root/scripts/
COPY --chown=root:root --chmod=754 ./scripts/ /root/scripts/

# Проверить
RUN httpd -S

EXPOSE 80

# Если эти параметры неверно указаны установка будет завершаться с ошибкой
ENV MY_WP_PROTOCOL="https"
ENV MY_WP_HOME="/"

ENV DB_NAME="database_name_here"
ENV DB_USER="username_here"
ENV DB_PASSWORD="password_here"
ENV DB_HOST="localhost"
ENV DB_CHARSET="utf8mb4"
ENV DB_COLLATE=""

ENV AUTH_KEY="put your unique phrase here"
ENV SECURE_AUTH_KEY="put your unique phrase here"
ENV LOGGED_IN_KEY="put your unique phrase here"
ENV NONCE_KEY="put your unique phrase here"
ENV AUTH_SALT="put your unique phrase here"
ENV SECURE_AUTH_SALT="put your unique phrase here"
ENV LOGGED_IN_SALT="put your unique phrase here"
ENV NONCE_SALT="put your unique phrase here"

# запуск
ENTRYPOINT ["/root/scripts/start.sh"]
