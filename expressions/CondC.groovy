package expressions

class CondC {
    private ExprC test
    private ExprC then
    private ExprC els

    CondC(ExprC test, ExprC then, ExprC els) {
        this.test = test
        this.then = then
        this.els = els
    }

    ExprC getTest() {
        return this.test
    }
    ExprC getThen() {
        return this.then
    }
    ExprC getElse() {
        return this.els
    }
}
