<?php

$default_language = '$DEFAULT_LANGUAGE';
$force_language = '$FORCE_LANGUAGE';
$default_locale = '$DEFAULT_LOCALE';
$force_locale = '$FORCE_LOCALE';
$knowledge_base_enabled = $KNOWLEDGE_BASE_ENABLED;
$allow_user_to_change_display_name = $ALLOW_USER_TO_CHANGE_DISPLAY_NAME;
$skeleton_directory = '$SKELETON_DIRECTORY';
$template_directory = '$TEMPLATE_DIRECTORY';

$CONFIG = array();

if (!empty($default_language)) {
    $CONFIG['default_language'] = $default_language;
}

if (!empty($force_language)) {
    $CONFIG['force_language'] = $force_language;
}

if (!empty($default_locale)) {
    $CONFIG['default_locale'] = $default_locale;
}

if (!empty($force_locale)) {
    $CONFIG['force_locale'] = $force_locale;
}

if (!empty($knowledge_base_enabled)) {
    $CONFIG['knowledgebaseenabled'] = $knowledge_base_enabled;
}

if (!empty($allow_user_to_change_display_name)) {
    $CONFIG['allow_user_to_change_display_name'] = $allow_user_to_change_display_name;
}

if (!empty($skeleton_directory)) {
    $CONFIG['skeletondirectory'] = $skeleton_directory;
}

if (!empty($template_directory)) {
    $CONFIG['templatedirectory'] = $template_directory;
}
