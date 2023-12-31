package hyperpaint.util

final class Util {
    private Util() { }

    static void downloadFile(String url, String file, Integer cacheLifetime = Integer.MAX_VALUE) {
        // Поиск существующего файла
        Integer fileModifiedXSecondsAgo = Integer.MAX_VALUE
        if (Shell.shGetStatus("[[ -f '${file}' ]]")) {
            fileModifiedXSecondsAgo = Shell.shGetOutput("echo \"\$(date +%s)-\$(date -r '${file}' +%s)\" | bc").toInteger()
        }

        // Проверка актуальности файла
        if (fileModifiedXSecondsAgo >= cacheLifetime) {
            Shell.echo("Скачиваю файл ${file}")
            if (!Shell.shGetStatus("wget -O '${file}' '${url}'")) {
                Shell.echo("Скачиваю файл ${file} без проверки сертификата")
                Shell.sh("wget -O '${file}' --no-check-certificate '${url}'")
            }
        } else {
            Shell.echo("Пропускаю скачивание файла ${file}")
        }
    }

    static void unarchiveFile(String file, String destination, boolean deleteDestinationDirectoryIfExists = false) {
        if (Shell.shGetStatus("[[ -d '${destination}' ]]")) {
            // Директория есть
            if (deleteDestinationDirectoryIfExists) {
                Shell.sh("rm -rf '${destination}'")
                Shell.sh("mkdir '${destination}'")
            }
        } else {
            // Директории нет
            Shell.sh("mkdir '${destination}'")
        }
        if (Regex.find(file, "^.*\\.zip\$")) {
            Shell.echo("Распаковываю файл ${file} как zip архив")
            Shell.sh("unzip -q '${file}' -d '${destination}'")
        } else if (Regex.find(file, "^.*\\.rar\$")) {
            // Не поддерживается alpine
            Shell.error("Формат rar не поддерживается")
        } else if (Regex.find(file, "^.*\\.tar\\.gz\$")) {
            Shell.echo("Распаковываю файл ${file} как tar.gz архив")
            Shell.sh("tar x -zf '${file}' -C '${destination}'")
        } else if (Regex.find(file, "^.*\\.tar\\.xz\$")) {
            Shell.echo("Распаковываю файл ${file} как tar.xz архив")
            Shell.sh("tar x -Jf '${file}' -C '${destination}'")
        } else if (Regex.find(file, "^.*\\.tar\\.bz2\$")) {
            Shell.echo("Распаковываю файл ${file} как tar.bz2 архив")
            Shell.sh("tar x -jf '${file}' -C '${destination}'")
        } else if (Regex.find(file, "^.*\\.tar\$")) {
            Shell.echo("Распаковываю файл ${file} как tar архив")
            Shell.sh("tar x -f '${file}' -C '${destination}'")
        } else {
            Shell.error("Формат не поддерживается")
        }
    }
}
