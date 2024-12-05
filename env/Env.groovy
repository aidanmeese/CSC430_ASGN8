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
    
    void extendEnv(List<String> ids, List<Value> vals) {
        if (ids.size() == vals.size()) {
            for (int i = 0; i < ids.size(); i++) {
                env.put(ids.get(i) as String, vals.get(i) as Value)
            }
        }
        else {
            throw new Exception("AAQZ Lists sizes did not match")
        }
    }

    
}
