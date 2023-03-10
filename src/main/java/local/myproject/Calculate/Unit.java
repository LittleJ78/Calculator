package local.myproject.Calculate;

/**
 * Абстрактный класс для членов алгебраического выражения
 * @see Operator
 * @see Operands
 * @author Evgenii Mironov
 * version 1.0
 */

public abstract class Unit {
    /**
     * перечисление задающее тип члена алгебраического выражения
     */
    enum UnitType {
        OPERATOR,
        OPERAND;
    }

    /**тип члена алгебраического выражения*/
    UnitType unitType;

    /**
     * гетер
     * @return - тип члена алгебраического выражения - оператор / операнд
     */
    public UnitType getUnitType() {
        return unitType;
    }

    /**
     * гетер
     * @return - возвращает значение оператора или операнда
     */
    public Object get() {
        return null;
    }

    /**
     * гетер
     * @return - возвращает тип оператора или операнда
     */
    public Object getType(){
       return null;
    }
}