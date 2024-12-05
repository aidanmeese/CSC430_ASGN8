package expressions

class LamC {
    private List<String> parameters
    private ExprC body

    LamC(ExprC body, List<String> parameters) {
        this.parameters = parameters
        this.body = body
    }

    ExprC getBody() {
        return this.body
    }

    List<String> getParams() {
        return this.parameters
    }
}
