package local.myproject.Calculate;

//import org.apache.logging.log4j.core.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class App 
{
    private static final Logger logger = LoggerFactory.getLogger(App.class.getName());
    public static void main(String[] args ) {
		logger.info("Start the program, wait the expression");
    	Scanner in = new Scanner(System.in);
    	String input = in.nextLine();
    	Expression expr = new Expression(input);
    	in.close();
		logger.info("result of execute expression is {}",NumConverter.convertDoubleToString(expr.calculate().get()));
    }
}