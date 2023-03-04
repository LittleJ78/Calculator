package local.myproject.Calculate;

import java.util.function.BiFunction;

public enum BinaryOperands {
	Addition ((x, y) -> x + y), 
	Subtraction ((x, y) -> x - y),
	Division ((x, y) -> x / y),
	Multiplication ((x, y) -> x * y),
	Exponentiation ((x, y) -> Math.pow(x, y));

	private BiFunction<Double, Double,Double> function;
	
	BinaryOperands(BiFunction<Double, Double,Double> function) {
		this.function = function;
	}
	
	public BiFunction<Double, Double,Double> get() {
		return this.function;
	}
}
