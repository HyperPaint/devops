#!/bin/sh

# Добавить дополнительную конфигурацию в начало файла с заменой <?php
file="wordpress/wp-config-sample.php"
sed -i "s/<?php//g" "${file}"
mv -v "${file}" "${file}.buff" || exit 1
cat "../src/job_build_for_docker/wordpress/wordpress/wp-config.php" "${file}.buff" | tee "${file}" 1>/dev/null
rm -vf "${file}.buff" || exit 1

# Параметризовать файл - параметры базы данных
sed -i "s/define(\\s*'DB_NAME',\\s*'.*'\\s*);/define( 'DB_NAME', '\\\$DB_NAME' );/g" "${file}"
sed -i "s/define(\\s*'DB_USER',\\s*'.*'\\s*);/define( 'DB_USER', '\\\$DB_USER' );/g" "${file}"
sed -i "s/define(\\s*'DB_PASSWORD',\\s*'.*'\\s*);/define( 'DB_PASSWORD', '\\\$DB_PASSWORD' );/g" "${file}"
sed -i "s/define(\\s*'DB_HOST',\\s*'.*'\\s*);/define( 'DB_HOST', '\\\$DB_HOST' );/g" "${file}"
sed -i "s/define(\\s*'DB_CHARSET',\\s*'.*'\\s*);/define( 'DB_CHARSET', '\\\$DB_CHARSET' );/g" "${file}"
sed -i "s/define(\\s*'DB_COLLATE',\\s*'.*'\\s*);/define( 'DB_COLLATE', '\\\$DB_COLLATE' );/g" "${file}"

# Параметризовать файл - уникальные ключи и соли для аутентификации
sed -i "s/define(\\s*'AUTH_KEY',\\s*'.*'\\s*);/define( 'AUTH_KEY', '\\\$AUTH_KEY' );/g" "${file}"
sed -i "s/define(\\s*'SECURE_AUTH_KEY',\\s*'.*'\\s*);/define( 'SECURE_AUTH_KEY', '\\\$SECURE_AUTH_KEY' );/g" "${file}"
sed -i "s/define(\\s*'LOGGED_IN_KEY',\\s*'.*'\\s*);/define( 'LOGGED_IN_KEY', '\\\$LOGGED_IN_KEY' );/g" "${file}"
sed -i "s/define(\\s*'NONCE_KEY',\\s*'.*'\\s*);/define( 'NONCE_KEY', '\\\$NONCE_KEY' );/g" "${file}"
sed -i "s/define(\\s*'AUTH_SALT',\\s*'.*'\\s*);/define( 'AUTH_SALT', '\\\$AUTH_SALT' );/g" "${file}"
sed -i "s/define(\\s*'SECURE_AUTH_SALT',\\s*'.*'\\s*);/define( 'SECURE_AUTH_SALT', '\\\$SECURE_AUTH_SALT' );/g" "${file}"
sed -i "s/define(\\s*'LOGGED_IN_SALT',\\s*'.*'\\s*);/define( 'LOGGED_IN_SALT', '\\\$LOGGED_IN_SALT' );/g" "${file}"
sed -i "s/define(\\s*'NONCE_SALT',\\s*'.*'\\s*);/define( 'NONCE_SALT', '\\\$NONCE_SALT' );/g" "${file}"
