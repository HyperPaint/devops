#!/bin/sh

for file in nextcloud/themes/*; do
  if [ ! "$file" = "nextcloud/themes/index.php" ]; then
    rm -rf "$file"
    if [ $? ]; then
      echo "Removed $file"
    else
      echo "Can't remove $file"
      exit 1
    fi
  fi
done

for file in nextcloud/apps/*; do
  if [ ! "$file" = "nextcloud/apps/index.php" ]; then
    rm -rf "$file"
    if [ $? ]; then
      echo "Removed $file"
    else
      echo "Can't remove $file"
      exit 1
    fi
  fi
done