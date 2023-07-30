package hyperpaint.util

final class Shell {
    private static final class OutputInterceptor implements Appendable {
        protected int processExitValue = 0
        private StringBuilder processOutput = new StringBuilder()
        private InputStream input = null
        private Appendable output = null

        OutputInterceptor(InputStream input, Appendable output) {
            this.input = input
            this.output = output
        }

        @Override
        Appendable append(CharSequence csq) throws IOException {
            processOutput.append(csq)
            output.append(csq)
            return this
        }

        @Override
        Appendable append(CharSequence csq, int start, int end) throws IOException {
            processOutput.append(csq, start, end)
            output.append(csq, start, end)
            return this
        }

        @Override
        Appendable append(char c) throws IOException {
            processOutput.append(c)
            output.append(c)
            return this
        }

        protected void setExitValue(int processExitValue) {
            this.processExitValue = processExitValue
        }

        boolean isSuccess() {
            return processExitValue == 0
        }

        boolean isFailed() {
            return processExitValue != 0
        }

        String getOutput() {
            return processOutput.toString()
        }
    }

    private static def jenkins = null

    private static boolean sshActive = false
    private static String sshHost = null
    private static String sshUser = null
    private static String sshIdRsa = null

    private Shell() { }

    static void setJenkins(def jenkins) {
        this.jenkins = jenkins
    }

    static void shOrFail(String command) {
        command = getSshConnectString() + command

        if (jenkins != null) {
            if (jenkins.sh(script: command, returnStatus: true) != 0) {
                throw new RuntimeException("sh '" + command + "' is failed")
            }
        } else {
            System.out.print(command)
            ProcessBuilder processBuilder = new ProcessBuilder(command)
            Process process = processBuilder.start()
            OutputInterceptor outputInterceptor = new OutputInterceptor(process.getInputStream(), System.out)
            outputInterceptor.setExitValue(process.exitValue())
            if (outputInterceptor.isFailed()) {
                throw new RuntimeException("sh '" + command + "' is failed")
            }
        }
    }

    static String shWithOutput(String command) {
        command = getSshConnectString() + command

        if (jenkins != null) {
            return jenkins.sh(script: command, returnStdout: true)
        } else {
            System.out.print(command)
            ProcessBuilder processBuilder = new ProcessBuilder(command)
            Process process = processBuilder.start()
            OutputInterceptor outputInterceptor = new OutputInterceptor(process.getInputStream(), System.out)
            outputInterceptor.setExitValue(process.exitValue())
            return outputInterceptor.getOutput()
        }
    }

    static boolean shWithStatus(String command) {
        command = getSshConnectString() + command

        if (jenkins != null) {
            return jenkins.sh(script: command, returnStatus: true) == 0
        } else {
            System.out.print(command)
            ProcessBuilder processBuilder = new ProcessBuilder(command)
            Process process = processBuilder.start()
            OutputInterceptor outputInterceptor = new OutputInterceptor(process.getInputStream(), System.out)
            outputInterceptor.setExitValue(process.exitValue())
            return outputInterceptor.isSuccess()
        }
    }

    static void echo(String text) {
        if (jenkins != null) {
            jenkins.echo(text)
        } else {
            System.out.println(text)
        }
    }

    static void error(String text) {
        if (jenkins != null) {
            jenkins.error(text)
        } else {
            throw new RuntimeException(text)
        }
    }

    static void sshConnect(String host, String user, String id_rsa, Runnable code) {
        assert(host instanceof String)
        assert(user instanceof String)
        assert (id_rsa instanceof String)

        echo("Подключаюсь к удалённому компьютеру по ssh...")
        sshActive = true
        sshHost = host
        sshUser = user
        sshIdRsa = id_rsa

        try {
            code.run()
        } catch (Exception e) {
            e.printStackTrace()
        } finally {
            echo("Отключаю ssh от удалённого компьютера...")
            sshActive = false
            sshHost = null
            sshUser = null
            sshIdRsa = null
        }
    }

    private static String getSshConnectString() {
        if (sshActive) {
            return "ssh -i '${sshIdRsa}' -o 'StrictHostKeyChecking=no' '${sshUser}@${sshHost}' "
        } else {
            return ""
        }
    }
}
