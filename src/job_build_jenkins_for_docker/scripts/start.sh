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

  log "JAVA_PARAMS is $JAVA_PARAMS"

  log "JENKINS_PREFIX is $JENKINS_PREFIX"

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

  tini -s -- java $JAVA_PARAMS -jar /root/.jenkins/jenkins.war --prefix=$JENKINS_PREFIX --httpPort=80 &

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
    if ! start_app; then
      sleep_app
    fi
else
    sleep_app
fi

log "Exited"
