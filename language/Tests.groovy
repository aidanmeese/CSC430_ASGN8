package language
import static Main.topInterp
import values.*
import expressions.*
import env.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Tests {
    //Serialize
    @Test
    void serializeNumV() {
        NumV n = new NumV(3)
        Assertions.assertEquals("3.0", Serializer.serialize(n))
    }

    @Test
    void serializeBoolV() {
        BoolV n = new BoolV(true)
        Assertions.assertEquals("true", Serializer.serialize(n))
    }

    @Test
    void serializeStrV() {
        StrV n = new StrV("Hello World!")
        Assertions.assertEquals("Hello World!", Serializer.serialize(n))
    }

    @Test
    void serializePrimV() {
        PrimV n = new PrimV("+")
        Assertions.assertEquals("#<primop>", Serializer.serialize(n))
    }

    @Test
    void serializeClosV() {
        List<String> strs = new ArrayList<String>()
        strs.add("+")
        NumC num = new NumC(3)
        Env env = new Env()
        ClosV n = new ClosV(strs, num, env)
        Assertions.assertEquals("#<procedure>", Serializer.serialize(n))
    }

    //Interpreter
    @Test
    void interpNumC() {
        NumC n = new NumC(3)
        Assertions.assertEquals(3, Interpreter.interp(n, new Env()).getN())
    }

    @Test
    void interpStrC() {
        StrC n = new StrC("Hello World!")
        Assertions.assertEquals("Hello World!", Interpreter.interp(n, new Env()).getS())
    }

    @Test
    void interpCondC() {
        CondC n = new CondC(new IdC("true"), new NumC(1), new NumC(2))
        Assertions.assertEquals("1.0", Serializer.serialize(Interpreter.interp(n, Interpreter.getTopEnv())))
    }

    @Test
    void interpIdC() {
        IdC n = new IdC("x")
        Assertions.assertEquals("5.0", Serializer.serialize(Interpreter.interp(n, Interpreter.getTopEnv().extendEnv(["x"], [new NumV(5)]))))
    }

    @Test
    void interpLamC() {
        List<String> params = ["x"]
        LamC lam = new LamC(new NumC(5), params)
        Assertions.assertEquals("#<procedure>", Serializer.serialize(Interpreter.interp(lam, Interpreter.getTopEnv())) )
    }

    @Test
    void interpAppC() {
        List<String> params = ["x"]
        LamC lam = new LamC(new NumC(5), params)
        AppC app = new AppC([new NumC(3)], lam)
        Assertions.assertEquals("5.0", Serializer.serialize(Interpreter.interp(app, Interpreter.getTopEnv())) )
    }

    @Test
    void interpPlus() {
        ExprC body = new AppC(
                [new NumC(3), new NumC(4)],
                new IdC("+")
        )
        String result = Serializer.serialize(Interpreter.interp(body, Interpreter.getTopEnv()))
        Assertions.assertEquals("7.0", result)
    }

    @Test
    void interpMinus() {
        ExprC body = new AppC(
                [new NumC(40), new NumC(45)],
                new IdC("-")
        )
        String result = Serializer.serialize(Interpreter.interp(body, Interpreter.getTopEnv()))
        Assertions.assertEquals("-5.0", result)
    }

    @Test
    void interpMult() {
        ExprC body = new AppC(
                [new NumC(2.5), new NumC(2.5)],
                new IdC("*")
        )
        String result = Serializer.serialize(Interpreter.interp(body, Interpreter.getTopEnv()))
        Assertions.assertEquals("6.25", result)
    }

    @Test
    void interpDivide() {
        ExprC body = new AppC(
                [new NumC(40), new NumC(4)],
                new IdC("/")
        )
        String result = Serializer.serialize(Interpreter.interp(body, Interpreter.getTopEnv()))
        Assertions.assertEquals("10.0", result)
    }

    @Test
    void interpDivideZero() {
        ExprC body = new AppC(
                [new NumC(40), new NumC(0)],
                new IdC("/")
        )
        Exception exception = Assertions.assertThrows(Exception.class, {
            Interpreter.interp(body, Interpreter.getTopEnv())
        })
        Assertions.assertEquals("AAQZ: / Can't divide by 0", exception.getMessage())
    }

    @Test
    //appc of a lamc to add 2 numbers
    void interpBind() {
        List<String> parameters = ["x", "y"]
        ExprC body = new AppC(
                [new IdC("x"), new IdC("y")],
                new IdC("+")
        )
        LamC lam = new LamC(body, parameters)
        ExprC bind = new AppC([new NumC(9), new NumC(10)], lam)
        String result = Serializer.serialize(Interpreter.interp(bind, Interpreter.getTopEnv()))
        Assertions.assertEquals("19.0", result)
    }

    //Parser
    @Test
    void parseNum() {
        def program = [1]
        def ret = Parser.parse(program, Parser.getTopParseEnv())
        Assertions.assertTrue(ret instanceof NumC)
        Assertions.assertEquals(1d, ((NumC)ret).getN())

        program = [1.0000d]
        ret = Parser.parse(program, Parser.getTopParseEnv())
        Assertions.assertTrue(ret instanceof NumC)
        Assertions.assertEquals(1d, ((NumC)ret).getN())

        program = [1.000]
        ret = Parser.parse(program, Parser.getTopParseEnv())
        Assertions.assertTrue(ret instanceof NumC)
        Assertions.assertEquals(1d, ((NumC)ret).getN())
    }

    @Test
    void parseStr() {
        def program = ["String"]
        def ret = Parser.parse(program, Parser.getTopParseEnv())
        Assertions.assertTrue(ret instanceof StrC)
        Assertions.assertEquals("String", ((StrC)ret).getS())
    }

    @Test
    void parseId() {
        def program = ["+"]
        def ret = Parser.parse(program, Parser.getTopParseEnv())
        Assertions.assertTrue(ret instanceof IdC)
        Assertions.assertEquals("+", ((IdC)ret).getId())
    }

    @Test
    void parseLambda() {
        def program = [["x", "y", "z"], "=>", 1]
        def ret = Parser.parse(program, Parser.getTopParseEnv())
        Assertions.assertTrue(ret instanceof LamC)
        ret = (LamC) ret
        Assertions.assertTrue(ret.getBody() instanceof NumC)
        Assertions.assertEquals(1, ((NumC)ret.getBody()).getN())
        Assertions.assertLinesMatch(List.of("x", "y", "z"), ret.getParams())
    }

    @Test
    void parseAppC() {
        def program = ["+", 1, 5]
        def ret = Parser.parse(program, Parser.getTopParseEnv())
        AppC expect = new AppC(List.of(new NumC(1), new NumC(5)), new IdC("+"))
        Assertions.assertEquals(ret.toString(), expect.toString())

        program = [[["x", "y"], "=>", ["+", "x", "y"]], 1, 2]
        ret = Parser.parse(program, Parser.getTopParseEnv())
        expect = new AppC(List.of(new NumC(1), new NumC(2)), new LamC(new AppC(List.of(new IdC("x"), new IdC("y")), new IdC("+")), List.of("x","y")))
        Assertions.assertEquals(expect.toString(), ret.toString())
    }

    @Test
    void parseWithVars() {
        def program = [[["x", "y"], "=>", ["+", "x", "y"]], 1, 2]
        def ret = Parser.parse(program, Parser.getTopParseEnv())
        def expect = new AppC(
                List.of(new NumC(1), new NumC(2)),
                new LamC(
                        new AppC(
                                List.of(new IdC("x"), new IdC("y")),
                                new IdC("+")
                        ),
                        List.of("x", "y")))
        Assertions.assertEquals(ret.toString(), expect.toString())
    }

    //Top-Interp
    @Test
    void testTopInterp() {
        def program = [[["x", "y"], "=>", ["+", "x", "y"]], 1, 2]
        Assertions.assertEquals(topInterp(program), "3.0")
    }

    @Test
    void BigTopInterp() {
        def program = [[["x", "y"], "=>", ["if", ["<=", "x", "y"], "y", "x"]], 1, 2]
        Assertions.assertEquals("2.0", topInterp(program))
    }
}
