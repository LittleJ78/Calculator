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
public class NumConverter {

	private static final Logger logger = LoggerFactory.getLogger(NumConverter.class.getName());
	private NumConverter() {
	}

	/**
	 * конвертация римских чисел в арабские принимает римские числа от 1 до 3 999 (от I до MMMCMXCIX)
	 * @param romanNum Римское число в виде строки, валидны I, V, X, L, C, D, M
	 * @return арабское число в формате double
	 */
	public static double romanToArabic (String romanNum) {
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
	public static String arabicToRoman (double num) {
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
	 * преобразует double в String убирая дробную часть если она равна нулю
	 * @param num - число
	 * @return - введенное число в виде строки
	 */
	public static  String convertDoubleToString(double num) {
		StringBuilder str =  new StringBuilder();
		str.append(String.valueOf((int) num))
				.append(Double.valueOf(String.valueOf(num).replaceAll("^-?[0-9]+", "")) != 0 ?
						String.valueOf(num).replaceAll("^-?[0-9]+", "") : "");
		return str.toString();
	}

	public static void main(String[] args) {
		double i = -1;
		logger.trace(convertDoubleToString(i));
	}
}

