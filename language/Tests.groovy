package language
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
        Assertions.assertEquals("3", Serializer.serialize(n))
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
        Assertions.assertEquals("1", Serializer.serialize(Interpreter.interp(n, new Env())))
    }
}
