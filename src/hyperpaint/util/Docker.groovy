package hyperpaint.util

final class Docker {
    private Docker() { }

    /* Стандартные функции */

    static boolean build(String imageFullName, String dockerfileDirectory, boolean useCache = true) {
        if (useCache) {
            return Shell.executeWithStatus("DOCKER_BUILDKIT=1 docker build --tag '${imageFullName}' '${dockerfileDirectory}'")
        } else {
            return Shell.executeWithStatus("DOCKER_BUILDKIT=1 docker build --no-cache --tag '${imageFullName}' '${dockerfileDirectory}'")
        }
    }

    static String images(boolean q = false) {
        if (q) {
            return Shell.executeWithOutput("docker image ls -q")
        } else {
            return Shell.executeWithOutput("docker image ls")
        }
    }

    static String version() {
        return Shell.executeWithOutput("docker version")
    }

    static String inspect(String objectID) {
        return Shell.executeWithOutput("docker inspect '${objectID}'")
    }

    static boolean load(String tarFile) {
        return Shell.executeWithStatus("docker load < '${tarFile}'")
    }

    static boolean rmi(String imageFullName) {
        return Shell.executeWithStatus("docker rmi '${imageFullName}'")
    }

    static boolean save(String imageFullName, String tarFile) {
        return Shell.executeWithStatus("docker save '${imageFullName}' > '${tarFile}'")
    }

    static boolean tag(String imageFullName1, String imageFullName2) {
        return Shell.executeWithStatus("docker tag '${imageFullName1}' '${imageFullName2}'")
    }

    /* Самописные функции */

    static void executeRemote(String host, String user, String id_rsa, Runnable code) {
        Shell.echo("Подключаюсь к удалённому компьютеру по ssh...")
        String pid = Shell.executeWithOutput("ssh -i '${id_rsa}' -o 'StrictHostKeyChecking=no' -o 'StreamLocalBindUnlink=yes' -N -L '/var/run/docker.sock:/var/run/docker.sock' '${user}@${host}' & echo \$!")
        Thread.sleep(10000)
        try {
            code.run()
        } catch (Exception e) {
            e.printStackTrace()
        } finally {
            Shell.echo("Отключаю ssh от удалённого компьютера...")
            Shell.executeWithStatus("kill ${pid}")
        }
    }
}
