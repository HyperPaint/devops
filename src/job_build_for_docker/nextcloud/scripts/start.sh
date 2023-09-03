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

    log "INSTANCE_ID is $INSTANCE_ID"
    log "PASSWORD_SALT is $PASSWORD_SALT"
    log "SECRET is $SECRET"
    log "TRUSTED_DOMAINS is $TRUSTED_DOMAINS"
    log "DATA_DIRECTORY is $DATA_DIRECTORY"

    log "DB_TYPE is $DB_TYPE"
    log "DB_HOST is $DB_HOST"
    log "DB_NAME is $DB_NAME"
    log "DB_USER is $DB_USER"
    log "DB_PASSWORD is $DB_PASSWORD"
    log "DB_TABLE_PREFIX is $DB_TABLE_PREFIX"

    log "DEFAULT_LANGUAGE is $DEFAULT_LANGUAGE"
    log "FORCE_LANGUAGE is $FORCE_LANGUAGE"
    log "DEFAULT_LOCALE is $DEFAULT_LOCALE"
    log "FORCE_LOCALE is $FORCE_LOCALE"
    log "KNOWLEDGE_BASE_ENABLED is $KNOWLEDGE_BASE_ENABLED"
    log "ALLOW_USER_TO_CHANGE_DISPLAY_NAME is $ALLOW_USER_TO_CHANGE_DISPLAY_NAME"
    log "SKELETON_DIRECTORY is $SKELETON_DIRECTORY"
    log "TEMPLATE_DIRECTORY is $TEMPLATE_DIRECTORY"

    log "MAIL_DOMAIN is $MAIL_DOMAIN"
    log "MAIL_FROM_ADDRESS is $MAIL_FROM_ADDRESS"
    log "MAIL_SMTP_MODE is $MAIL_SMTP_MODE"
    log "MAIL_SMTP_HOST is $MAIL_SMTP_HOST"
    log "MAIL_SMTP_PORT is $MAIL_SMTP_PORT"
    log "MAIL_SMTP_SECURE is $MAIL_SMTP_SECURE"
    log "MAIL_SMTP_AUTH is $MAIL_SMTP_AUTH"
    log "MAIL_SMTP_NAME is $MAIL_SMTP_NAME"
    log "MAIL_SMTP_PASSWORD is $MAIL_SMTP_PASSWORD"

    log "OVERWRITE_HOST is $OVERWRITE_HOST"
    log "OVERWRITE_PROTOCOL is $OVERWRITE_PROTOCOL"
    log "OVERWRITE_WEBROOT is $OVERWRITE_WEBROOT"
    log "OVERWRITE_COND_ADDR is $OVERWRITE_COND_ADDR"
    log "OVERWRITE_CLI_URL is $OVERWRITE_CLI_URL"

    log "MEMCACHE_LOCAL is $MEMCACHE_LOCAL"

    # Сборка конфигурации
    cd "/var/www/html/config/sample/" || error "Can't cd to /var/www/html/config/sample/"
    for file in *; do
      log "Compile '/var/www/html/config/$file' from '/var/www/html/config/sample/$file'"
      # shellcheck disable=SC2016
      envsubst '$INSTANCE_ID $PASSWORD_SALT $SECRET $TRUSTED_DOMAINS $DATA_DIRECTORY $DB_TYPE $DB_HOST $DB_NAME $DB_USER $DB_PASSWORD $DB_TABLE_PREFIX $DEFAULT_LANGUAGE $FORCE_LANGUAGE $DEFAULT_LOCALE $FORCE_LOCALE $KNOWLEDGE_BASE_ENABLED $ALLOW_USER_TO_CHANGE_DISPLAY_NAME $SKELETON_DIRECTORY $TEMPLATE_DIRECTORY $MAIL_DOMAIN $MAIL_FROM_ADDRESS $MAIL_SMTP_MODE $MAIL_SMTP_HOST $MAIL_SMTP_PORT $MAIL_SMTP_SECURE $MAIL_SMTP_AUTH $MAIL_SMTP_NAME $MAIL_SMTP_PASSWORD $OVERWRITE_HOST $OVERWRITE_PROTOCOL $OVERWRITE_WEBROOT $OVERWRITE_COND_ADDR $OVERWRITE_CLI_URL $MEMCACHE_LOCAL' \
        < "/var/www/html/config/sample/$file" \
        > "/var/www/html/config/$file"
      if [ $? ]; then
        chown apache:apache "/var/www/html/config/$file"
        if [ ! $? ]; then
          error "Can't chown /var/www/html/config/$file"
          return 1
        fi

        chmod 754 "/var/www/html/config/$file"
        if [ ! $? ]; then
          error "Can't chmod /var/www/html/config/$file"
          return 1
        fi
      else
        error "Can't compile '/var/www/html/config/$file' from '/var/www/html/config/sample/$file'"
        return 1
      fi
    done

    # Scan files
    log "Scanning user files"
    if [ ! -f "/var/www/html/config/CAN_INSTALL" ]; then
      sudo -u apache php /var/www/html/occ files:scan --all > /root/scripts/scanning-user-files.log
      if [ ! $? ]; then
        error "Can't scan user files"
        #return 1
      fi
    else
      log "Skip scanning user files, app not installed"
    fi

    # Apache fix
    log "Apache fix"
    for file in /var/run/httpd/*; do
      rm -rf "$file"
      if [ $? ]; then
        log "Removed $file"
      else
        log "Can't remove $file"
        return 1
      fi
    done

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
