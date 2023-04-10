package local.myproject.Calculate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * класс функциональных методов - конвертеров чисел из одного формата или системы счисления в другой/другую
 * @author Evgenii Mironov
 * version 1.0
 */
public class Converter {

	private static final Logger logger = LoggerFactory.getLogger(Converter.class.getName());
	private Converter() {
	}

	/**
	 * конвертация римских чисел в арабские принимает римские числа от 1 до 3 999 (от I до MMMCMXCIX)
	 * @param romanNum Римское число в виде строки, валидны I, V, X, L, C, D, M
	 * @return арабское число в формате double
	 */
	public static double romanToDouble(String romanNum) {
		double arabicNum = 0;
		
    	List<Roman> romanNums = (ArrayList<Roman>) romanNum.chars().mapToObj(x -> Roman.valueOf(String.valueOf((char) x)))
    															   .collect(Collectors.toList());
    	
    	while (romanNums.size() > 0) {
    		Roman buf = romanNums.remove(0);
    		if(romanNums.size() > 0 && buf.shouldCombine(romanNums.get(0))) {
    			arabicNum += buf.toInt(romanNums.remove(0));
    		}
    		else {
    			arabicNum += buf.toInt();
    		}
    	}
		return arabicNum;
	}

	/**
	 * конвертация арабских чисел в римские работает с диапазоном числа от 1 до 3 999 (от I до MMMCMXCIX)
	 * @param num - арабское число
	 * @return - римское число
	 */
	public static String doubleToRoman(double num) {
		String romanNum = "";
		
    	List<String> romans = Arrays.stream(Roman.values()).map(Roman::toString).toList();
    	List<Integer> arabics = romans.stream().map(x -> Roman.valueOf(x).toInt()).toList();
    	
    	while (num > 0 ) {
    		for (int i = romans.size() - 1; i >= 0; i--) {
    			if (num >= arabics.get(i)) {
    				romanNum += romans.get(i);
    				num -= arabics.get(i);
    				i = 0;
    			}
    		}
    	}
		return romanNum;
	}

	/**
	 * преобразует число из произвольного типа в double
	 * @return число в double
	 */
	public static double anyTypeNumbToDouble(String numb){
		if (Validator.validateArabic(numb)) {
			return Double.valueOf(numb);
		}
		if(Validator.validateRoman(numb)) {
			return Double.valueOf(romanToDouble(numb));
		}
		if(Validator.validateBinary(numb)) {
			return Double.valueOf(byteToDouble(numb));
		}
		return Double.NaN;
	}
	/**
	 * конвертация операнда в печатный вид в соответствии с типом операнда
	 * @param - операнд
	 * @return - значение ооперанда в виде строки в сответствии с типом операнда
	 */
	public static String operandToString(Operand operand) {
		switch (operand.getType()) {
			case Roman:
				return Double.valueOf(operand.getValue()) == 0 ? "Zero" : doubleToRoman(Double.valueOf(operand.getValue()));
			case Arabic:
				return operand.getValue();
			case Binary:
				return doubleToByte(Double.valueOf(operand.getValue()));
		}
		return null;
	}
	/**
	 * перегруженый метод конвертации операнда в печатный вид в соответствии с заданным типом
	 * @param operand - операнд
	 * @param type - заданный тип
	 * @return - значение ооперанда в виде строки в сответствии с заданным типом
	 */
	public static String operandToString(Operand operand, TypeOfOperands type) {
		switch (type) {
			case Roman:
				return Double.valueOf(operand.getValue()) == 0 ? "Zero" : doubleToRoman(Double.valueOf(operand.getValue()));
			case Arabic:
				return operand.getValue();
			case Binary:
				return doubleToByte(Double.valueOf(operand.getValue()));
		}
		return null;
	}

	/**
	 * конвертация двоичных чисел в десятичные
	 * @param - число в двоичном виде с перфиксом "0b"
	 * @return - число в виде double
	 */
	public static double byteToDouble (String byteNum) {
		return Long.parseLong(byteNum.replaceAll("0b",""),2);
	}
	/**
	 * конвертация десятичных чисел в двоичные
	 * @param - десятичное число
	 * @return - двоичное число
	 */
	public static String doubleToByte (double num) {
		return "0b" + Long.toBinaryString((long) num);
	}
	/**
	 * преобразует double в String убирая дробную часть если она равна нулю
	// * @param num - число
	 * @return - введенное число в виде строки
	 */
	public static  String doubleToString(double num) {
		StringBuilder str =  new StringBuilder();
		str.append(String.valueOf((int) num))
				.append(Double.valueOf(String.valueOf(num).replaceAll("^-?[0-9]+", "")) != 0 ?
						String.valueOf(num).replaceAll("^-?[0-9]+", "") : "");
		return str.toString();
	}

	public static void main(String[] args) {

		logger.trace("{}", anyTypeNumbToDouble("0b110010010111"));
	}
}

