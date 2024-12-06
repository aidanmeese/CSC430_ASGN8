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
        return env.lookup(exp.getId())
    }

    static Value interpLambda(LamC exp, Env env) {
        return new ClosV(exp.getParams(), exp.getBody(), env)
    }

    static Value interpApplication(AppC exp, Env env) {
        Value func = interp(exp.function, env)
        if (func instanceof PrimV) interpPrimitive((PrimV) func, exp.args.stream().map(item -> interp(item, env)).collect(Collectors.toList()), env)
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

    static Value interpPrimitive(PrimV primitive, List<Value> args, Env env) {
        String op = primitive.getS()
        if (args.size() == 2) {
            Value a = args.get(0)
            Value b = args.get(1)
            switch (op) {
                case ("+"):
                    if((a.class == NumV.class) &&  (b.class == NumV.class)) {
                        return new NumV((a as NumV).getN() + (b as NumV).getN())
                    }
                    else {
                        throw new Exception("AAQZ: + operator not passed 2 numbers")
                    }
                    break
                case ("-"):
                    if((a.class == NumV.class) &&  (b.class == NumV.class)) {
                        return new NumV((a as NumV).getN() - (b as NumV).getN())
                    }
                    else {
                        throw new Exception("AAQZ: - operator not passed 2 numbers")
                    }
                    break
                case ("*"):
                    if((a.class == NumV.class) && (b.class == NumV.class)) {
                        return new NumV((a as NumV).getN() * (b as NumV).getN())
                    }
                    else {
                        throw new Exception("AAQZ: * operator not passed 2 numbers")
                    }
                    break
                case ("/"):
                    if((a.class == NumV.class) && (b.class == NumV.class) && ((b as NumV).getN() != 0)) {
                        return new NumV((a as NumV).getN() / (b as NumV).getN())
                    }
                    else {
                        if ((b as NumV).getN() == 0) {
                            throw new Exception("AAQZ: / Can't divide by 0")
                        }
                        else {
                            throw new Exception("AAQZ: / operator not passed 2 numbers")
                        }
                    }
                    break
                case ("<="):
                    if((a.class == NumV.class) &&  (b.class == NumV.class)) {
                        return new BoolV((a as NumV).getN() <= (b as NumV).getN())
                    }
                    else {
                        throw new Exception("AAQZ: + operator not passed 2 numbers")
                    }
                    break
                case ("equal?"):
                    if((a.class == NumV.class) &&  (b.class == NumV.class)) {
                        return new BoolV((a as NumV).getN() == (b as NumV).getN())
                    }
                    else if ((a.class == BoolV.class) &&  (b.class == BoolV.class)) {
                        return new BoolV((a as BoolV).getB() == (b as BoolV).getB())
                    }
                    else if ((a.class == StrV.class) &&  (b.class == StrV.class)) {
                        return new BoolV((a as StrV).getS() == (b as StrV).getS())
                    }
                    else {
                        throw new Exception("AAQZ: equal operator requires 2 of the same parameters")
                    }
            }
        }
        else {
            if(op == "error") {
                if (args.get(0).class == StrV.class) {
                    throw new Exception((args.get(0) as StrV).getS())
                }
                else {
                    throw new Exception("AAQZ: error operator requires a single String")
                }
            }
            else {
                throw new Exception("AAQZ: Wrong amount of Arguements passed")
            }
        }

    }
}
