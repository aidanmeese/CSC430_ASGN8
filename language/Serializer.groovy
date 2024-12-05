package language
import values.*

class Serializer {
    static String serialize(Value v) {
        switch (v.class){
            case NumV:
                return v.getN().toString()
            case BoolV:
                return v.getB() ? "true" : "false"
            case StrV:
                return v.getS()
            case PrimV:
                return "#<primop>"
            case ClosV:
                return "#<procedure>"
        }
    }
}
