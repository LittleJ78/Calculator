package local.myproject.Calculate;

public enum Operators {
	//binary operands
	Addition (new OperatorVal("+", 0)),
	Subtraction (new OperatorVal("-", 0)),
	Division (new OperatorVal("/", 1)),
	Multiplication (new OperatorVal("*", 1)),
	Exponentiation (new OperatorVal("^", 2)),
	//unary operands
	Sinus (new OperatorVal("sin", 2)),
	Cosinus (new OperatorVal("cos", 2)),

	// brackets
	LeftRoundBracket (new OperatorVal("(", 100)),
	RightRoundBracket (new OperatorVal(")", 100));


	private OperatorVal operator;


	private Operators(OperatorVal operator) {
		this.operator = operator;
	}
	
	public String getOperator() {
		return this.operator.operator;
	}
	public int getPriority() {
		return this.operator.priority;
	}
	private static class OperatorVal {
		String operator;
		int priority;
		OperatorVal (String operator, int priority) {
			this.operator = operator;
			this.priority = priority;
		}
	}
}
