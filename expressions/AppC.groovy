package expressions

class AppC implements ExprC{
    private List<ExprC> arguments
    private ExprC function

    AppC(List<ExprC> args, ExprC function) {
        this.arguments = args
        this.function = function
    }

    ExprC getFunction() {
        return this.function
    }
    List<ExprC> getArgs() {
        return this.arguments
    }

    String toString() {
        return "AppC[func="+this.function+", arguments="+this.arguments+"]"
    }
}
