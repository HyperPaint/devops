#!/bin/sh

for file in wordpress/wp-content/themes/*; do
  if [ ! "$file" = "wordpress/wp-content/themes/index.php" ]; then
    rm -rf "$file"
    if [ $? ]; then
      echo "Removed $file"
    else
      echo "Can't remove $file"
      exit 1
    fi
  fi
done

for file in wordpress/wp-content/plugins/*; do
  if [ ! "$file" = "wordpress/wp-content/plugins/index.php" ]; then
    rm -rf "$file"
    if [ $? ]; then
      echo "Removed $file"
    else
      echo "Can't remove $file"
      exit 1
    fi
  fi
done