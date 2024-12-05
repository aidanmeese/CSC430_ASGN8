package env
import values.Value
class Env {
    Map<String, Value> env = new HashMap<>();

    Value lookupValue(id) {
        if (env.get(id) == null) {
            throw new Exception("AAQZ this id does not have a value")
        }
        return env.get(id)
    }

    
}
