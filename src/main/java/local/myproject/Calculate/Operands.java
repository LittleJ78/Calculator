package local.myproject.Calculate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * перечисление форматов чисел соотносящийся к типу системы счисления в которой может быть записан операнд
 * 							- арабское, римское, двоичное и т.д.
 * @see Operand
 * * @author Evgenii Mironov
 *  * version 1.0
 */

public enum Operands {
	Roman,
	Arabic;

	private static final Logger logger = LoggerFactory.getLogger(Operands.class.getName());
}
