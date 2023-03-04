package local.myproject.Calculate;

import java.util.Objects;

public class Validator {
	
	private boolean validForString;
	private boolean validForOperation;
	private TypeNum typeFirstNum;
	private TypeNum typeSecondNum;
	
	
	public Validator(String expr) {
		if(expr.split(" ").length == 3) {
			String firstNum = expr.split(" ")[0];
			String secondNum = expr.split(" ")[2];
			String operation =  expr.split(" ")[1];
			this.typeFirstNum = validateRoman(firstNum) ? TypeNum.Roman : validateArabic(firstNum) ? TypeNum.Arabic : null;
			this.typeSecondNum = validateRoman(secondNum) ? TypeNum.Roman : validateArabic(secondNum) ? TypeNum.Arabic : null;
			this.validForOperation = validateOperation(operation);
			this.validForString = Objects.isNull(this.typeFirstNum) || Objects.isNull(this.typeSecondNum) ?
								  false : this.typeFirstNum.equals(this.typeSecondNum) && this.validForOperation;
		}
	}
	
	boolean validateArabic(String arabicNum) {
		return arabicNum.chars().mapToObj(x -> String.valueOf((char) x)).allMatch(x -> x.matches("[0-9]"));
	}
	boolean validateRoman(String romanNum) {
		boolean valid = romanNum.chars().mapToObj(x -> String.valueOf((char) x)).allMatch(x -> x.matches("[IVXLCD]"));
		return valid ? romanNum.equals(NumConverter.arabicToRoman(NumConverter.romanToArabic(romanNum))) : false;
	}
	boolean validateOperation(String operation) {
		return operation.chars().mapToObj(x -> String.valueOf((char) x)).allMatch(x -> x.matches("[+-/^*]"));
	}

	public boolean isValidForString() {
		return validForString;
	}

	public boolean isValidForOperation() {
		return validForOperation;
	}

	public TypeNum getTypeFirstNum() {
		return typeFirstNum;
	}

	public TypeNum getTypeSecondNum() {
		return typeSecondNum;
	}
}
