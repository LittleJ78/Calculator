package local.myproject.Calculate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.function.BiFunction;

/**
 * класс бинарных операторов с соответствующей лямбдой возвращаемой в виде функционального интерфейса
 * @see Operators - перечисление всех операторов, должно включать все названия полей текущего перечисления
 * @see TypeOfOperators - перечисление классов типов операторов, должно влючать имя текущего класса
 * @author Evgenii Mironov
 * version 1.0
 */
public enum BinaryOperators {

	Addition ((x, y) -> x + y), 
	Subtraction ((x, y) -> x - y),
	Division ((x, y) -> x / y ),
	Multiplication ((x, y) -> x * y),
	Exponentiation ((x, y) -> Math.pow(x, y));

	private static final Logger logger = LoggerFactory.getLogger(BinaryOperators.class.getName());
	/** лямбда соответствующая заданой операции */
	private BiFunction<Double, Double,Double> function;

	/**
	 * конструктор
	 * @param function - лямбда соответствующая заданой операции
	 */
	BinaryOperators(BiFunction<Double, Double,Double> function) {
		this.function = function;
	}

	/**
	 * гетер
	 * @return - лямбда соответствующая заданой операции
	 */
	public BiFunction<Double, Double,Double> get() {
		return this.function;
	}
}