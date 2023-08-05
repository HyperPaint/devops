package hyperpaint.util

final class Shell {
    private static final class OutputInterceptor implements Appendable {
        /** Выходной поток данных */
        private Appendable output = null
        /** Результат выполнения процесса */
        protected int processExitValue = 0
        /** Текстовый результат выполнения процесса */
        private StringBuilder processOutput = new StringBuilder()

        OutputInterceptor(Appendable output) {
            this.output = output
        }

        @Override
        Appendable append(CharSequence csq) throws IOException {
            output.append(csq)
            processOutput.append(csq)
            return this
        }

        @Override
        Appendable append(CharSequence csq, int start, int end) throws IOException {
            output.append(csq, start, end)
            processOutput.append(csq, start, end)
            return this
        }

        @Override
        Appendable append(char c) throws IOException {
            output.append(c)
            processOutput.append(c)
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

    static void sh(String command) {
        command = getSshConnectString() + command

        if (jenkins != null) {
            if (jenkins.sh(script: command, returnStatus: true) != 0) {
                throw new RuntimeException("sh '${command}' is failed")
            }
        } else {
            System.out.println("\$ ${command}")
            OutputInterceptor outputInterceptor = new OutputInterceptor(System.out)
            ProcessBuilder processBuilder = new ProcessBuilder(command.split("\\s"))
            try {
                Process process = processBuilder.start()
                process.consumeProcessOutputStream(outputInterceptor)
                process.consumeProcessErrorStream(outputInterceptor)
                outputInterceptor.setExitValue(process.waitFor())
                if (outputInterceptor.isFailed()) {
                    throw new RuntimeException("sh '${command}' is failed")
                }
            } catch (IOException ignored) {
                throw new RuntimeException("sh '${command}' is failed")
            }

        }
    }

    static boolean shGetStatus(String command) {
        command = getSshConnectString() + command

        if (jenkins != null) {
            return jenkins.sh(script: command, returnStatus: true) == 0
        } else {
            System.out.println("\$ ${command}")
            OutputInterceptor outputInterceptor = new OutputInterceptor(System.out)
            ProcessBuilder processBuilder = new ProcessBuilder(command.split("\\s"))
            try {
                Process process = processBuilder.start()
                process.consumeProcessOutputStream(outputInterceptor)
                process.consumeProcessErrorStream(outputInterceptor)
                outputInterceptor.setExitValue(process.waitFor())
                return outputInterceptor.isSuccess()
            } catch (IOException ignored) {
                System.err.println("sh '${command}' is failed")
                return false
            }
        }
    }

    static String shGetOutput(String command) {
        command = getSshConnectString() + command

        if (jenkins != null) {
            String buff = jenkins.sh(script: command, returnStdout: true)
            jenkins.echo(buff)
            return buff
        } else {
            System.out.println("\$ ${command}")
            OutputInterceptor outputInterceptor = new OutputInterceptor(System.out)
            ProcessBuilder processBuilder = new ProcessBuilder(command.split("\\s"))
            try {
                Process process = processBuilder.start()
                process.consumeProcessOutputStream(outputInterceptor)
                process.consumeProcessErrorStream(outputInterceptor)
                outputInterceptor.setExitValue(process.waitFor())
                return outputInterceptor.getOutput()
            } catch (IOException ignored) {
                System.err.println("sh '${command}' is failed")
                return "sh '${command}' is failed"
            }

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
        assert (code instanceof Runnable)

        echo("Подключаюсь к удалённому компьютеру по ssh")
        sshActive = true
        sshHost = host
        sshUser = user
        sshIdRsa = id_rsa

        try {
            code.run()
        } catch (Exception e) {
            e.printStackTrace()
        } finally {
            echo("Отключаю ssh от удалённого компьютера")
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
