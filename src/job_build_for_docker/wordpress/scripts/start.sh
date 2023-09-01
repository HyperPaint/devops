#!/bin/sh

pid=$$

trap "stop_app" 2 15

log() {
  # ISO-8601
  echo "[$(date '+%FT%TZ')] [$0] $1"
}

error() {
  # ISO-8601
  echo "[$(date '+%FT%TZ')] [$0] $1" 1>&2
}

prepare_app() {
  log "Preparing..."
  prepare_date=$(date "+%s")
  ### Начало ###

  log "MY_WP_PROTOCOL is $MY_WP_PROTOCOL"
  log "MY_WP_HOME is $MY_WP_HOME"

  log "DB_NAME is $DB_NAME"
  log "DB_USER is $DB_USER"
  log "DB_PASSWORD is $DB_PASSWORD"
  log "DB_HOST is $DB_HOST"
  log "DB_CHARSET is $DB_CHARSET"
  log "DB_COLLATE is $DB_COLLATE"

  log "AUTH_KEY is $AUTH_KEY"
  log "SECURE_AUTH_KEY is $SECURE_AUTH_KEY"
  log "LOGGED_IN_KEY is $LOGGED_IN_KEY"
  log "NONCE_KEY is $NONCE_KEY"
  log "AUTH_SALT is $AUTH_SALT"
  log "SECURE_AUTH_SALT is $SECURE_AUTH_SALT"
  log "LOGGED_IN_SALT is $LOGGED_IN_SALT"
  log "NONCE_SALT is $NONCE_SALT"

  # Сборка конфигурации
  log "Compile 'wp-config.php' from 'wp-config-sample.php'"
  # shellcheck disable=SC2016
  envsubst '$MY_WP_PROTOCOL $MY_WP_HOME $MY_WP_PORT $DB_NAME $DB_USER $DB_PASSWORD $DB_HOST $DB_CHARSET $DB_COLLATE $AUTH_KEY $SECURE_AUTH_KEY $LOGGED_IN_KEY $NONCE_KEY $AUTH_SALT $SECURE_AUTH_SALT $LOGGED_IN_SALT $NONCE_SALT' \
    < "/var/www/html/wp-config-sample.php" \
    > "/var/www/html/wp-config.php" && \
    chown apache:apache "/var/www/html/wp-config.php" && \
    chmod 754 "/var/www/html/wp-config.php"

  # Apache fix
  log "Apache fix"
  rm -f /var/run/httpd/* > /dev/null 2>&1

  ### Конец ###
  wait
  prepared_time=$(echo "$(date '+%s') - $prepare_date" | bc)
  log "Prepared in $prepared_time seconds"
  return 0
}

start_app() {
  log "Starting..."
  start_date=$(date "+%s")
  ### Начало ###

  httpd -DFOREGROUND &

  ### Конец ###
  pid=$!
  if [ $pid = -1 ]; then
    error "Can't start process"
    return 1
  else
    started_time=$(echo "$(date '+%s') - $start_date" | bc)
    log "Started in $started_time seconds"
    wait
    return 0
  fi
}

stop_app() {
  log "Stopping..."
  stop_date=$(date "+%s")
  if [ $pid = $$ ]; then
    log "Killing self"
    kill -9 $pid
  else
    log "Killing pid $pid"
    kill $pid || error "Can't kill pid $pid"
    wait
  fi
  stopped_time=$(echo "$(date '+%s') - $stop_date" | bc)
  log "Stopped in $stopped_time seconds"
  return 0
}

sleep_app() {
  error "Something went wrong"
  error "Sleeping 10 minutes..."
  sleep "10m"
  exit 1
}

if prepare_app; then
    start_app
else
    sleep_app
fi

log "Exited"
