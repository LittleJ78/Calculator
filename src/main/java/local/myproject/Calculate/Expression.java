package local.myproject.Calculate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс для хранения алгебраического выражения в разных видах
 * @author Evgenii Mironov
 * version 1.0
 */
public class Expression {
	private static final Logger logger = LoggerFactory.getLogger(Expression.class.getName());
	/**контейнер для хранения алгебраического выражения в виде польской записи*/
	private Deque<Unit> polishRecord;
	/**контейнер для хранения алгебраического выражения в виде простой записи*/
	private List<Unit> unitExpression;
	Validator validator = new Validator();


	/**
	 * конструктор
	 * @param expression - Алгебраическое выражение вида "1 + 2 - (3 - 1)"
	 * @see Operators - перечисление поддерживаемых операторов
	 * @see Operands - перечисление поддерживаемых форматов чисел
	 */
	public Expression(String expression) throws Exception {
		this.unitExpression = toUnitExpression(expression);
		logger.info("записали нормализованую запись {} ",this.getUnitExpression());
		this.unitExpression = normaliseOperatorsTypeInUnitExpr(unitExpression);
		logger.info("переопределили операторы в выражении {} ",this.getUnitExpression());
		this.polishRecord = toPolishRecord(expression);
		logger.info("Записали польскую запись " + getPolishRecord());
	}

	/**
	 * переопределение операторов с изменяемым типом - минус может быть как бинарным так и префиксным унарным -
	 * в зависимости от места где  оператор находиться в алгебраическом выражении
	 * @param expression - алгебраическое выражение в виде списка из отдельных операторов и операнд
	 * @return - алгебраическое выражение в виде списка из отдельных переопределенных операторов и операнд
	 */
	private List<Unit> normaliseOperatorsTypeInUnitExpr (List<Unit> expression) throws Exception {
		if (expression.get(0).getUnitType().equals(Unit.UnitType.OPERATOR) && ((Operator) expression.get(0)).get().equals(Operators.Subtraction)) {
			logger.trace("обрабатываем {}",expression.get(0).get());
			((Operator) expression.get(0)).setTypeOfOperator(TypeOfOperators.PrefixUnaryOperators);
			logger.trace("изменили тип оператора с индексом 0 на {} ", TypeOfOperators.PrefixUnaryOperators);
		}
		for (int i = 1; i < expression.size(); i++) {
			logger.trace("обрабатываем {}",expression.get(i).get());
			if(expression.get(i).getUnitType().equals(Unit.UnitType.OPERATOR) && ((Operator) expression.get(i)).get().equals(Operators.Subtraction)) {
				if(expression.get(i - 1).getUnitType().equals(Unit.UnitType.OPERATOR) && !((Operator) expression.get(i-1)).get().equals(Operators.RightRoundBracket)) {
					((Operator) expression.get(i)).setTypeOfOperator(TypeOfOperators.PrefixUnaryOperators);
					logger.trace("изменили тип оператора с индексом {} на {} ",i , TypeOfOperators.PrefixUnaryOperators);
				}
			}
		}
		return expression;
	}

