package language
import values.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Tests {
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
}
