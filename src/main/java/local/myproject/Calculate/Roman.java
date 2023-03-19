package local.myproject.Calculate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * перечисление используемое для конвертации из римских чисел в арабские и обратно
 * @see Converter
 * @author Evgenii Mironov
 * version 1.0
 */
public enum Roman {
	I (1), IV (4), V (5), IX (9), X (10), XL (40), L (50), XC (90), C (100), CD (400), D (500), CM (900), M (1000);

    private static final Logger logger = LoggerFactory.getLogger(Roman.class.getName());
    /** число соответствующее заданному перечислению*/
    private final int value;

    /**
     * конструктор
     * @param value - число соответствующее заданному перечислению
     */
    private Roman(int value) {
        this.value = value;
    }

    /**
     * гетер для одиночных римских чисел
     * @return - число соответствующее заданному перечислению
     */
    public int toInt() {
        return value;
    }

    /**
     * проверка на валидность комбинации двух римских чисел - I < V (IV) - true
     * @param next - римское число
     * @return да/нет
     */
    public boolean shouldCombine(Roman next) {
        return this.value < next.value;
    }

    /**
     * перегруженый гетер для составных (из двух) римских чисел -   "V(5) - I(1) = IV(4)"
     * @param next римское число
     * @return - составное (из двух) римское число
     */
    public int toInt(Roman next) {
        return next.value - this.value;
    }
}
