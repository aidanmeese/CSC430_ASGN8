package expressions

class IdC implements ExprC{
    private String symbol

    IdC(String symbol) {
        this.symbol = symbol
    }

    String getId() {
        return this.symbol
    }

    String toString() {
        return "IdC[id="+this.id+"]"
    }
}
