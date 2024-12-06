package env

import values.Value

class ParserEnv {
    private Set<String> env

    ParserEnv() {
        env = new HashSet<>()
    }
    ParserEnv(List<String> init) {
        env = new HashSet<>()
        for (String id : init) {
            env.add(id)
        }
    }

    Set<String> getEnv() {
        return this.env
    }

    Boolean has(String id) {
        return env.contains(id)
    }

    ParserEnv addSymbols(List<String> ids) {
        ParserEnv newEnv = new ParserEnv(this.env.toList())
        for (String id : ids) {
            newEnv.env.add(id)
        }
        return newEnv
    }
}
