package main.java.fr.pizzeria.model;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author etbourreau
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface ToString {
	/**
	 * @return true if the field must be write in uppercase, false by default
	 */
	boolean upperCase() default false;
}
