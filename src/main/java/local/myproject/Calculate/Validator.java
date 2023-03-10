package local.myproject.Calculate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * клас определяющий методы для валидации разных значений в текущем виде практически непригоден будет переписан
 * @author Evgenii Mironov
 * version 1.0
 */
public class Validator {

	private static final Logger logger = LoggerFactory.getLogger(Validator.class.getName());

	private boolean validForString;
	private boolean validForOperation;
	private Operands typeFirstNum;
	private Operands typeSecondNum;


	public Validator () {}
	public Validator(String expr) {
		if(expr.split(" ").length == 3) {
			String firstNum = expr.split(" ")[0];
			String secondNum = expr.split(" ")[2];
			String operation =  expr.split(" ")[1];
			this.typeFirstNum = validateRoman(firstNum) ? Operands.Roman : validateArabic(firstNum) ? Operands.Arabic : null;
			this.typeSecondNum = validateRoman(secondNum) ? Operands.Roman : validateArabic(secondNum) ? Operands.Arabic : null;
			this.validForOperation = validateOperation(operation);
			this.validForString = Objects.isNull(this.typeFirstNum) || Objects.isNull(this.typeSecondNum) ?
								  false : this.typeFirstNum.equals(this.typeSecondNum) && this.validForOperation;
		}
	}
	
	boolean validateArabic(String arabicNum) {
		return arabicNum.chars().mapToObj(x -> String.valueOf((char) x)).allMatch(x -> x.matches("[0-9.]"));
	}
	boolean validateRoman(String romanNum) {
		boolean valid = romanNum.chars().mapToObj(x -> String.valueOf((char) x)).allMatch(x -> x.matches("[IVXLCD]"));
		return valid ? romanNum.equals(NumConverter.arabicToRoman(NumConverter.romanToArabic(romanNum))) : false;
	}
	boolean validateOperation(String operation) {
		for (Operators operator : Operators.values()) {
			if (operation.equals(operator.getOperator())) {
				return true;
			}
		}
		return false; //operation.chars().mapToObj(x -> String.valueOf((char) x)).allMatch(x -> x.matches("[+-/^*]"));
	}

	public boolean isValidForString() {
		return validForString;
	}

	public boolean isValidForOperation() {
		return validForOperation;
	}

	public Operands getTypeFirstNum() {
		return typeFirstNum;
	}

	public Operands getTypeSecondNum() {
		return typeSecondNum;
	}
}
