#!/bin/sh
cd "wordpress/wp-content/themes" || exit 1
for file in *; do
  if [ -f "$file" ]; then
    if [ ! "$file" = "index.php" ]; then
      rm -rf "$file"
    fi
  fi
done

cd "../../.." || exit 1

cd "wordpress/wp-content/plugins" || exit 1
for file in *; do
  if [ -f "$file" ]; then
    if [ ! "$file" = "index.php" ]; then
      rm -rf "$file"
    fi
  fi
done