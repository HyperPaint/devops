<?php

$my_wp_protocol = '$MY_WP_PROTOCOL';
$my_wp_home = '$MY_WP_HOME';

/** HTTPS */
if ($my_wp_protocol == 'https') {
    $_SERVER['REQUEST_SCHEME'] = $my_wp_protocol;
    $_SERVER['HTTPS'] = 'on';
}

if ($my_wp_home != '') {
    /** Wordpress as subdirectory */
    define( 'WP_HOME', $my_wp_home );
    define( 'WP_SITEURL', $my_wp_home );

    /** Fix to get the dashboard working with the reverse proxy. */
    $count = 1;
    $_SERVER['REQUEST_URI'] = str_replace('/wp-admin/', $my_wp_home . '/wp-admin/',  $_SERVER['REQUEST_URI'], $count);
}

/** Disable auto-update */
define( 'WP_AUTO_UPDATE_CORE', false );
/** WP Super Cache plugin */
define( 'WP_CACHE', true );
define( 'WPCACHEHOME', '/var/www/html/wp-content/plugins/wp-super-cache/' );
