package lw.droid.persistence;

/**
 * @author Standa
 *
 */
public @interface MapField {
	String name() default "auto";
	String defVal() default "auto";
	String sqlType() default "auto";
	boolean readOnly() default false;
	boolean pk() default false;
	boolean notNull() default true;
}
