<?php

$memcache_local = '$MEMCACHE_LOCAL';

$CONFIG = array();

if (!empty($memcache_local)) {
    $CONFIG['memcache.local'] = $memcache_local;
}
