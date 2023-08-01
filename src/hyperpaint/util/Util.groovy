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
            if (!Shell.shWithStatus("wget -O '${file}' '${url}'")) {
                Shell.echo("Скачиваю файл ${file} без проверки сертификата")
                Shell.shOrFail("wget -O '${file}' --no-check-certificate '${url}'")
            }
        } else {
            Shell.echo("Пропускаю скачивание файла ${file}")
        }
    }

    static void unarchiveFile(String file, String destination, boolean deleteDestinationDirectoryIfExists = false) {
        if (Shell.shWithStatus("[[ -d '${destination}' ]]")) {
            // Директория есть
            if (deleteDestinationDirectoryIfExists) {
                Shell.shOrFail("rm -rf '${destination}'")
                Shell.shOrFail("mkdir '${destination}'")
            }
        } else {
            // Директории нет
            Shell.shOrFail("mkdir '${destination}'")
        }
        if (Regex.find(file, "^.*.zip\$")) {
            Shell.echo("Распаковываю файл ${file} как zip архив")
            Shell.shOrFail("unzip -q '${file}' -d '${destination}'")
        } else if (Regex.find(file, "^.*.rar\$")) {
            // Не поддерживается alpine
            Shell.echo("Распаковываю файл ${file} как rar архив")
            Shell.error("Формат rar не поддерживается")
        } else if (Regex.find(file, "^.*.tar.gz\$")) {
            Shell.echo("Распаковываю файл ${file} как tar.gz архив")
            Shell.shOrFail("tar x -zf '${file}' -C '${destination}'")
        } else if (Regex.find(file, "^.*.tar.xz\$")) {
            Shell.echo("Распаковываю файл ${file} как tar.xz архив")
            Shell.shOrFail("tar x -Jf '${file}' -C '${destination}'")
        } else if (Regex.find(file, "^.*.tar\$")) {
            Shell.echo("Распаковываю файл ${file} как tar архив")
            Shell.shOrFail("tar x -f '${file}' -C '${destination}'")
        }
    }
}
