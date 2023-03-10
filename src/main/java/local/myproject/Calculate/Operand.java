package local.myproject.Calculate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.stream.Collectors;
/**
 * Класс для операндов использующихся в алгебраическом выражении
 * @author Evgenii Mironov
 * version 1.0
 */
public class Operand extends Unit{

	private static final Logger logger = LoggerFactory.getLogger(Operand.class.getName());
	/** формат числа соотносящийся к типу системы счисления вкоторой записан операнд - арабское, римское, двоичное и т.д. */
	private Operands type;
	/** числовое значение операнда */
	private double value;


	/**
	 * конструктор
	 * @param type - формат числа соотносящийся к типу системы счисления вкоторой записан операнд
	 *                - арабское, римское, двоичное и т.д.
	 * @param value - числовое значение операнда
	 */
	public Operand(Operands type, double value) {
		this.type = type;
		this.value = value;
		this.unitType = UnitType.OPERAND;
	}

	/**
	 * перегруженый метод для вычисления унарного алгебраического выражения
	 * @param operation - унарная операция - синус, косинус и т.п.
	 * @return итоговый результат операции в виде нового операнда
	 */
	public Operand apply(Operator operation) {
		String name = Arrays.stream(Operators.values())
				.filter(o -> operation.get().getOperator().equals(o.getOperator()))
				.map(o -> o.name()).collect(Collectors.joining());

		logger.trace("обработка унарного оператора {}", name);
		double		result = PrefixUnaryOperators.valueOf(name).get().apply(this.value);

		result = result < 0 && this.type == Operands.Roman ? 0 : result;
		return new Operand(this.type, result);
	}

	/**
	 * перегруженый метод для вычисления бинарного алгебраического выражения
	 * @param operation - бинарная операция - плюс, минус и т.п.
	 * @param nextNumber - второй операнд
	 * @return
	 */
	public Operand apply(Operator operation, Operand nextNumber) {
		String name = Arrays.stream(Operators.values())
				.filter(o -> operation.get().getOperator().equals(o.getOperator()))
				.map(o -> o.name()).collect(Collectors.joining());

				logger.trace("обработка бинарного оператора {}", name);
				double result = BinaryOperators.valueOf(name).get().apply(this.value, nextNumber.value);
		result = result < 0 && this.type == Operands.Roman ? 0 : result;
		return new Operand(this.type, result);
	}

	/**
	 * гетер
	 * @return числовое значение операнда
	 */
	@Override
	public Double get() {
		return this.value;
	}

	/**
	 * гетер
	 * @return формат числа соотносящийся к типу системы счисления вкоторой записан операнд
	 *                 - арабское, римское, двоичное и т.д.
	 */
	@Override
	public Operands getType () {
		return type;
	}

	public static void main(String[] args) {
		Operand num2 = new Operand(Operands.Arabic,10);
		Operand num1 = new Operand(Operands.Arabic,90);
		Operator operate = new Operator(Operators.Sinus);
		System.out.println(num1.apply(operate).get());
		System.out.println(Math.sin(Math.toRadians(90)));
	}
}


