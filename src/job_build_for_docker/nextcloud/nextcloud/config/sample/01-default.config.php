<?php

$overwrite_protocol = '$OVERWRITE_PROTOCOL'

/** HTTPS */
if ($overwrite_protocol == 'https') {
    $_SERVER['REQUEST_SCHEME'] = $overwrite_protocol;
    $_SERVER['HTTPS'] = 'on';
}

$CONFIG = array (
    'instanceid' => '$INSTANCE_ID',
    'passwordsalt' => '$PASSWORD_SALT',
    'secret' => '$SECRET',
    'trusted_domains' => $TRUSTED_DOMAINS,
    'datadirectory' => '$DATA_DIRECTORY',
);
