package local.myproject.Calculate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * класс скобок, функциональной нагрузки не несет, требуется для поддержания структуры операторов
 * @see Operators - перечисление всех операторов, должно включать все названия полей текущего перечисления
 * @see TypeOfOperators - перечисление классов типов операторов, должно влючать имя текущего класса
 * @author Evgenii Mironov
 * version 1.0
 */
public enum Brackets {
    LeftRoundBracket,
    RightRoundBracket;

    private static final Logger logger = LoggerFactory.getLogger(Brackets.class.getName());
}
