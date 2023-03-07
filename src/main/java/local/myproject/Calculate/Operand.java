package local.myproject.Calculate;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Operand extends Unit{


	private TypeNum type;
	private double value;




	
	public Operand(TypeNum type, double value) {
		this.type = type;
		this.value = value;
		this.unitType = UnitType.OPERAND;
	}
	
	public Operand apply(String operation, Operand nextNumber) {

		String name = Arrays.stream(Operators.values())
				.filter(o -> operation.equals(o.getOperator()))
				.map(o -> o.name()).collect(Collectors.joining());
		double result = BinaryOperators.valueOf(name).get().apply(this.value, nextNumber.value);

		result = result < 0 && this.type == TypeNum.Roman ? 0 : result;
		return new Operand(this.type, result);
	}
	
	@Override
	public Double get() {
		return this.value;
	}

	@Override
	public TypeNum getType () {
		return type;
	}

}
