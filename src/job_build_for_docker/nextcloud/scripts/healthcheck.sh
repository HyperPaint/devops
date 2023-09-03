#!/bin/sh

log() {
  # ISO-8601
  echo "[$(date '+%FT%TZ')] [$0] $1"
}

error() {
  # ISO-8601
  echo "[$(date '+%FT%TZ')] [$0] $1" 1>&2
}

healthcheck() {
  curl -f http://localhost:80
  return $?
}

if healthcheck; then
  log "Healthcheck OK"
  exit 0
else
  error "Healthcheck ERROR"
  exit 1
fi