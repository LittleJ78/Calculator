package local.myproject.Calculate;

import java.util.function.Function;

public enum PrefixUnaryOperators {
    Sinus (x -> Math.sin(x)),
    Cosinus (x -> Math.cos(x));


    private Function<Double, Double> function;

    PrefixUnaryOperators(Function<Double, Double> function) {
        this.function = function;
    }

    public Function<Double, Double> get() {
        return this.function;
    }
}
