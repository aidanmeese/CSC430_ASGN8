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
        Serializer s = new Serializer()
        NumV n = new NumV(3)
        Assertions.assertEquals("3", s.serialize(n))
    }

    @Test
    void serializeBoolV() {
        Serializer s = new Serializer()
        BoolV n = new BoolV(true)
        Assertions.assertEquals("true", s.serialize(n))
    }

    @Test
    void serializeStrV() {
        Serializer s = new Serializer()
        StrV n = new StrV("Hello World!")
        Assertions.assertEquals("Hello World!", s.serialize(n))
    }

    @Test
    void serializePrimV() {
        Serializer s = new Serializer()
        PrimV n = new PrimV("+")
        Assertions.assertEquals("#<primop>", s.serialize(n))
    }

    @Test
    void serializeClosV() {
        Serializer s = new Serializer()
        List<String> strs = new ArrayList<String>()
        strs.add("+")
        NumC num = new NumC(3)
        Env env = new Env()
        ClosV n = new ClosV(strs, num, env)
        Assertions.assertEquals("#<procedure>", s.serialize(n))
    }

    //Interpreter

}
