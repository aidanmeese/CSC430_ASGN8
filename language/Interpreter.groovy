package language

import env.Env
import expressions.*
import values.*

import java.util.stream.Collectors

class Interpreter {

    static Env getTopEnv() {
        Env pie = new Env()
        List<String> ids = ["true", "false", "+", "-", "*", "/", "<=", "equal?", "error"]
        List<Value> vals = [new BoolV(true), new BoolV(false), new PrimV("+"), new PrimV("-"), new PrimV("*"), new PrimV("/"), new PrimV("<="), new PrimV("equal?"), new PrimV("error")]

        return pie.extendEnv(ids, vals)
    }

    static Value interp(ExprC ast, Env env) {
        switch(ast.class) {
            case(NumC.class):
                interpNum((NumC)ast)
                break
            case(StrC.class):
                interpStr((StrC)ast)
                break
            case(IdC.class):
                interpId((IdC)ast, env)
                break
            case(LamC.class):
                interpLambda((LamC)ast, env)
                break
            case(AppC.class):
                interpApplication((AppC)ast, env)
                break
            case(CondC.class):
                interpCondition((CondC)ast, env)
                break
        }
    }

    static NumV interpNum(NumC exp) {
        return new NumV(exp.getN())
    }

    static StrV interpStr(StrC exp) {
        return new StrV(exp.getS())
    }

    static Value interpId(IdC exp, Env env) {
        return env.lookupValue(exp.getId())
    }

    static Value interpLambda(LamC exp, Env env) {
        return new ClosV(exp.getParams(), exp.getBody(), env)
    }

    static Value interpApplication(AppC exp, Env env) {
        Value func = interp(exp.function, env)
        if (func instanceof PrimV) interpPrimitive((PrimV) func, env)
        else if (func instanceof ClosV) {
            if (func.args.size() == exp.args.size()) {
                return interp(func.getBody(), env.extendEnv(
                        func.args, exp.args.stream().map(item -> interp(item, env)).collect(Collectors.toList())))
            } else {
                throw new RuntimeException(String.format(
                        "AAQZ Cannot call function requiring %s parameters with %s arguments",
                        func.args.size(), exp.args.size())
                )
            }
        } else {
            throw new RuntimeException("AAQZ Cannot call " + func.class)
        }
    }

    static Value interpCondition(CondC exp, Env env) {
        Value test = interp(exp.getTest(), env)
        if (test instanceof BoolV) {
            if (((BoolV)test).getB()) {
                return interp(exp.getThen(), env)
            } else return interp(exp.getElse(), env)
        } else throw new RuntimeException("AAQZ Expected boolean, got " + test.class)
    }

    static Value interpPrimitive(PrimV primitive, Env env) {
        throw new Exception("This is not implemented yet.")
    }
}
