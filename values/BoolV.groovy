package values

class BoolV implements Value {
    private boolean b

    BoolV(boolean b) {
        this.b = b
    }

    boolean getB() {
        return this.b
    }
}