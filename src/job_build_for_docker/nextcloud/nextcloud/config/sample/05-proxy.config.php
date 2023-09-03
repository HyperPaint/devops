<?php

$overwrite_host = '$OVERWRITE_HOST';
$overwrite_protocol = '$OVERWRITE_PROTOCOL';
$overwrite_webroot = '$OVERWRITE_WEBROOT';
$overwrite_cond_addr = '$OVERWRITE_COND_ADDR';
$overwrite_cli_url = '$OVERWRITE_CLI_URL';

$CONFIG = array();

if (!empty($overwrite_host)) {
    $CONFIG['overwritehost'] = $overwrite_host;
}

if (!empty($overwrite_protocol)) {
    $CONFIG['overwriteprotocol'] = $overwrite_protocol;
}

if (!empty($overwrite_webroot)) {
    $CONFIG['overwritewebroot'] = $overwrite_webroot;
}

if (!empty($overwrite_cond_addr)) {
    $CONFIG['overwritecondaddr'] = $overwrite_cond_addr;
}

if (!empty($overwrite_cli_url)) {
    $CONFIG['overwrite.cli.url'] = $overwrite_cli_url;
}
