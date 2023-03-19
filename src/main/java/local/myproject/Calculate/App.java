package local.myproject.Calculate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * алгебраическое выражение принимает на вход следующие операторы -
 * 		"+" - сложение
 * 		"-" - вычитание
 * 		"*" - умножение
 * 		"/" - деление
 * 		"^" - возведение в сепень
 * 		"sin" - синус (аргумент в градусах)
 * 		"cos" - косинус (аргумент в градусах)
 * 		"tan" - тангенс (аргумент в градусах)
 * 		"ctg" - котангенс (аргумент в градусах)
 * 		"ln" - натуральный логарифм
 * 		"lg" - десятичный логарифм
 * 		"(" ")" - для указания приоритета выполнения используются скобки
 * 	пробелы не значимы, перед/после скобок знак умножения не значим
 */
public class App 
{
    private static final Logger logger = LoggerFactory.getLogger(App.class.getName());
    public static void main(String[] args ) {
		logger.info("The program is started, waiting the expression");
    	Scanner in = new Scanner(System.in);
    	String input = in.nextLine();
		try{
    	Expression expr = new Expression(input);
    	in.close();
			logger.info("result of execute expression is {}", Converter.operandToString(expr.calculate()));
		}
		catch (ArithmeticException exc) {
			logger.warn("ошибочка вышла : {}", exc.getMessage());
		}
		catch (Exception exc) {
			logger.error("случилось страшное : {} ", exc.toString());
			exc.printStackTrace();
		}
    }
}
//1+2(-3-4)(2+sin90)6! - вызывает ошибку
//1+2(3-4)(2+sin90)6! - не вызывает