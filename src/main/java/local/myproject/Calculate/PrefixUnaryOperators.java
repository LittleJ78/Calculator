package local.myproject.Calculate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;
/**
 * класс префиксных унарных операторов с соответствующей лямбдой возвращаемой в виде функционального интерфейса
 * @see Operators - перечисление всех операторов, должно включать все названия полей текущего перечисления
 * @see TypeOfOperators - перечисление классов типов операторов, должно влючать имя текущего класса
 * @author Evgenii Mironov
 * version 1.0
 */
public enum PrefixUnaryOperators {
    Sinus (x -> Math.sin(Math.toRadians(x))),
    Cosine (x -> Math.cos(Math.toRadians(x))),
    Tangent (x -> Math.tan(Math.toRadians(x))),
    Cotangent (x -> 1 / Math.tan(Math.toRadians(x)));

    private static final Logger logger = LoggerFactory.getLogger(PrefixUnaryOperators.class.getName());

    /** лямбда соответствующая заданой операции */
    private Function<Double, Double> function;

    /**
     * конструктор
     * @param function - лямбда соответствующая заданой операции
     */
    PrefixUnaryOperators(Function<Double, Double> function) {
        this.function = function;
    }

    /**
     * гетер
     * @return - лямбда соответствующая заданой операции
     */
    public Function<Double, Double> get() {
        return this.function;
    }
}