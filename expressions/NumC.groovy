package expressions

class NumC implements ExprC {
    private int n

    NumC(int n) {
        this.n = n
    }

    int getN() {
        return this.n
    }
}
