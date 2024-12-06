package expressions

class LamC implements ExprC{
    private ExprC body
    private List<String> parameters

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

    String toString() {
        return "LamC[body=" + body + ", params=" + params + "]"
    }
}
