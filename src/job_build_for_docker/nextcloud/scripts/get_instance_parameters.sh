#!/bin/sh

grep -ioE "'instanceid'\s*=>\s*'.+'|'passwordsalt'\s*=>\s*'.+'|'secret'\s*=>\s*'.+'|'version'\s*=>\s*'.+'|'installed'\s*=>\s*[a-z]+" /var/www/html/config/*config.php