package local.myproject.Calculate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * перечисление всех операторов, должно повторяться по группам в классах перечисленых в классе TypeOfOperators
 * @see TypeOfOperators - перечисление классов типов операторов - унарные, бинарные и т.д.
 * @author Evgenii Mironov
 * version 1.0
 */
public enum Operators {
	//binary operands
	Addition (new OperatorVal("+", 0)),
	Subtraction (new OperatorVal("-", 0)),
	Division (new OperatorVal("/", 1)),
	Multiplication (new OperatorVal("*", 1)),
	Exponentiation (new OperatorVal("^", 2)),
	//Prefix unary operands
	Sinus (new OperatorVal("sin", 2)),
	Cosine(new OperatorVal("cos", 2)),
	Tangent (new OperatorVal("tan", 2)),
	Cotangent (new OperatorVal("ctg", 2)),

	// brackets
	LeftRoundBracket (new OperatorVal("(", 100)),
	RightRoundBracket (new OperatorVal(")", 100));

	private static final Logger logger = LoggerFactory.getLogger(Operators.class.getName());

	/** оператор соответствующий заданному перечислению*/
	private OperatorVal operator;

	/**
	 * конструктор
	 * @param operator - оператор соответствующий заданному перечислению
	 */
	private Operators(OperatorVal operator) {
		this.operator = operator;
	}

	/**
	 * гетер
	 * @return строковый вид оператора:  "+",  "-",  "sin" и т.д.
	 */
	public String getOperator() {
		return this.operator.operator;
	}

	/**
	 * гетер
	 * @return приоритет выполнения операции - 0 самый низкий, 100 наивысший
	 */
	public int getPriority() {
		return this.operator.priority;
	}

	/**
	 * класс хранящий вид и приоритет оператора
	 */
	private static class OperatorVal {
		/** строковый вид оператора:  "+",  "-",  "sin" и т.д.*/
		String operator;
		/** приоритет выполнения операции - 0 самый низкий, 100 наивысший */
		int priority;

		/**
		 * конструктор
		 * @param operator - строковый вид оператора:  "+",  "-",  "sin" и т.д
		 * @param priority - приоритет выполнения операции - 0 самый низкий, 100 наивысший
		 */
		OperatorVal (String operator, int priority) {
			this.operator = operator;
			this.priority = priority;
		}
	}
}
