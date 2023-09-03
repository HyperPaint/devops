<?php

$overwrite_protocol = '$OVERWRITE_PROTOCOL';

/** HTTPS */
if ($overwrite_protocol == 'https') {
    $_SERVER['REQUEST_SCHEME'] = $overwrite_protocol;
    $_SERVER['HTTPS'] = 'on';
}

$instance_id = '$INSTANCE_ID';
$password_salt = '$PASSWORD_SALT';
$secret = '$SECRET';
$trusted_domains = '$TRUSTED_DOMAINS';
$data_directory = '$DATA_DIRECTORY';
$version = '$VERSION';
$installed = '$INSTALLED';

$CONFIG = array();

if (!empty($instance_id)) {
    $CONFIG['instanceid'] = $instance_id;
}

if (!empty($password_salt)) {
    $CONFIG['passwordsalt'] = $password_salt;
}

if (!empty($secret)) {
    $CONFIG['secret'] = $secret;
}

if (!empty($trusted_domains)) {
    $CONFIG['trusted_domains'] = $trusted_domains;
}

if (!empty($data_directory)) {
    $CONFIG['datadirectory'] = $data_directory;
}

if (!empty($version)) {
    $CONFIG['version'] = $version;
}

if (!empty($installed)) {
    $CONFIG['installed'] = $installed;
}
