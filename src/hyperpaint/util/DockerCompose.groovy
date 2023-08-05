package hyperpaint.util

final class DockerCompose {
    private DockerCompose() { }

    /* Стандартные функции */

    static String config(String projectDirectory) {
        return Shell.shGetOutput("docker compose --project-directory ${projectDirectory} config")
    }

    static boolean create(String projectDirectory) {
        return Shell.shGetStatus("docker compose --project-directory ${projectDirectory} create")
    }

    static boolean down(String projectDirectory) {
        return Shell.shGetStatus("docker compose --project-directory ${projectDirectory} down")
    }

    static boolean kill(String projectDirectory) {
        return Shell.shGetStatus("docker compose --project-directory ${projectDirectory} kill")
    }

    static String logs(String projectDirectory) {
        return Shell.shGetOutput("docker compose --project-directory ${projectDirectory} logs")
    }

    static String listComposes(boolean all = false, quiet = false) {
        if (all) {
            if (quiet) {
                return Shell.shGetOutput("docker compose list -aq")
            } else {
                return Shell.shGetOutput("docker compose list -a")
            }
        } else {
            if (quiet) {
                return Shell.shGetOutput("docker compose list-q")
            } else {
                return Shell.shGetOutput("docker compose list")
            }
        }
    }

    static boolean pause(String projectDirectory) {
        return Shell.shGetStatus("docker compose --project-directory ${projectDirectory} pause")
    }

    static String port(String projectDirectory) {
        return Shell.shGetOutput("docker compose --project-directory ${projectDirectory} port")
    }

    static String listContainers(String projectDirectory, boolean q = false) {
        if (q) {
            return Shell.shGetOutput("docker compose --project-directory '${projectDirectory}' ps -qa")
        } else {
            return Shell.shGetOutput("docker compose --project-directory '${projectDirectory}' ps -a")
        }
    }

    static boolean pull(String projectDirectory) {
        return Shell.shGetStatus("docker compose --project-directory ${projectDirectory} pull")
    }

    static boolean push(String projectDirectory) {
        return Shell.shGetStatus("docker compose --project-directory ${projectDirectory} push")
    }

    static boolean restart(String projectDirectory) {
        return Shell.shGetStatus("docker compose --project-directory ${projectDirectory} restart")
    }

    static boolean remove(String projectDirectory) {
        return Shell.shGetStatus("docker compose --project-directory ${projectDirectory} rm")
    }

    static boolean start(String projectDirectory) {
        return Shell.shGetStatus("docker compose --project-directory ${projectDirectory} start")
    }

    static boolean stop(String projectDirectory) {
        return Shell.shGetStatus("docker compose --project-directory ${projectDirectory} stop")
    }

    static boolean unpause(String projectDirectory) {
        return Shell.shGetStatus("docker compose --project-directory ${projectDirectory} unpause")
    }

    static boolean up(String projectDirectory) {
        return Shell.shGetStatus("docker compose --project-directory '${projectDirectory} up -d")
    }

    static String version() {
        return Shell.shGetOutput("docker compose version")
    }

    static String wait(String projectDirectory) {
        return Shell.shGetOutput("docker compose --project-directory '${projectDirectory} wait")
    }
}
