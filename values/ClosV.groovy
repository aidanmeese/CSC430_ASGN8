package values
import env.Env
import expressions.*

class ClosV implements Value {
    private List<String> args
    private ExprC body
    private Env env

    ClosV(List<String> args, ExprC body, Env env){
        this.args = args
        this.body = body
        this.env = env
    }

    List<String> getArgs() {
        return this.args
    }

    ExprC getBody() {
        return this.body
    }

    Env getEnv() {
        return this.env
    }
}