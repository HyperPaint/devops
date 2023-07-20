#!/bin/bash

trap 'stop_app' SIGTERM

prepare_app() {
  echo "Preparing..."

  echo "MY_WP_PROTOCOL is $MY_WP_PROTOCOL"
  echo "MY_WP_HOME is $MY_WP_HOME"

	echo "DB_NAME is 'secret'"
	echo "DB_USER is 'secret'"
	echo "DB_PASSWORD is 'secret'"
	echo "DB_HOST is $DB_HOST"
	echo "DB_CHARSET is $DB_CHARSET"
	echo "DB_COLLATE is $DB_COLLATE"

	echo "AUTH_KEY is 'secret'"
	echo "SECURE_AUTH_KEY is 'secret'"
	echo "LOGGED_IN_KEY is 'secret'"
	echo "NONCE_KEY is 'secret'"
	echo "AUTH_SALT is 'secret'"
	echo "SECURE_AUTH_SALT is 'secret'"
	echo "LOGGED_IN_SALT is 'secret'"
	echo "NONCE_SALT is 'secret'"

  # Сборка конфигурации
  echo "Configuration compile started"
    echo "Compile wp-config.php from wp-config-sample.php"
    envsubst '$MY_WP_PROTOCOL $MY_WP_HOME $MY_WP_PORT $DB_NAME $DB_USER $DB_PASSWORD $DB_HOST $DB_CHARSET $DB_COLLATE $AUTH_KEY $SECURE_AUTH_KEY $LOGGED_IN_KEY $NONCE_KEY $AUTH_SALT $SECURE_AUTH_SALT $LOGGED_IN_SALT $NONCE_SALT' \
      < /var/www/html/wp-config-sample.php \
      > /var/www/html/wp-config.php && \
      chown apache:apache /var/www/html/wp-config.php && \
      chmod 754 /var/www/html/wp-config.php
    # Конфигурация существует
    if [[ ! -f '/var/www/html/wp-config.php' ]]; then
      return 1
    fi
  echo "Configuration compile success"

  # Apache fix
  echo Apache fix started
  rm -f /var/run/httpd/* > /dev/null 2>&1

  return 0
}

start_app() {
  echo "Starting..."
  exec httpd -DFOREGROUND
}

stop_app() {
  echo "Stopping..."
  httpd -k stop
}

sleep_app() {
  echo "Something went wrong" 1>&2
  echo "Sleeping 300 seconds..." 1>&2
  sleep '300'
}

prepare_app && start_app || sleep_app
