package hyperpaint.util

final class Docker {
    private Docker() { }

    /* Стандартные функции */

    static boolean build(String imageFullName, String dockerfileDirectory, boolean useCache = true) {
        if (useCache) {
            return Shell.shWithStatus("DOCKER_BUILDKIT=1 docker build --tag '${imageFullName}' '${dockerfileDirectory}'")
        } else {
            return Shell.shWithStatus("DOCKER_BUILDKIT=1 docker build --no-cache --tag '${imageFullName}' '${dockerfileDirectory}'")
        }
    }

    static String imagesLatest(String image, boolean q = false) {
        if (q) {
            return Shell.shWithOutput("docker image ls -q ${image}:latest")
        } else {
            return Shell.shWithOutput("docker image ls ${image}:latest")
        }
    }

    static String images(boolean q = false) {
        if (q) {
            return Shell.shWithOutput("docker image ls -q")
        } else {
            return Shell.shWithOutput("docker image ls")
        }
    }

    static String version() {
        return Shell.shWithOutput("docker version")
    }

    static String inspect(String objectID) {
        return Shell.shWithOutput("docker inspect '${objectID}'")
    }

    static boolean load(String tarFile) {
        return Shell.shWithStatus("docker load < '${tarFile}'")
    }

    static boolean rmi(String imageFullName) {
        return Shell.shWithStatus("docker rmi '${imageFullName}'")
    }

    static boolean save(String imageFullName, String tarFile) {
        return Shell.shWithStatus("docker save '${imageFullName}' > '${tarFile}'")
    }

    static boolean tag(String imageFullName1, String imageFullName2) {
        return Shell.shWithStatus("docker tag '${imageFullName1}' '${imageFullName2}'")
    }

    /* Самописные функции */

    static void dockerConnect(String host, String user, String id_rsa, Runnable code) {
        assert(host instanceof String)
        assert(user instanceof String)
        assert (id_rsa instanceof String)

        Shell.echo("Подключаюсь к удалённому компьютеру по ssh...")
        String pid = Shell.shWithOutput("ssh -i '${id_rsa}' -o 'StrictHostKeyChecking=no' -o 'StreamLocalBindUnlink=yes' -N -L '/var/run/docker.sock:/var/run/docker.sock' '${user}@${host}' & echo \$!")
        Thread.sleep(10000)
        try {
            code.run()
        } catch (Exception e) {
            e.printStackTrace()
        } finally {
            Shell.echo("Отключаю ssh от удалённого компьютера...")
            Shell.shOrFail("kill ${pid}")
        }
    }
}
