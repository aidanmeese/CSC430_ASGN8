package expressions

class NumC implements ExprC {
    private double n

    NumC(double n) {
        this.n = n
    }

    double getN() {
        return this.n
    }

    String toString() {
        return "NumC[n="+this.n+"]"
    }
}
