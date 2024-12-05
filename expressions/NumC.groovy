package expressions

class NumC implements ExprC {
    private double n

    NumC(double n) {
        this.n = n
    }

    int getN() {
        return this.n
    }
}
