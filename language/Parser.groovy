package language

import env.ParserEnv
import expressions.AppC
import expressions.CondC
import expressions.ExprC
import expressions.IdC
import expressions.LamC
import expressions.NumC
import expressions.StrC

import java.util.stream.Collectors

class Parser {
    static Set<String> restrictedIds = ["if", "bind", "=>", "="]

    static ExprC parse(program, parseEnv) {
        if (program instanceof List) {
            program = (List) program
            if (program.size() > 0) {
                switch(program.get(0)) {
                    case "if":
                        return parseIf(program, parseEnv)
                    case "bind":
                        return parseBind(program, parseEnv)
                        break
                    default:
                        if (program.size() > 1 && program.get(1) == "=>") {
                            parseLambda(program, parseEnv)
                        } else if (program.size() > 1) {
                            // generic "what the hell is this" parsing function
                            parseStatement(program, parseEnv)
                        } else {
                            // single item (str, num, id)
                            parseToken(program.get(0), parseEnv)
                        }
                }
            } else {
                throw new Exception("AAQZ Cannot parse an empty program: " + program)
            }
        } else return parseToken(program, parseEnv)
    }

    static ExprC parseIf(program, parseEnv) {
        if (program.size() != 4) {
            throw new Exception("AAQZ incorrect definition for if: " + program.toString())
        } else return new CondC(
                parse(program.get(1), parseEnv),
                parse(program.get(2), parseEnv),
                parse(program.get(3), parseEnv)
        )
    }

    static ExprC parseBind(program, parseEnv) {
        // didn't manage to add
    }

    static ExprC desugar(program, parseEnv) {
        // didn't manage to add
    }

    static ExprC parseLambda(program, ParserEnv parseEnv) {
        if (program.size() != 3) {
            throw new Exception("AAQZ Improperly defined lambda at " + program.toString())
        }
        def checkParam = program.get(0)
        if (checkParam instanceof List) {
            List<String> validParam = checkParam.stream().map(item -> {
                if (item instanceof String) {
                    if (isValidId(item)) {
                        return item
                    } else throw new Exception("AAQZ Cannot use reserved token as parameter value: " + item.toString())
                } else throw new Exception("AAQZ Unexpected token used as parameter value: " + item.toString())
            }).collect(Collectors.toList()) as List<String>
            return new LamC(parse(program.get(2), parseEnv.addSymbols(validParam)), validParam)
        } else  {
            throw new Exception("AAQZ Expected list of parameters got this " + checkParam.toString())
        }
    }

    static boolean isValidId(String id) {
        if (id == null || !(id instanceof String)) {
            return false
        }
        return !restrictedIds.contains(id)
    }

    // This should realistically parse to an AppC
    static ExprC parseStatement(List<?> program, parseEnv) {
        if(program.get(0) instanceof List || parseEnv.has(program.get(0))) {
            List<ExprC> arguments = new LinkedList<>()
            ExprC body = parse(program.get(0), parseEnv)
            int i = 0
            for(def item : program) {
                if (i != 0) {
                    arguments.add(parse(item, parseEnv))
                } else i++
            }
            return new AppC(arguments, body)
        } else throw new Exception("AAQZ Improper function call at " + program.toString())
    }

    static ExprC parseToken(program, parseEnv) {
        if (program instanceof Number) {
            return new NumC((Double) program)
        } else if (program instanceof String) {
            if (parseEnv.has(program)) {
                return new IdC(program)
            } else return new StrC(program)
        } else throw new Exception("AAQZ What is this garbage you have sent to my parser? " + program.toString())
    }

    static ParserEnv getTopParseEnv() {
        List<String> topEnv = List.of("true", "false", "+", "-", "*", "/", "<=", "equal?", "error")
        return new ParserEnv(topEnv)
    }
}
