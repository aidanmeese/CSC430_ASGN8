package language

import expressions.ExprC

class Parser {
    Set<String> ids = ["if", "bind", "=>", "="]

    static ExprC parse(program) {
        if (program instanceof List) {
            program = (List) program
            if (program.size() > 0) {
                switch(program.get(0)) {
                    case "if":
                        parseIf(program)
                        break
                    case "bind":
                        parseBind(program)
                        break
                    default:
                        if (program.size() > 1 && program.get(1) == "=>") {
                            parseLambda(program)
                        } else {

                        }
                }
            } else {
                throw new Exception("AAQZ Cannot parse an empty program: " + program)
            }
        } else if (program instanceof Integer) {
            return NumC((int) program)
        } else if (program instanceof List) {
            throw new RuntimeException("I don't know how to do this.")
        }
    }

    boolean isValidId(String id) {
        if (id == null || !(id instanceof String)) {
            return false
        }
        return !ids.contains(id)
    }
}
