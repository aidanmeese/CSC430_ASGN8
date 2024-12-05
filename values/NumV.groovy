package values

class NumV implements Value {
    private double n

    NumV(double num) {
        this.n = num
    }

    int getN() {
        return this.n
    }
}