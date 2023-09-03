FROM centos:7

# Обновить систему
RUN yum -y update && yum -y upgrade

# Репозитории
RUN rpm -ivh https://dl.fedoraproject.org/pub/epel/epel-release-latest-7.noarch.rpm && \
    rpm -ivh http://rpms.famillecollet.com/enterprise/remi-release-7.rpm && yum-config-manager --enable remi-php80

# PHP
RUN yum -y install php php-zip php-dom php-xml php-mbstring php-gd php-mysql php-pgsql php-fileinfo php-bz2 php-intl php-ldap php-smbclient php-ftp php-imap php-bcmath php-gmp php-exif php-apcu php-memcached php-redis php-imagick php-pcntl php-phar php-pcre php-ssh2 php-sockets php-process php-opcache

# Программы
RUN yum -y install httpd wget curl openssl gettext

# Специфичное
RUN yum -y install sudo

# Настроить php
RUN sed -i 's/memory_limit = 128M/memory_limit = 512M/g' /etc/php.ini && \
    sed -i 's/post_max_size = 8M/post_max_size = 10G/g' /etc/php.ini && \
    sed -i 's/upload_max_filesize = 2M/upload_max_filesize = 10G/g' /etc/php.ini && \
    sed -i 's/output_buffering = 4096/output_buffering = Off/g' /etc/php.ini && \
    echo "apc.enable_cli = 1" >> /etc/php.ini
	
# Файлы
COPY --chown=apache:apache --chmod=754 ./nextcloud/ /var/www/html/
RUN chown apache:apache /var/www/html/

# Конфигурация
COPY --chown=root:root --chmod=754 ./httpd/ /etc/httpd/

# Скрипты
RUN mkdir /root/scripts/
COPY --chown=root:root --chmod=754 ./scripts/ /root/scripts/

# Проверить
RUN httpd -S

EXPOSE 80

# https://docs.nextcloud.com/server/latest/admin_manual/configuration_server/config_sample_php_parameters.html
# Обязательные
ENV INSTANCE_ID=""
ENV PASSWORD_SALT=""
ENV SECRET=""
ENV TRUSTED_DOMAINS="['*']"
ENV DATA_DIRECTORY="/var/www/html/data"
ENV VERSION=""
ENV INSTALLED=""

# Обязательные, база данных
ENV DB_TYPE=""
ENV DB_HOST=""
ENV DB_NAME=""
ENV DB_USER=""
ENV DB_PASSWORD=""
ENV DB_TABLE_PREFIX=""

# Дополнительные
ENV DEFAULT_LANGUAGE="ru"
ENV FORCE_LANGUAGE="false"
ENV DEFAULT_LOCALE="ru"
ENV FORCE_LOCALE="false"
# Help menu item in the user menu
ENV KNOWLEDGE_BASE_ENABLED=""
# Allows users to change their display names on their personal pages
ENV ALLOW_USER_TO_CHANGE_DISPLAY_NAME=""
# The directory where the skeleton files are located
ENV SKELETON_DIRECTORY="/var/www/html/core/skeleton"
# The directory where the template files are located
ENV TEMPLATE_DIRECTORY="/var/www/html/templates"

# Дополнительные, почта
ENV MAIL_DOMAIN=""
ENV MAIL_FROM_ADDRESS=""
ENV MAIL_SMTP_MODE=""
ENV MAIL_SMTP_HOST=""
ENV MAIL_SMTP_PORT=""
ENV MAIL_SMTP_SECURE=""
ENV MAIL_SMTP_AUTH=""
ENV MAIL_SMTP_AUTH_TYPE=""
ENV MAIL_SMTP_NAME=""
ENV MAIL_SMTP_PASSWORD=""

# Дополнительные, прокси
ENV OVERWRITE_HOST=""
ENV OVERWRITE_PROTOCOL=""
ENV OVERWRITE_WEBROOT=""
ENV OVERWRITE_COND_ADDR=""
ENV OVERWRITE_CLI_URL=""

# Дополнительные, кэш
ENV MEMCACHE_LOCAL="\OC\Memcache\APCu"

# Запуск
ENTRYPOINT ["/root/scripts/start.sh"]

# Готовность
HEALTHCHECK CMD /root/scripts/healthcheck.sh