	/**
	 * преобразование сроки в контейнер - простая запись
	 * @param expression - Алгебраическое выражение вида "1 + 2 - (3 - 1)"
	 * @return - алгебраическое выражение в виде списка из отдельных операторов и операнд
	 * @see Unit - абстрактный класс для операторов и операнд
	 */
	private List<Unit> toUnitExpression(String expression) {
		String str = normaliseExpr(expression);
		logger.debug("нормализовали запись {}", str);
		List<String> stringExpression = Arrays.stream(str.split(" ")).toList();

		Deque<Unit> buffer = new LinkedList<>();
		logger.trace("ОБРАБОТКА НОРМАЛИЗОВАНОЙ ЗАПИСИ, [{}] элемента - {}",stringExpression.size(),stringExpression.toString());
		for(int i = 0; i < stringExpression.size(); i++) {
			logger.trace("обрабатывается элемент {}",stringExpression.get(i));
			if (validator.validateArabic(stringExpression.get(i))) {
				buffer.add(new Operand(Operands.Arabic, Double.parseDouble(stringExpression.get(i))));
				logger.trace("добавили {} в контейнер", stringExpression.get(i));
			}
			if(validator.validateOperation(stringExpression.get(i))) {
				for (Operators operator : Operators.values()) {
					if (operator.getOperator().equals(stringExpression.get(i))) {
						buffer.add(new Operator(operator));
						logger.trace("добавили {} в контейнер", stringExpression.get(i));
					}
				}
			}
		}

		Deque <Unit> result = new LinkedList<>();
		while(!buffer.isEmpty()){  //преобразование "1(2-1)1" в "1 * (2 - 1) * 1"
			if(buffer.peekLast().getUnitType().equals(Unit.UnitType.OPERATOR) && !result.isEmpty())
				if (((Operator) buffer.peekLast()).get().equals(Operators.RightRoundBracket)
						&& (result.peekFirst().getUnitType().equals(Unit.UnitType.OPERAND)
						|| ((Operator) result.peekFirst()).get().equals(Operators.LeftRoundBracket))){
					result.addFirst(new Operator(Operators.Multiplication));
				}
			if(!result.isEmpty() && result.peekFirst().getUnitType().equals(Unit.UnitType.OPERATOR))
				if(((Operator) result.peekFirst()).get().equals(Operators.LeftRoundBracket)
						&& buffer.peekLast().getUnitType().equals(Unit.UnitType.OPERAND)) {
					result.addFirst(new Operator(Operators.Multiplication));
				}
			result.addFirst(buffer.pollLast());
		}
		return (List<Unit>) result;
	}

	/**
	 * метод нормализует запись -
	 * убирает лишние пробелы и добавляет нужные -
	 * "1-2 +(3  *4)" -> "1 - 2 + ( 3 * 4 )"
	 * @param expression - алгебраическое выражение
	 * @return - нормализованое алгебраическое выражение
	 */
	private String normaliseExpr(String expression) {
    	expression = expression.replaceAll(" ", "");
    	for (Operators operator : Operators.values()) {
    		expression = expression.replace(operator.getOperator(), " " + operator.getOperator() + " ");
    	}
		expression = expression.replaceAll("  ", " ").trim();
    	return expression;
	}

	/**
	 * преобразование алгебраического выражения в польскую запись
	 * @param expression - алгебраическое выражение
	 * @return - польская запись
	 */
	private Deque<Unit> toPolishRecord(String expression) {

		Deque <Unit> stack = new LinkedList<>();
		Deque<Unit> result = new LinkedList<>();


		for(int i = 0; i < unitExpression.size(); i++) {

			if(unitExpression.get(i).getUnitType().equals(Unit.UnitType.OPERAND)) {
				result.add(unitExpression.get(i));
				logger.trace("добавили в запись " + result.peekLast().get());
			}
			if(unitExpression.get(i).getUnitType().equals(Unit.UnitType.OPERATOR)) {
				Operator curentOperator = (Operator) unitExpression.get(i);
				for (Operators operator : Operators.values()) {
					if(operator.getOperator().equals( curentOperator.get().getOperator()) ) {
						if(curentOperator.getType().equals(TypeOfOperators.BinaryOperators)) {
							while(!stack.isEmpty() && ( (((Operator) stack.peekLast()).getType().equals(TypeOfOperators.PrefixUnaryOperators)
									|| ((Operators) stack.peekLast().get()).getPriority() >= curentOperator.get().getPriority())
									&& stack.peekLast().getType() != TypeOfOperators.Brackets) ) {
								result.add(stack.pollLast());
								logger.trace("добавили из стека в запись " + ((Operators)result.peekLast().get()).getOperator());
							}
						}
						if(curentOperator.get() == Operators.RightRoundBracket) {
							while (stack.peekLast().get() != Operators.LeftRoundBracket) {
								result.add(stack.pollLast());
								logger.trace("добавили из стека в запись между скобками " + ((Operators)result.peekLast().get()).getOperator());
							}
							logger.trace("убрали из стека " + ((Operators)stack.peekLast().get()).getOperator());
							stack.pollLast();
						}
						else {
							stack.addLast(curentOperator);
							logger.trace("добавили в стек " + ((Operators)stack.peekLast().get()).getOperator());
						}
					}
				}
			}
		}
		while(stack.size() > 0) {
			result.add(stack.pollLast());
			logger.trace("добавили из стека в запись " + ((Operators)result.peekLast().get()).getOperator());
		}
		return result;
	}

