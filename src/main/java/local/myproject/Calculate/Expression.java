package local.myproject.Calculate;

import java.util.LinkedList;
import java.util.List;

public class Expression {
	//refactor
	private List<Object> polishRecord = new LinkedList<>();
	
	
	//old
	private int firstNum;
	private int secondNum;
	private String operation;
	private Validator validation;
	private double result;
	


	public Expression(String expr) {
		//refactor
		expr = normaliseExpr(expr);	
		
		System.out.println(expr);
		
    	//old
		this.validation = new Validator(expr);
		if (validation.isValidForString()) {
			String firstNum = expr.split(" ")[0];
			String operation = expr.split(" ")[1];
			String secondNum = expr.split(" ")[2];
			this.operation = operation;
	    	this.firstNum = this.validation.getTypeFirstNum() == TypeNum.Roman ? (int) NumConverter.romanToArabic(firstNum) : 
	    												         Integer.parseInt(firstNum);
	    	this.secondNum = this.validation.getTypeSecondNum() == TypeNum.Roman ? (int) NumConverter.romanToArabic(secondNum) : 
	    												           Integer.parseInt(secondNum);
	    	

	    	this.result = new Number(validation.getTypeFirstNum(), this.firstNum)
	    			.apply(this.operation, new Number(validation.getTypeSecondNum(), this.secondNum)).get();
		}
		else {
			System.out.println("Expression is not correct");
		} 
	}
	
	private String normaliseExpr(String expr) {
		//refactor
    	expr = expr.replaceAll(" ", "");
    	for (Operands operand : Operands.values()) {
    		expr = expr.replace(operand.getOperand(), " " + operand.getOperand() + " ");
    	}
    	return expr;
	}

	
	
	public String getResult() {
		return this.validation.getTypeFirstNum() == TypeNum.Roman ? NumConverter.arabicToRoman((int) this.result) : String.valueOf(result);
	}
	
	public String getFirstNum() {
		return this.validation.getTypeFirstNum() == TypeNum.Roman ? NumConverter.arabicToRoman((int) this.firstNum) : String.valueOf(firstNum);
	}

	public String getSecondNum() {
		return this.validation.getTypeSecondNum() == TypeNum.Roman ? NumConverter.arabicToRoman((int) this.secondNum) : String.valueOf(secondNum);
	}

	public String getOperation() {
		return operation;
	}
	
	public boolean isValid() {
		return validation.isValidForString();
		
	}
}
