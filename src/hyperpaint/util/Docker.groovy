package hyperpaint.util

final class Docker {
    private Docker() { }

    /* Стандартные функции */

    static boolean executeCommand(String container, String command) {
        return Shell.shGetStatus("docker exec -it ${container} ${command}")
    }

    static String listContainers(boolean all = false, quiet = false) {
        if (all) {
            if (quiet) {
                return Shell.shGetOutput("docker ps -aq")
            } else {
                return Shell.shGetOutput("docker ps -a")
            }
        } else {
            if (quiet) {
                return Shell.shGetOutput("docker ps -q")
            } else {
                return Shell.shGetOutput("docker ps")
            }
        }
    }

    static boolean build(String dockerfileDirectory, String image, boolean useCache = true) {
        if (useCache) {
            return Shell.shGetStatus("DOCKER_BUILDKIT=1 docker build --tag ${image} ${dockerfileDirectory}")
        } else {
            return Shell.shGetStatus("DOCKER_BUILDKIT=1 docker build --no-cache --tag ${image} ${dockerfileDirectory}")
        }
    }

    static boolean pull(String image) {
        return Shell.shGetStatus("docker pull ${image}")
    }

    static boolean push(String image) {
        return Shell.shGetStatus("docker push ${image}")
    }

    static String images(boolean q = false) {
        if (q) {
            return Shell.shGetOutput("docker image ls -q")
        } else {
            return Shell.shGetOutput("docker image ls")
        }
    }

    static String images(String image, boolean q = false) {
        if (q) {
            return Shell.shGetOutput("docker image ls -q ${image}")
        } else {
            return Shell.shGetOutput("docker image ls ${image}")
        }
    }

    static boolean login(String username, String password, String dockerServer = "") {
        return Shell.shGetStatus("docker login --username ${username} --password ${password} ${dockerServer}")
    }

    static boolean logout(String dockerServer = "") {
        return Shell.shGetStatus("docker logout ${dockerServer}")
    }

    static String version() {
        return Shell.shGetOutput("docker version")
    }

    static String info() {
        return Shell.shGetOutput("docker info")
    }

    static String inspect(String object) {
        return Shell.shGetOutput("docker inspect ${object}")
    }

    static boolean kill(String container) {
        return Shell.shGetStatus("docker kill ${container}")
    }

    static boolean load(String tarFile) {
        return Shell.shGetStatus("docker load < ${tarFile}")
    }

    static String logs(String container) {
        return Shell.shGetOutput("docker logs ${container}")
    }

    static boolean pause(String container) {
        return Shell.shGetStatus("docker pause ${container}")
    }

    static String port(String container) {
        return Shell.shGetOutput("docker port ${container}")
    }

    static boolean restart(String container) {
        return Shell.shGetStatus("docker restart ${container}")
    }

    static boolean remove(String container) {
        return Shell.shGetStatus("docker rm ${container}")
    }

    static boolean removeImage(String image) {
        return Shell.shGetStatus("docker image rm ${image}")
    }

    static boolean save(String image, String tarFile) {
        return Shell.shGetStatus("docker save ${image} > ${tarFile}")
    }

    static boolean start(String container) {
        return Shell.shGetStatus("docker start ${container}")
    }

    static boolean stop(String container) {
        return Shell.shGetStatus("docker stop ${container}")
    }

    static boolean tag(String imageFullName1, String imageFullName2) {
        return Shell.shGetStatus("docker tag ${imageFullName1} ${imageFullName2}")
    }

    static boolean unpause(String container) {
        return Shell.shGetStatus("docker unpause ${container}")
    }

    static String wait(String container) {
        return Shell.shGetOutput("docker wait ${container}")
    }

    /* Самописные функции */

    static void dockerConnect(String host, String user, String id_rsa, Runnable code) {
        assert(host instanceof String)
        assert(user instanceof String)
        assert (id_rsa instanceof String)
        assert (code instanceof Runnable)

        Shell.echo("Подключаюсь к удалённому компьютеру по ssh")
        String pid = Shell.shGetOutput("ssh -i '${id_rsa}' -o 'StrictHostKeyChecking=no' -o 'StreamLocalBindUnlink=yes' -N -L '/var/run/docker.sock:/var/run/docker.sock' '${user}@${host}' & echo \$!")
        Thread.sleep(10000)
        try {
            version()
            code.run()
        } catch (Exception e) {
            e.printStackTrace()
        } finally {
            Shell.echo("Отключаю ssh от удалённого компьютера")
            Shell.sh("kill ${pid}")
        }
    }
}
