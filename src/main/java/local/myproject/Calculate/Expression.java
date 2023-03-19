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
	/**контейнер для хранения алгебраического выражения разбитого на отдельные операции */
	private List<List<Unit>> atomicExpression;


	/**
	 * конструктор
	 * @param expression - Алгебраическое выражение вида "1 + 2 - (3 - 1)"
	 * @see Operators - перечисление поддерживаемых операторов
	 * @see TypeOfOperands - перечисление поддерживаемых форматов чисел
	 */
	public Expression(String expression) throws Exception {
		this.unitExpression = toUnitExpression(expression);
		logger.info("записали нормализованую запись {} ",this.getUnitExpression());
		this.unitExpression = normaliseOperatorsTypeInUnitExpr(unitExpression);
		logger.info("переопределили операторы в выражении {} ",this.getUnitExpression());
		this.polishRecord = toPolishRecord(expression);
		logger.info("Записали польскую запись " + getPolishRecord());
		atomicExpression = new ArrayList<>();
	}

	/**
	 * переопределение операторов с изменяемым типом - минус может быть как бинарным так и префиксным унарным -
	 * в зависимости от места где  оператор находиться в алгебраическом выражении
	 * @param expression - алгебраическое выражение в виде списка из отдельных операторов и операнд
	 * @return - алгебраическое выражение в виде списка из отдельных переопределенных операторов и операнд
	 */
	private List<Unit> normaliseOperatorsTypeInUnitExpr (List<Unit> expression) throws Exception {
		if (Operator.class.isInstance(expression.get(0)) && ((Operator) expression.get(0)).getValue().equals("-")) {
			logger.trace("обрабатываем {}",expression.get(0).getValue());
			((Operator) expression.get(0)).setTypeOfOperator(TypeOfOperators.PrefixUnaryOperators);
			logger.trace("изменили тип оператора с индексом 0 на {} ", TypeOfOperators.PrefixUnaryOperators);
		}
		for (int i = 1; i < expression.size(); i++) {
			logger.trace("обрабатываем {}",expression.get(i).getValue());
			if(Operator.class.isInstance(expression.get(i)) && ((Operator) expression.get(i)).getValue().equals("-")) {
				if(Operator.class.isInstance(expression.get(i - 1)) && !((Operator) expression.get(i-1)).getValue().equals(")")) {
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
			if (Validator.validateNumber(stringExpression.get(i))) {
				buffer.add(new Operand(Validator.setTypeOfOperand(stringExpression.get(i)), Converter.anyTypeNumbToDouble(stringExpression.get(i))));
				logger.trace("добавили {} в контейнер", stringExpression.get(i));
			}
			if(Validator.validateOperation(stringExpression.get(i))) {
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
			if(Operator.class.isInstance(buffer.peekLast()) && !result.isEmpty())
				if (((Operator) buffer.peekLast()).getValue().equals(")")
						&& (Operand.class.isInstance(result.peekFirst())
						|| ((Operator) result.peekFirst()).getValue().equals("("))){
					result.addFirst(new Operator(Operators.Multiplication));
				}
			if(!result.isEmpty() && Operator.class.isInstance(result.peekFirst()))
				if(((Operator) result.peekFirst()).getValue().equals("(")
						&& Operand.class.isInstance(buffer.peekLast())) {
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

			if(Operand.class.isInstance(unitExpression.get(i))) {
				result.add(unitExpression.get(i));
				logger.trace("добавили в запись {}", result.peekLast().getValue());
			}
			if(Operator.class.isInstance(unitExpression.get(i))) {
				Operator curentOperator = (Operator) unitExpression.get(i);
				for (Operators operator : Operators.values()) {
					if(operator.getOperator().equals( curentOperator.getValue()) ) {
						if(curentOperator.getType().equals(TypeOfOperators.BinaryOperators)) {
							while(!stack.isEmpty() && ( (stack.peekLast().getType().equals(TypeOfOperators.PrefixUnaryOperators)
									|| stack.peekLast().getPriority() >= curentOperator.getPriority())
									&& !stack.peekLast().getType().equals(TypeOfOperators.Brackets)) ) {
								result.add(stack.pollLast());
								logger.trace("добавили из стека в запись {}", result.peekLast().getValue());
							}
						}
						if(curentOperator.getValue().equals(")")) {
							while (!stack.peekLast().getValue().equals("(")) {
								result.add(stack.pollLast());
								logger.trace("добавили из стека в запись между скобками {}", result.peekLast().getValue());
							}
							logger.trace("убрали из стека {}", stack.peekLast().getValue());
							stack.pollLast();
						}
						else {
							stack.addLast(curentOperator);
							logger.trace("добавили в стек {}", stack.peekLast().getValue());
						}
					}
				}
			}
		}
		while(stack.size() > 0) {
			result.add(stack.pollLast());
			logger.trace("добавили из стека в запись {}", result.peekLast().getValue());
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
			if (Operand.class.isInstance(polishRecord.peekFirst())) {
				stack.addLast(polishRecord.pollFirst());
			}
			if (Operator.class.isInstance(polishRecord.peekFirst())) {
				Operand secondOperand = (Operand) stack.pollLast();
				Operator operator = ((Operator) polishRecord.pollFirst());
				logger.trace("выполняется операция \" {} \" ",operator.getValue());
				if (operator.getType().equals(TypeOfOperators.BinaryOperators)) {
					logger.trace("выполняется бинарная операция");
					Operand firstOperand = (Operand) stack.pollLast();
					stack.addLast(firstOperand.apply(operator, secondOperand));
					atomicExpression.add(new ArrayList<>(List.of(firstOperand,operator,secondOperand)));
					switch (((Operand) stack.peekLast()).getValue().toString()) {
						case "Infinity":
							logger.warn("{} {} {} = {} ",Converter.operandToString(firstOperand),operator.getValue(), Converter.operandToString(secondOperand), Converter.operandToString((Operand) stack.peekLast()));
							throw new ArithmeticException("Infinity");
						case "NaN":
							logger.warn("{} {} {} = {} ",Converter.operandToString(firstOperand),operator.getValue(), Converter.operandToString(secondOperand), Converter.operandToString((Operand) stack.peekLast()));
							throw new ArithmeticException("Not-a-Number");
					}
					logger.debug("{} {} {} = {} ",Converter.operandToString(firstOperand),operator.getValue(), Converter.operandToString(secondOperand), Converter.operandToString((Operand) stack.peekLast()));
				}
				else {
					logger.trace("выполняется унарная операция");
					stack.addLast(secondOperand.apply(operator));
					if(operator.getType().equals(TypeOfOperators.PrefixUnaryOperators)) {
						atomicExpression.add(new ArrayList<>(List.of(operator, secondOperand)));
					} else if (operator.getType().equals(TypeOfOperators.PostfixUnaryOperators)) {
						atomicExpression.add(new ArrayList<>(List.of(secondOperand, operator)));
					}
					switch (((Operand) stack.peekLast()).getValue().toString()) {
						case "Infinity":
							logger.error("{} {} = {} ", operator.getValue(), Converter.operandToString(secondOperand), Converter.operandToString((Operand) stack.peekLast()));
							throw new ArithmeticException("Infinity");
						case "NaN":
							logger.error("{} {} = {} ", operator.getValue(), Converter.operandToString(secondOperand), Converter.operandToString((Operand) stack.peekLast()));
							throw new ArithmeticException("Not-a-Number");
					}
					logger.debug("{} {} = {} ", operator.getValue(), Converter.operandToString(secondOperand), Converter.operandToString((Operand) stack.peekLast()));
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
				.map(x -> Operator.class.isInstance(x) ?
						((Operator) x).getValue() : Converter.doubleToString(((Operand) x).getValue()))
				.collect(Collectors.joining(" "));

		return result;
	}
	/**
	 * возвращает польскую запись в виде строки
	 * @return - польская запись
	 */
	public String getPolishRecord() {
		String result = polishRecord.stream()
				.map(x -> Operator.class.isInstance(x) ?
						((Operator) x).getValue() : Converter.doubleToString(((Operand) x).getValue()))
				.collect(Collectors.joining(" "));
		return result;
	}

	/**
	 * гетер
	 * @return - возвращает количество атомарных выражений
	 */
	public int countOfAtomicExpressions() {
		return atomicExpression.size();
	}

	/**
	 * гетер
	 * @param - номер атомарной алгебраического выражения
	 * @return - запрошеное выражение в виде строки
	 */
	public String getAtomicExpression(int i) throws Exception{
		if( i < 0 || i >= atomicExpression.size()) {
			throw new IndexOutOfBoundsException(String.format("количество операций %d, запрошено %d", atomicExpression.size(), i));
		}
		int size = atomicExpression.get(i).size();
		StringBuilder expression = new StringBuilder();
		for (int j = 0; j < size; j++) {
			expression.append( Operand.class.isInstance(atomicExpression.get(i).get(j)) ?
					Converter.operandToString((Operand) atomicExpression.get(i).get(j)) :
					((Operator) atomicExpression.get(i).get(j)).getValue()).append(" ");
		}
		return expression.toString().trim();
	}

	public static void main(String[] args) throws Exception {
		Expression expression = new Expression("1-2(-4*7)+2^2");
		expression.calculate();
		for(int i = 0; i < expression.countOfAtomicExpressions(); i++) {
			logger.info("{}) {}",i + 1 ,expression.getAtomicExpression(i));
		}
		logger.info("{}",Operand.class.isInstance(expression.unitExpression.get(0)));
	}
}