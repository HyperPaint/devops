package hyperpaint.util

class ShellTest {
    private ShellTest() { }

    static void main(String[] args) {
        Shell.sh("echo Hello World")
        assert(Shell.shGetStatus("echo Hello World!"))
        assert(!Shell.shGetStatus("error Hello World!"))
        assert(Shell.shGetOutput("echo Hello World!").trim() != "Hello World")
        assert(Shell.shGetOutput("echo Hello World!").trim() == "Hello World!")
    }
}
