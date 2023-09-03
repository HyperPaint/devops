<?php

$mail_domain = '$MAIL_DOMAIN';
$mail_from_address = '$MAIL_FROM_ADDRESS';
$mail_smtp_mode = '$MAIL_SMTP_MODE';
$mail_smtp_host = '$MAIL_SMTP_HOST';
$mail_smtp_port = '$MAIL_SMTP_PORT';
$mail_smtp_secure = '$MAIL_SMTP_SECURE';
$mail_smtp_auth = '$MAIL_SMTP_AUTH';
$mail_smtp_auth_type = '$MAIL_SMTP_AUTH_TYPE';
$mail_smtp_name = '$MAIL_SMTP_NAME';
$mail_smtp_password = '$MAIL_SMTP_PASSWORD';

$CONFIG = array();

if (!empty($mail_domain)) {
    $CONFIG['mail_domain'] = $mail_domain;
}

if (!empty($mail_from_address)) {
    $CONFIG['mail_from_address'] = $mail_from_address;
}

if (!empty($mail_smtp_mode)) {
    $CONFIG['mail_smtpmode'] = $mail_smtp_mode;
}

if (!empty($mail_smtp_host)) {
    $CONFIG['mail_smtphost'] = $mail_smtp_host;
}

if (!empty($mail_smtp_port)) {
    $CONFIG['mail_smtpport'] = $mail_smtp_port;
}

if (!empty($mail_smtp_secure)) {
    $CONFIG['mail_smtpsecure'] = $mail_smtp_secure;
}

if (!empty($mail_smtp_auth)) {
    $CONFIG['mail_smtpauth'] = $mail_smtp_auth;
}

if (!empty($mail_smtp_auth_type)) {
    $CONFIG['mail_smtpauthtype'] = $mail_smtp_auth_type;
}

if (!empty($mail_smtp_name)) {
    $CONFIG['mail_smtpname'] = $mail_smtp_name;
}

if (!empty($mail_smtp_password)) {
    $CONFIG['mail_smtppassword'] = $mail_smtp_password;
}
