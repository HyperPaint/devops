package hyperpaint.util

final class Util {
    private Util() { }

    static void downloadFile(String url, String file, Integer cacheLifetime = Integer.MAX_VALUE) {
        // Поиск существующего файла
        Integer fileModifiedXSecondsAgo = Integer.MAX_VALUE
        if (Shell.shWithStatus("[[ -f '${file}' ]]")) {
            fileModifiedXSecondsAgo = Shell.shWithOutput("echo \"\$(date +%s)-\$(date -r '${file}' +%s)\" | bc").toInteger()
        }

        // Проверка актуальности файла
        if (fileModifiedXSecondsAgo >= cacheLifetime) {
            Shell.echo("Скачиваю файл ${file}")
            Shell.shOrFail("wget -O '${file}' '${url}'")
        } else {
            Shell.echo("Пропускаю скачивание файла ${file}")
        }
    }
}
