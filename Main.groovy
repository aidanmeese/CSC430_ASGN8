import language.Interpreter
import language.Parser
import language.Serializer

class Main {
    static void main(String[] args){
        topInterp(args) // this is not going to work
    }

    static String topInterp(program) {
        return Serializer.serialize(
                Interpreter.interp(
                        Parser.parse(program, Parser.getTopParseEnv()),
                        Interpreter.getTopEnv()
                )
        )
    }
}