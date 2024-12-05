package language

import expressions.*
import values.*
import env.Env

class Interpreter {
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
        return interp(env.lookup(exp.getId()))
    }

    static Value interpLambda(LamC exp, Environment env) {
        throw new Exception("This is not implemented yet.")
    }

    static Value interpApplication(AppC exp, Environment env) {
        throw new Exception("This is not implemented yet.")
    }

    static Value interpCondition(CondC exp, Environment env) {
        throw new Exception("This is not implemented yet.")
    }


}
