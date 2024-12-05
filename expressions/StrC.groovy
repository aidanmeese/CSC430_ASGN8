package expressions

class StrC implements ExprC {
    private String s;

    StrC(String s) {
        this.s = s
    }

    String getS() {
        return this.s
    }
}
