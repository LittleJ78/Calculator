package local.myproject.Calculate;

public class Number {

	private TypeNum type;
	private double value;
	
	public Number(TypeNum type, double value) {
		this.type = type;
		this.value = value;
	}
	
	public Number apply(String operation, Number nextNumber) {
		double result = 0;
		for(Operands o : Operands.values()) {
			 if (operation.equals(o.getOperand())) {
				 result = BinaryOperands.valueOf(o.name()).get().apply(this.value, nextNumber.value);
			 }
		}
		TypeNum type = this.type == nextNumber.type ? this.type : TypeNum.Arabic;
		return new Number(type, result);
	}
	
	public double get() {
		return this.value;
	}

}
