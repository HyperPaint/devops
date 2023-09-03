#!/bin/sh

# Удалять не нужно, т.к. нет тем
#for file in nextcloud/themes/*; do
#  if [ ! "$file" = "nextcloud/themes/index.php" ]; then
#    rm -rf "$file"
#    if [ $? ]; then
#      echo "Removed $file"
#    else
#      echo "Can't remove $file"
#      exit 1
#    fi
#  fi
#done

# Удалять не нужно, т.к. плагины в поставке не предназначены для удаления
#for file in nextcloud/apps/*; do
#  if [ ! "$file" = "nextcloud/apps/index.php" ]; then
#    rm -rf "$file"
#    if [ $? ]; then
#      echo "Removed $file"
#    else
#      echo "Can't remove $file"
#      exit 1
#    fi
#  fi
#done