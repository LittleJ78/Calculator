package local.myproject.Calculate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * перечисление классов типов операторов, в пакете обязан быть класс (перечисление) с именем кажого из
 * полей текщего перечисления заполненым соответствующими ему полями из перечисления Operators
 * @see Operators
 * @author Evgenii Mironov
 * version 1.0
 */
public enum TypeOfOperators {

    BinaryOperators,
    PrefixUnaryOperators,
    PostfixUnaryOperators,
    Brackets;

    private static final Logger logger = LoggerFactory.getLogger(TypeOfOperators.class.getName());
}
