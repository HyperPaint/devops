#!/bin/sh

# Удалить "временные файлы" приложения
rm -rf /var/www/html/data/appdata_*/css/* /var/www/html/data/appdata_*/js/*
# Произвести сканирование и восстановить
php /var/www/html/occ files:scan-app-data
