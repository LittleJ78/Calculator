package local.myproject.Calculate;

import java.util.*;

public class Expression {
	//refactor
	private Deque <Unit> polishRecord = new LinkedList<>();
	private List<String> expression = new ArrayList<>();
	private Deque <Unit> stec = new LinkedList<>();
	Validator validator = new Validator();



	//old
	private int firstNum;
	private int secondNum;
	private String operation;
	private Validator validation;
	private double result;

	String exp;


	


	public Expression(String expr) {
		//refactor

		System.out.println(expr);

		this. expression = Arrays.stream(normaliseExpr(expr).split(" ")).toList();

		toPolishRecord();

//3 4 2 * 1 5 − 2 ^ / +



    	//old
		expr = normaliseExpr(expr);
		this.exp = expr;

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
	    	

	    	this.result = new Operand(validation.getTypeFirstNum(), this.firstNum)
	    			.apply(this.operation, new Operand(validation.getTypeSecondNum(), this.secondNum)).get();
		}
		else {
			System.out.println("Expression is not correct");
		}
	}
	
	private String normaliseExpr(String expr) {
		//refactor
    	expr = expr.replaceAll(" ", "");
    	for (Operators operator : Operators.values()) {
    		expr = expr.replace(operator.getOperator(), " " + operator.getOperator() + " ");
    	}
		expr = expr.replaceAll("  ", " ");
    	return expr;
	}

	private  void toPolishRecord() {

		for(int i = 0; i < expression.size(); i++) {
			if(validator.validateArabic(expression.get(i))) {
				polishRecord.add(new Operand(TypeNum.Arabic, Double.parseDouble(expression.get(i))));
	//			System.out.println("добавляем в запись " + polishRecord.peekLast().get());
			}
			if(validator.validateOperation(expression.get(i))) {
				for (Operators operator : Operators.values()) {
					if(operator.getOperator().equals(expression.get(i))){
						Operator buf = new Operator(operator);
						if(!stec.isEmpty()) {
							if(((Operators) stec.peekLast().get()).getPriority() >= buf.get().getPriority() &&
									stec.peekLast().getType() != TypeOfOperators.Brackets) {
	//							System.out.println("убираем из стека " + ((Operators)stec.peekLast().get()).getOperator());
								polishRecord.add(stec.pollLast());
	//							System.out.println("добавляем в запись " + polishRecord.peekLast().get());
							}
						}
						if(buf.get() == Operators.RightRoundBracket) {
							while (((Operators) stec.peekLast().get()) != Operators.LeftRoundBracket) {
	//							System.out.println("убираем из стека между скобками " + ((Operators)stec.peekLast().get()).getOperator());
	//							System.out.println("добавляем в запись между скобками " + polishRecord.peekLast().get());
								polishRecord.add(stec.pollLast());
							}
							stec.pollLast();
						}
						else {
							stec.addLast(buf);
	//						System.out.println("добавляем в стек " + ((Operators)stec.peekLast().get()).getOperator());
						}
					}
				}
			}
		}
		while(stec.size() > 0) {
			polishRecord.add(stec.pollLast());
	//		System.out.println("добавляем в запись " + polishRecord.peekLast().get());
		}
	}

	
	public Operand calculate() {
		stec = new LinkedList<>();
		while (!polishRecord.isEmpty()) {
			if (polishRecord.peekFirst().getUnitType() == Unit.UnitType.OPERAND) {
				stec.addLast(polishRecord.pollFirst());

			}
			if (polishRecord.peekFirst().getUnitType() == Unit.UnitType.OPERATOR) {
				Operand secondOperand = (Operand) stec.pollLast();
				Operand firstOperand = (Operand) stec.pollLast();
				String operator = ((Operator) polishRecord.pollFirst()).get().getOperator();
				stec.addLast(firstOperand.apply(operator, secondOperand));
	//			System.out.println(firstOperand.get() + " " + operator + " " + secondOperand.get() + " = " + ((Operand) stec.peekLast()).get());
			}
		}
		return (Operand) stec.pollFirst(); //3 + 4 * 2 / (1 - 5)^2
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

	public static void main(String...args) {
		Expression exp = new Expression("3 + 4 * 2 / (1 - 5)^2");
		System.out.println(exp.expression.toString());
		exp.polishRecord.forEach(x -> System.out.print( x.getUnitType() ==  Unit.UnitType.OPERAND ? x.get() + " | " : ((Operators) x.get()).getOperator() + " | ") );
		System.out.println();
		exp.stec.forEach(x -> {
				System.out.println(x.getType() + " --- " + ((Operators) x.get()).getOperator() + " " + ((Operators) x.get()).getPriority());
		});

		System.out.println(exp.calculate().get());
	}
}