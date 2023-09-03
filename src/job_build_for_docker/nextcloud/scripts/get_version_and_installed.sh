#!/bin/sh

grep -ioE "'version'\s*=>\s*'[0-9\.]+'|'installed'\s*=>\s*[a-z]+" /var/www/html/config/*config.php