package values

class NumV implements Value {
    private double n

    NumV(double num) {
        this.n = num
    }

    double getN() {
        return this.n
    }
}