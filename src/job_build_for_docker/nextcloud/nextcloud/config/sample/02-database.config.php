<?php

$db_type = '$DB_TYPE';
$db_host = '$DB_HOST';
$db_name = '$DB_NAME';
$db_user = '$DB_USER';
$db_password = '$DB_PASSWORD';
$db_table_prefix = '$DB_TABLE_PREFIX';

$CONFIG = array();

if (!empty($db_type)) {
    $CONFIG['dbtype'] = $db_type;
}

if (!empty($db_host)) {
    $CONFIG['dbhost'] = $db_host;
}

if (!empty($db_name)) {
    $CONFIG['dbname'] = $db_name;
}

if (!empty($db_user)) {
    $CONFIG['dbuser'] = $db_user;
}

if (!empty($db_password)) {
    $CONFIG['dbpassword'] = $db_password;
}

if (!empty($db_table_prefix)) {
    $CONFIG['dbtableprefix'] = $db_table_prefix;
}

$CONFIG['mysql.utf8mb4'] = true;
