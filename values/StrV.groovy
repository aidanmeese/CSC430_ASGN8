package values

class StrV implements Value {
    private String s

    StrV(String s) {
        this.s = s
    }

    String getS() {
        return this.s
    }
}
