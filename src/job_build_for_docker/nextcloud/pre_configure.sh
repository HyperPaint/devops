#!/bin/sh

cd "nextcloud/themes/" || exit 1
for file in *; do
  if [ ! "$file" = "index.php" ]; then
    rm -rf "nextcloud/themes/$file"
    if [ $? ]; then
      echo "Removed nextcloud/themes/$file"
    else
      echo "Can't remove nextcloud/themes/$file"
      exit 1
    fi
  fi
done

cd "../../"

cd "nextcloud/apps/" || exit 1
for file in *; do
  if [ ! "$file" = "index.php" ]; then
    rm -rf "nextcloud/apps/$file"
    if [ $? ]; then
      echo "Removed nextcloud/apps/$file"
    else
      echo "Can't remove nextcloud/apps/$file"
      exit 1
    fi
  fi
done