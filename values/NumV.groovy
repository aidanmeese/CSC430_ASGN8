package values

class NumV implements Value {
    private int n

    NumV(int num) {
        this.n = num
    }

    int getN() {
        return this.n
    }
}