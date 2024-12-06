package env
import values.Value

class Env {
    private Map<String, Value> env

    Env(Map<String, Value> init) {
        this.env = init
    }

    Env() {
        this.env = new HashMap<>()
    }

    Map<String, Value> getEnv() {
        return this.env
    }

    Value lookup(String id) {
        if (env.get(id) == null) {
            throw new Exception("AAQZ this id does not have a value")
        }
        return env.get(id)
    }
    
    Env extendEnv(List<String> ids, List<Value> vals) {
        Env newEnv = new Env(new HashMap<>(this.env))
        if (ids.size() == vals.size()) {
            for (int i = 0; i < ids.size(); i++) {
                newEnv.env.put(ids.get(i) as String, vals.get(i) as Value)
            }
        } else {
            throw new Exception("AAQZ Lists sizes did not match")
        }
        return newEnv
    }
}