	/**
	 * вычислеие результата польской записи
 	 * @return - результат в виде операнда
	 * @see Operand
	 */
	public Operand calculate() throws ArithmeticException {
		Deque <Unit> stack = new LinkedList<>();
		while (!polishRecord.isEmpty()) {
			if (polishRecord.peekFirst().getUnitType() == Unit.UnitType.OPERAND) {
				stack.addLast(polishRecord.pollFirst());
			}
			if (polishRecord.peekFirst().getUnitType() == Unit.UnitType.OPERATOR) {
				Operand secondOperand = (Operand) stack.pollLast();
				Operator operator = ((Operator) polishRecord.pollFirst());
				logger.trace("выполняется операция \" {} \" ",operator.get().getOperator());
				if (operator.getType().equals(TypeOfOperators.BinaryOperators)) {
					logger.trace("выполняется бинарная операция");
					Operand firstOperand = (Operand) stack.pollLast();
					stack.addLast(firstOperand.apply(operator, secondOperand));
					switch (((Operand) stack.peekLast()).get().toString()) {
						case "Infinity":
							logger.warn("{} {} {} = {} ",firstOperand.get(),operator.get().getOperator(), secondOperand.get(), ((Operand) stack.peekLast()).get());
							throw new ArithmeticException("Infinity");
						case "NaN":
							logger.warn("{} {} {} = {} ",firstOperand.get(),operator.get().getOperator(), secondOperand.get(), ((Operand) stack.peekLast()).get());
							throw new ArithmeticException("Not-a-Number");
					}
					logger.debug("{} {} {} = {} ",firstOperand.get(),operator.get().getOperator(), secondOperand.get(), ((Operand) stack.peekLast()).get());
				}
				else {
					logger.trace("выполняется унарная операция");
					stack.addLast(secondOperand.apply(operator));
					switch (((Operand) stack.peekLast()).get().toString()) {
						case "Infinity":
							logger.error("{} {} = {} ",operator.get().getOperator(), secondOperand.get(), ((Operand) stack.peekLast()).get());
							throw new ArithmeticException("Infinity");
						case "NaN":
							logger.error("{} {} = {} ",operator.get().getOperator(), secondOperand.get(), ((Operand) stack.peekLast()).get());
							throw new ArithmeticException("Not-a-Number");
					}
					logger.debug("{} {} = {} ",operator.get().getOperator(), secondOperand.get(), ((Operand) stack.peekLast()).get());
				}
			}
		}
		return (Operand) stack.pollFirst();
	}

	/**
	 * возвращает алгебраическое выражение в виде строки
	 * @return - алгебраическое выражение
	 */
	public String getUnitExpression() {
		String result = unitExpression.stream()
				.map(x -> x.getUnitType() == Unit.UnitType.OPERATOR ?
						((Operator) x).get().getOperator() : NumConverter.convertDoubleToString(((Operand) x).get()))
				.collect(Collectors.joining(" "));

		return result;
	}
	/**
	 * возвращает польскую запись в виде строки
	 * @return - польская запись
	 */
	public String getPolishRecord() {
		String result = polishRecord.stream()
				.map(x -> x.getUnitType() == Unit.UnitType.OPERATOR ?
						((Operator) x).get().getOperator() : NumConverter.convertDoubleToString(((Operand) x).get()))
				.collect(Collectors.joining(" "));
		return result;
	}

	public static void main(String[] args) {

	}
}