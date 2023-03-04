package local.myproject.Calculate;

import java.util.*;
import java.util.stream.Collectors;

public class NumConverter {

	private NumConverter() {
	}
	
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
	
	public static String arabicToRoman (double num) {
		String romanNum = "";
		
    	List<String> romans = Arrays.asList(Roman.values()).stream().map(Roman::toString).toList();
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
}
