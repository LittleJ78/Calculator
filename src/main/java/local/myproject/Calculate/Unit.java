package local.myproject.Calculate;

public abstract class Unit {
    enum UnitType {
        OPERATOR,
        OPERAND;
    }

    UnitType unitType;

    public UnitType getUnitType() {
        return unitType;
    }

    public Object get() {
        return null;
    }

    public Object getType(){
       return null;
    }
}