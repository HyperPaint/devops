package hyperpaint.util

final class DockerCompose {
    private DockerCompose() { }

    /* Стандартные функции */

    static String config(String projectDirectory) {
        return Shell.shWithOutput("docker compose --project-directory '${projectDirectory}' config")
    }

    static boolean create(String projectDirectory) {
        return Shell.shWithStatus("docker compose --project-directory '${projectDirectory}' create")
    }

    static boolean down(String projectDirectory) {
        return Shell.shWithStatus("docker compose --project-directory '${projectDirectory}' down")
    }

    static String logs(String projectDirectory) {
        return Shell.shWithOutput("docker compose --project-directory '${projectDirectory}' logs")
    }

    static String ps(String projectDirectory, boolean q = false) {
        if (q) {
            return Shell.shWithOutput("docker compose --project-directory '${projectDirectory}' ps -qa")
        } else {
            return Shell.shWithOutput("docker compose --project-directory '${projectDirectory}' ps -a")
        }
    }

    static boolean restart(String projectDirectory) {
        return Shell.shWithStatus("docker compose --project-directory '${projectDirectory}' restart")
    }

    static boolean rm(String projectDirectory) {
        return Shell.shWithStatus("docker compose --project-directory '${projectDirectory}' rm")
    }

    static boolean start(String projectDirectory) {
        return Shell.shWithStatus("docker compose --project-directory '${projectDirectory}' start")
    }

    static boolean stop(String projectDirectory) {
        return Shell.shWithStatus("docker compose --project-directory '${projectDirectory}' stop")
    }

    static boolean up(String projectDirectory) {
        return Shell.shWithStatus("docker compose --project-directory '${projectDirectory}' up -d")
    }

    static String version() {
        return Shell.shWithOutput("docker compose version")
    }
}
