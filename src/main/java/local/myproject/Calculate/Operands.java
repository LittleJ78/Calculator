package local.myproject.Calculate;

public enum Operands {
	//binary operands
	Addition ("+"), 
	Subtraction ("-"),
	Division ("/"),
	Multiplication ("*"),
	Exponentiation ("^"),
	//unary operands
	Sinus ("sin");



	private String operand;


	private Operands(String operand) {
		this.operand = operand;
	}
	
	public String getOperand() {
		return this.operand;
	}
}
