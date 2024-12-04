package values

class PrimV implements Value {
    private String s

    PrimV(String s) {
        this.s = s
    }

    String getS() {
        return this.s
    }
}
