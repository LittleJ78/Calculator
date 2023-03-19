package local.myproject.Calculate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * класс для самописных математических функций
 */
public class CustomMath {
    private static final Logger logger = LoggerFactory.getLogger(App.class.getName());

    /**
     * вычисление факториала
     * @param numb аргумент факториала
     * @return факториал аргумента
     */
    public static double factorial(double numb)
    {
       if (numb < 0) {
           throw new ArithmeticException(String.format("Factorial argument must be a positive, but it's %s",
                   Converter.doubleToString(numb)));
       }
       if(Converter.doubleToString(numb).contains(".")) {
           throw new ArithmeticException(String.format("Factorial argument must be an integer, but it's %s",
                   Converter.doubleToString(numb)));
       }
       double result = 1;
       for (long i = 1; i <= (long) numb; i++) {
           result *= i;
       }
        return result;
    }
}
