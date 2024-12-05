package language

import expressions.ExprC
import expressions.NumC

class Parser {
    Set<String> ids = ["if", "bind", "=>", "="]

    static ExprC parse(program) {
        return new NumC(1)
    }

    boolean isValidId(String id) {
        if (id == null || !(id instanceof String)) {
            return false
        }
        return !ids.contains(id)
    }
}
