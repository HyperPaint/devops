#!/bin/sh

sudo -u apache php "/var/www/html/occ" files:scan --all | tee /root/scripts/scanning-user-files.log