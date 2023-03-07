package local.myproject.Calculate;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public class Operator extends Unit{
    private TypeOfOperators type;
    private Operators value;

    Operator (Operators value) {
        this.value = value;
        this.unitType = UnitType.OPERATOR;
        setType();
    }

    private void setType() {
        String strClass = "local.myproject.Calculate.";
        List<String> typeOp = Arrays.stream(TypeOfOperators.values()).map(x -> x.name()).toList();
        for (int i = 0; i < typeOp.size(); i++) {
            try {
                Field[] fieldsOfEnumDifferentOperators = Class.forName(strClass + typeOp.get(i)).getFields();
                String result = Arrays.stream(fieldsOfEnumDifferentOperators)
                        .map(o -> o.toString().replaceAll("^[\\D]+[!.]", ""))
                        .filter(x -> x.equals(value.name())).findFirst().orElse("");
                if (result != "") {
                    this.type = TypeOfOperators.valueOf(typeOp.get(i));
                    break;
                }
            }
            catch (ClassNotFoundException e) {
                this.type = TypeOfOperators.Brackets;
                System.out.println("жопа");
            }
        }
    }

    @Override
    public TypeOfOperators getType() {
        return type;
    }

    @Override
    public Operators get() {
        return value;
    }

    public static void main(String... args) {
        Operator op = new Operator(Operators.Sinus);
        System.out.println(op.type);
    }
}
