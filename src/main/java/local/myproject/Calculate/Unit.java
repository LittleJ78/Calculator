package local.myproject.Calculate;

/**
 * Абстрактный класс для членов алгебраического выражения
 * @see Operator
 * @see TypeOfOperands
 * @author Evgenii Mironov
 * version 1.0
 */

public abstract class Unit {

    /**
     * гетер
     * @return - возвращает значение оператора или операнда
     */
    public String getValue() {
        return null;
    }

    /**
     * гетер
     * @return - возвращает тип оператора или операнда
     */
    public Enum getType(){
       return null;
    }
    /**
     * гетер
     * @return - возвращает приотет оператора, для операнда возвращает ноль
     */
    public int getPriority() {return 0;}
}