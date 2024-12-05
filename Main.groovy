import static language.Serializer.serialize
import static language.Interpreter.interp
import static language.Parser.parse

class Main {
    static void main(String[] args){
        // Example for how our program works
        def program = [["Hello", "Values"], "Hello"]
        println(serialize(interp(parse(program))))


    }
}