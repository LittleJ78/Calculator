package local.myproject.Calculate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
/**
 * Класс для операторов использующихся в алгебраическом выражении
 * @author Evgenii Mironov
 * version 1.0
 */
public class Operator extends Unit{
    private static final Logger logger = LoggerFactory.getLogger(Operator.class.getName());

    /** тип оператора - унарный / бинарный / скобка и т.д. */
    private TypeOfOperators type;
    /** значение оператора - плюс / минус / синус и т.д. */
    private Operators value;

    /**
     * конструктор
     * @param value - значение оператора - плюс / минус / синус и т.д.
     * @see Operators
     */
    Operator (Operators value) {
        this.value = value;
        this.type = setType(value);
        this.unitType = UnitType.OPERATOR;
    }

    /**
     * определяет по значению оператора его тип
     * @param value - значение оператора - плюс / минус / синус и т.д.
     * @return - тип оператора - унарный / бинарный / скобка и т.д.
     */
    private TypeOfOperators setType(Operators value) {
        String strClass = this.getClass().getName().replaceAll("[a-zA-Z]*$","");
        List<String> typeOp = Arrays.stream(TypeOfOperators.values()).map(x -> x.name()).toList();
        for (int i = 0; i < typeOp.size(); i++) {
            try {
                Field[] fieldsOfEnumDifferentOperators = Class.forName(strClass + typeOp.get(i)).getFields();
                String result = Arrays.stream(fieldsOfEnumDifferentOperators)
                        .map(o -> o.toString().replaceAll("^[\\D]+[!.]", ""))
                        .filter(x -> x.equals(value.name())).findFirst().orElse("");
                if (result != "") {
                    return TypeOfOperators.valueOf(typeOp.get(i));
                }
            }
            catch (ClassNotFoundException e) {
                System.out.println("Didn't define operation for " + this.value);
            }
        }
        return null;
    }

    /**
     * геттер
     * @return - возвращает тип оператора - унарный / бинарный / скобка и т.д.
     */
    @Override
    public TypeOfOperators getType() {
        return type;
    }

    /**
     * геттер
     * @return - возвращает значение оператора - плюс / минус / синус и т.д.
     */
    @Override
    public Operators get() {
        return value;
    }
}